package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.service.UserService;
import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupActivity extends ActionBarActivity {

    private MaterialEditText userNameEditText, userLastName1EditText,
            userLastName2EditText, userCareerEditText, userIdEditText;
    private Button deleteButton;
    private String mUserName, mUserLastName1, mUserLastName2, mUserId, mUserCareer, mUserMail;
    private long mUserUid;
    private String ENDPOINT;
    private FloatingActionButton mFab;
    private Activity mActivity;
    private SnackBar.OnMessageClickListener postSnackbarClickListener;
    private SnackBar.OnMessageClickListener putSnackbarClickListener;
    private SnackBar.OnMessageClickListener deleteSnackbarClickListener;
    private Boolean mIsEditting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userNameEditText = (MaterialEditText) findViewById(R.id.signup_name);
        userLastName1EditText = (MaterialEditText) findViewById(R.id.signup_last_name_1);
        userLastName2EditText = (MaterialEditText) findViewById(R.id.signup_last_name_2);
        userCareerEditText = (MaterialEditText) findViewById(R.id.signup_career);
        userIdEditText = (MaterialEditText) findViewById(R.id.signup_id);
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

        mFab = (FloatingActionButton) findViewById(R.id.activity_signup_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignup();
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
    }

    private void doSignup() {
        mUserName = userNameEditText.getText().toString();
        mUserLastName1 = userLastName1EditText.getText().toString();
        mUserLastName2 = userLastName2EditText.getText().toString();
        mUserId = userIdEditText.getText().toString();
        mUserCareer = userCareerEditText.getText().toString();
        mUserMail = mUserId + "@itesm.mx";
        mUserUid = 0;

        if (mUserName.equals("") || mUserLastName1.equals("") || mUserLastName2.equals("")
                || mUserId.equals("") || mUserCareer.equals("")) {
            new SnackBar.Builder(mActivity)
                    .withMessage("Error en datos.")
                    .withTextColorId(R.color.primary_text_light)
                    .show();
        } else {
            User newUser = new User(
                    mUserName,
                    mUserLastName1,
                    mUserLastName2,
                    mUserId,
                    mUserCareer,
                    mUserUid,
                    mUserMail,
                    new ArrayList<String>()
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
        }
    }

    private void doDelete() {
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
    }
}
