package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.async_tasks.GetLoginInfo;
import com.itesm.labs.rest.models.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends ActionBarActivity {

    private String ENDPOINT = "http://labs.chi.itesm.mx:8080";
    private Activity mActivity;

    private Button loginButton;
    private MaterialEditText userMat, userPass;
    private String mUserId, mUserPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;

        userMat = (MaterialEditText) findViewById(R.id.user_id);
        userPass = (MaterialEditText) findViewById(R.id.user_pass);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mUserId = userMat.getText().toString();
                mUserPass = userPass.getText().toString();

                if (mUserId.length() == 6)
                    mUserId = "A00" + mUserId;
                else if (mUserId.length() == 7)
                    mUserId = "A0" + mUserId;

                if(mUserId.contains("a0"))
                    mUserId = mUserId.replace("a0","A0");
                else if(mUserId.contains("a00"))
                    mUserId = mUserId.replace("a00", "A00");

                userMat.setText(mUserId);

                if (mUserId.length() == 9) {
                    GetLoginInfo getLoginInfo = new GetLoginInfo() {
                        @Override
                        protected void onPostExecute(User user) {
                            super.onPostExecute(user);

                            if (user != null) {
                                Intent intent = new Intent(mActivity.getApplicationContext(), LaboratoriesActivity.class);
                                intent.putExtra("ALLOWEDLABS", user.getAllowedLabs());
                                startActivity(intent);
                                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom); // Activity transition animation.
                            } else
                                new SnackBar.Builder(mActivity)
                                        .withMessage("Matrícula o Contraseña incorrectos.")
                                        .withTextColorId(R.color.primary_text_light)
                                        .show();
                        }
                    };
                    getLoginInfo.execute(ENDPOINT, mUserId);
                } else {
                    new SnackBar.Builder(mActivity)
                            .withMessage("Matrícula o Contraseña incorrectos.")
                            .withTextColorId(R.color.primary_text_light)
                            .show();
                }
            }
        });
    }
}