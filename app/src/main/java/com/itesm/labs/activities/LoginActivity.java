package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.itesm.labs.R;
import com.itesm.labs.dialogs.SignupDialog;

public class LoginActivity extends ActionBarActivity {

    Button loginButton, signupButton;
    public String ENDPOINT = "http://labs.chi.itesm.mx:8080/elec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        signupButton = (Button) findViewById(R.id.login_sign_up);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , LaboratoriesActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom); // Activity transition animation.
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupDialog signupDialog = new SignupDialog(v.getContext(), ENDPOINT);
                signupDialog.show();
            }
        });
    }
}