package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.queries.LabQuery;
import com.itesm.labs.rest.service.UserService;
import com.itesm.labs.util.NfcHandler;
import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupActivity extends AppCompatActivity implements NfcHandler.UidCallback {

    private String mUserName, mUserLastName1, mUserLastName2, mUserId, mUserCareer, mUserMail;
    private long mUserUid;
    private ArrayList<String> mUserLabs;
    private String ENDPOINT;
    private Boolean mIsEditting;

    private MaterialEditText userNameEditText, userLastName1EditText,
            userLastName2EditText, userCareerEditText, userIdEditText;
    private TextView userUidTextView;
    private Button deleteButton, updateUidButton;
    private FloatingActionButton mFab;
    private Activity mActivity;
    private SnackBar.OnMessageClickListener postSnackbarClickListener;
    private SnackBar.OnMessageClickListener putSnackbarClickListener;
    private SnackBar.OnMessageClickListener deleteSnackbarClickListener;
    private NfcHandler nfcHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userNameEditText = (MaterialEditText) findViewById(R.id.signup_name);
        userLastName1EditText = (MaterialEditText) findViewById(R.id.signup_last_name_1);
        userLastName2EditText = (MaterialEditText) findViewById(R.id.signup_last_name_2);
        userCareerEditText = (MaterialEditText) findViewById(R.id.signup_career);
        userIdEditText = (MaterialEditText) findViewById(R.id.signup_id);
        userUidTextView = (TextView) findViewById(R.id.signup_uid_text);
        updateUidButton = (Button) findViewById(R.id.signup_uid_update);
        deleteButton = (Button) findViewById(R.id.activity_signup_delete);

        Intent callingIntent = getIntent();
        ENDPOINT = callingIntent.getStringExtra("ENDPOINT");
        mIsEditting = callingIntent.getBooleanExtra("ISEDIT", false);
        if (mIsEditting)
            deleteButton.setVisibility(View.VISIBLE);
        else
            deleteButton.setVisibility(View.INVISIBLE);
        mUserName = callingIntent.getStringExtra("USERNAME");
        if (mUserName != null) userNameEditText.setText(mUserName);
        mUserLastName1 = callingIntent.getStringExtra("USERLASTNAME1");
        if (mUserLastName1 != null) userLastName1EditText.setText(mUserLastName1);
        mUserLastName2 = callingIntent.getStringExtra("USERLASTNAME2");
        if (mUserLastName2 != null) userLastName2EditText.setText(mUserLastName2);
        mUserId = callingIntent.getStringExtra("USERID");
        if (mUserId != null) userIdEditText.setText(mUserId);
        mUserCareer = callingIntent.getStringExtra("USERCAREER");
        if (mUserCareer != null) userCareerEditText.setText(mUserCareer);
        mUserUid = callingIntent.getLongExtra("USERUID", 0);
        userUidTextView.setText("" + mUserUid);
        mUserLabs = callingIntent.getStringArrayListExtra("USERLABS");

        mFab = (FloatingActionButton) findViewById(R.id.activity_signup_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignup();
            }
        });

        updateUidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUidTextView.setText("" + mUserUid);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDelete();
            }
        });

        postSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doSignup();
            }
        };
        putSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doSignup();
            }
        };
        deleteSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doDelete();
            }
        };

        getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));

        mActivity = this;

        try {
            nfcHandler = new NfcHandler(this);
            enableReader();
        } catch (Exception ex) {
            Toast.makeText(this, "No NFC adapter available", Toast.LENGTH_SHORT).show();
        }


    }

    private void doSignup() {
        mUserName = userNameEditText.getText().toString();
        mUserLastName1 = userLastName1EditText.getText().toString();
        mUserLastName2 = userLastName2EditText.getText().toString();
        mUserId = userIdEditText.getText().toString();
        mUserCareer = userCareerEditText.getText().toString();
        mUserMail = mUserId + "@itesm.mx";

        if (mUserName.equals("") || mUserLastName1.equals("") || mUserLastName2.equals("")
                || mUserId.equals("") || mUserCareer.equals("")) {
            new SnackBar.Builder(mActivity)
                    .withMessage("Error en datos.")
                    .withTextColorId(R.color.primary_text_light)
                    .show();
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    User newUser = new User(
                            mUserName,
                            mUserLastName1,
                            mUserLastName2,
                            mUserId,
                            mUserCareer,
                            mUserUid,
                            mUserMail,
                            mUserLabs
                    );

                    RequestInterceptor requestInterceptor = new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Content-Type", "application/json");
                            request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
                        }
                    };

                    RestAdapter restAdapter = new RestAdapter
                            .Builder()
                            .setEndpoint(ENDPOINT)
                            .setRequestInterceptor(requestInterceptor)
                            .build();

                    UserService service = restAdapter.create(UserService.class);

                    Callback<Response> callbackPost = new Callback<Response>() {
                        @Override
                        public void success(Response result, Response response) {
                            new SnackBar.Builder(mActivity)
                                    .withMessage(mUserName + " fué agregado.")
                                    .withTextColorId(R.color.primary_text_light)
                                    .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                                        @Override
                                        public void onShow(int i) {

                                        }

                                        @Override
                                        public void onHide(int i) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            new SnackBar.Builder(mActivity)
                                    .withMessage("Error al agregar a " + mUserName)
                                    .withTextColorId(R.color.primary_text_light)
                                    .withActionMessage("Retry")
                                    .withOnClickListener(postSnackbarClickListener)
                                    .withStyle(SnackBar.Style.ALERT)
                                    .show();
                        }
                    };

                    Callback<Response> callbackPut = new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {
                            new SnackBar.Builder(mActivity)
                                    .withMessage(mUserName + " fué editado.")
                                    .withTextColorId(R.color.primary_text_light)
                                    .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                                        @Override
                                        public void onShow(int i) {

                                        }

                                        @Override
                                        public void onHide(int i) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            new SnackBar.Builder(mActivity)
                                    .withMessage("Error al editar a " + mUserName)
                                    .withTextColorId(R.color.primary_text_light)
                                    .withActionMessage("Retry")
                                    .withOnClickListener(putSnackbarClickListener)
                                    .withStyle(SnackBar.Style.ALERT)
                                    .show();
                        }
                    };

                    if (mIsEditting)
                        service.editUser(newUser.getUserId(), newUser, callbackPut);
                    else
                        service.postNewUser(newUser, callbackPost);

                    return null;
                }
            }.execute();
        }
    }

    private void doDelete() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                RequestInterceptor requestInterceptor = new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
                    }
                };

                RestAdapter restAdapter = new RestAdapter
                        .Builder()
                        .setEndpoint(ENDPOINT)
                        .setRequestInterceptor(requestInterceptor)
                        .build();

                UserService service = restAdapter.create(UserService.class);

                Callback<Response> callbackDelete = new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        new SnackBar.Builder(mActivity)
                                .withMessage(mUserName + " fué eliminado.")
                                .withTextColorId(R.color.primary_text_light)
                                .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                                    @Override
                                    public void onShow(int i) {

                                    }

                                    @Override
                                    public void onHide(int i) {
                                        finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        new SnackBar.Builder(mActivity)
                                .withMessage("Error al eliminar a " + mUserName)
                                .withTextColorId(R.color.primary_text_light)
                                .withActionMessage("Retry")
                                .withOnClickListener(deleteSnackbarClickListener)
                                .withStyle(SnackBar.Style.ALERT)
                                .show();
                    }
                };

                service.deleteUser(mUserId, callbackDelete);

                return null;
            }
        }.execute();
    }

    @Override
    public void getUid(long uid) {
        Log.d("UID:", "" + uid);
        mUserUid = uid;
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableReader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableReader();
    }

    private void enableReader() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(mActivity, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(mActivity);
    }
}
