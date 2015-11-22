package com.itesm.labs.activities;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.adapters.AllowedLabsModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.UserClient;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.utils.NfcHandler;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AddUserActivity extends LabsAppBaseActivity implements NfcHandler.UidCallback {

    private final static String TAG = AddUserActivity.class.getSimpleName();

    @Bind(R.id.signup_name)
    EditText userNameEditText;
    @Bind(R.id.signup_last_name_1)
    EditText userLastName1EditText;
    @Bind(R.id.signup_last_name_2)
    EditText userLastName2EditText;
    @Bind(R.id.signup_career)
    EditText userCareerEditText;
    @Bind(R.id.signup_id)
    EditText userIdEditText;
    @Bind(R.id.signup_uid_text)
    TextView userUidTextView;
    @Bind(R.id.signup_uid_update)
    Button updateUidButton;
    @Bind(R.id.signup_allowed_labs)
    ListView allowedLabs;
    @Bind(R.id.activity_signup_delete)
    Button deleteButton;
    @Bind(R.id.activity_signup_fab)
    FloatingActionButton mFab;

    @Inject
    UserClient mUserClient;

    private Boolean mIsEditting;
    private long mUserUid;

    private NfcHandler nfcHandler;

    private AllowedLabsModelAdapter mAdapter;

    private ArrayList<String> allowedLabsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        setupWithIntent(getIntent());

        setupAllowesLabs();

        setupStatusBar();

        setupNfc();
    }

    private void setupWithIntent(Intent callingIntent) {
        mIsEditting = callingIntent.getBooleanExtra("ISEDIT", false);
        if (mIsEditting)
            deleteButton.setVisibility(View.VISIBLE);
        else
            deleteButton.setVisibility(View.INVISIBLE);

        userNameEditText.setText(mAppGlobals.getUser().getUserName());
        userLastName1EditText.setText(mAppGlobals.getUser().getUserLastName1());
        userLastName2EditText.setText(mAppGlobals.getUser().getUserLastName2());
        userIdEditText.setText(mAppGlobals.getUser().getUserId());
        userCareerEditText.setText(mAppGlobals.getUser().getUserCareer());
        userIdEditText.setText("" + mAppGlobals.getUser().getUserUid());
    }

    private void setupAllowesLabs() {
        allowedLabsList = mAppGlobals.getUser().getAllowedLabs();

        mAdapter = new AllowedLabsModelAdapter(mContext);
        allowedLabs.setAdapter(mAdapter);
    }

    private void setupStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
    }

    private void setupNfc() {
        try {
            nfcHandler = new NfcHandler(this);
            enableReader();
        } catch (Exception ex) {
            Toast.makeText(this, "No NFC adapter available", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.activity_signup_fab)
    void onFabClick() {
        doSignup();
    }

    @OnClick(R.id.signup_uid_update)
    void onUpdateUidClick() {
        userUidTextView.setText("" + mUserUid);
    }

    @OnClick(R.id.activity_signup_delete)
    void onDeleteClick() {
        doDelete();
    }

    private void doSignup() {
        String mUserName = userNameEditText.getText().toString();
        String mUserLastName1 = userLastName1EditText.getText().toString();
        String mUserLastName2 = userLastName2EditText.getText().toString();
        String mUserId = userIdEditText.getText().toString();
        String mUserCareer = userCareerEditText.getText().toString();
        String mUserMail = mUserId + "@itesm.mx";

        if (mUserName.equals("") || mUserLastName1.equals("") || mUserLastName2.equals("")
                || mUserId.equals("") || mUserCareer.equals("")) {
            Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_empty_data), Snackbar.LENGTH_SHORT)
                    .show();
        } else if (mIsEditting) {
            User user = new User(mUserName,
                    mUserLastName1,
                    mUserLastName2,
                    mUserId,
                    mUserCareer,
                    mUserUid,
                    mUserMail,
                    allowedLabsList);

            mUserClient.editUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""), mUserId, user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task put user started");
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "Task put user completed");

                            Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_put_ok), Snackbar.LENGTH_SHORT)
                                    .setCallback(new Snackbar.Callback() {
                                        @Override
                                        public void onDismissed(Snackbar snackbar, int event) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "Task put user error: " + e.getMessage());

                            Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_put_error), Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onNext(Void aVoid) {

                        }
                    });
        } else {
            User user = new User(mUserName,
                    mUserLastName1,
                    mUserLastName2,
                    mUserId,
                    mUserCareer,
                    mUserUid,
                    mUserMail,
                    allowedLabsList);

            mUserClient.postUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""), user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task post user started");
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "Task post user completed");

                            Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_post_ok), Snackbar.LENGTH_SHORT)
                                    .setCallback(new Snackbar.Callback() {
                                        @Override
                                        public void onDismissed(Snackbar snackbar, int event) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "Task post user error: " + e.getMessage());

                            Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_post_error), Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onNext(Void aVoid) {

                        }
                    });
        }
    }

    private void doDelete() {
        String mUserId = userIdEditText.getText().toString();

        mUserClient.deleteUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""), mUserId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task delete user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task delete user completed");

                        Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_delete_ok), Snackbar.LENGTH_SHORT)
                                .setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task delete user error: " + e.getMessage());

                        Snackbar.make(findViewById(R.layout.activity_signup), getString(R.string.signup_delete_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
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
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(this, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(this);
    }
}