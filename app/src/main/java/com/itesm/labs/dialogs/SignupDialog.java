package com.itesm.labs.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.service.UserService;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mgrad_000 on 1/3/2015.
 */
public class SignupDialog extends Dialog {

    private Context mContext;
    private EditText userName, userLastName1, userLastName2, userId, userCareer;
    private Button cancel, accept;
    private String ENDPOINT;
    private String mUserName, mUserLastName1, mUserLastName2, mUserId, mUserCareer, mUserMail;
    private Integer mUserUid;
    private User mUserModel;

    public SignupDialog(Context context, String endpoint) {
        super(context);

        this.mContext = context;
        ENDPOINT = endpoint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_dialog);

        userName = (EditText) findViewById(R.id.signup_dialog_name);
        userLastName1 = (EditText) findViewById(R.id.signup_dialog_last_name_1);
        userLastName2 = (EditText) findViewById(R.id.signup_dialog_last_name_2);
        userId = (EditText) findViewById(R.id.signup_dialog_id);
        userCareer = (EditText) findViewById(R.id.signup_dialog_career);

        cancel = (Button) findViewById(R.id.signup_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCancel();
            }
        });
        accept = (Button) findViewById(R.id.signup_dialog_accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignup();
            }
        });
    }

    private void doSignup() {
        mUserName = userName.getText().toString();
        mUserLastName1 = userLastName1.getText().toString();
        mUserLastName2 = userLastName2.getText().toString();
        mUserId = userId.getText().toString();
        mUserCareer = userCareer.getText().toString();
        mUserMail = mUserId + "@itesm.mx";
        mUserUid = 0;

        mUserModel = new User(
                mUserName,
                mUserLastName1,
                mUserLastName2,
                mUserId,
                mUserCareer,
                mUserUid,
                mUserMail
        );

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "dsp-server:6842ldCC$");
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        UserService service = restAdapter.create(UserService.class);

        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(mContext, "User created...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mContext, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        };

        service.postNewUser(mUserModel, callback);
    }

    private void doCancel() {
        dismiss();
    }
}
