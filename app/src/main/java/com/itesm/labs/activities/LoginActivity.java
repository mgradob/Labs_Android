package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.R;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.UserClient;
import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.LoginAdmin;
import com.itesm.labs.rest.models.User;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends LabsAppBaseActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.user_id)
    EditText userMat;

    @Bind(R.id.user_pass)
    EditText userPass;

    @Inject
    UserClient mUserClient;

    private String mAdminId;
    private String mAdminPass;

    private MaterialDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    /**
     * Validates if the login data is correct and starts LaboratoriesActivity.
     */
    @OnClick(R.id.login_button)
    void validateUser() {
        mAdminId = userMat.getText().toString();
        mAdminPass = userPass.getText().toString();

        if (validateFields(mAdminId, mAdminPass)) {
            getToken();
        } else {
            Snackbar.make(findViewById(R.id.activity_login),
                    getResources().getString(R.string.activity_login_snackbar_login_error),
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    boolean validateFields(String id, String pass) {
        if (id.contains("l0"))
            id = id.replace("l0", "L0");

        userMat.setText(id);

        return id.length() == 9;
    }

    void getToken() {
        mUserClient.loginAdmin(new LoginAdmin(mAdminId, mAdminPass))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Token task started");
                        eventDialog(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Token task completed");

                        loginAdmin();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Token task error");
                        eventDialog(false);

                        Snackbar.make(findViewById(R.id.activity_login),
                                getResources().getString(R.string.activity_login_snackbar_login_error),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(String s) {
                        if (s == null)
                            throw new NullPointerException("Token is null");

                        mSharedPreferences.edit()
                                .putString(AppConstants.PREFERENCES_KEY_USER_TOKEN, "Token " + s)
                                .apply();
                    }
                });
    }

    void loginAdmin() {
        mUserClient.getUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAdminId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Login task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Login task completed");
                        eventDialog(false);

                        Intent intent = new Intent(getApplicationContext(), LaboratoriesActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Login task error");
                        eventDialog(false);

                        Snackbar.make(findViewById(R.id.activity_login),
                                getResources().getString(R.string.activity_login_snackbar_login_error),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(User admin) {
                        if (admin == null)
                            throw new NullPointerException("User was null");

                        mAppGlobals.setUser(admin);
                    }
                });
    }

    void eventDialog(boolean show) {
        if (show) {
            loginDialog = new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.activity_login_dialog_login_title))
                    .content(getResources().getString(R.string.activity_login_dialog_login_content))
                    .progress(true, 0)
                    .show();
        } else {
            loginDialog.dismiss();
        }
    }
}