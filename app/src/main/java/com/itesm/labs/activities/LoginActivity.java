package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.async_tasks.GetAdminInfo;
import com.itesm.labs.async_tasks.GetUsersInfo;
import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.sqlite.LabsSqliteHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private String ENDPOINT = "http://labs.chi.itesm.mx:8080";
    private Activity mActivity;

    private Button loginButton;
    private EditText userMat, userPass;

    private String mAdminId, mAdminPass;

    private LabsSqliteHelper labsSqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;

        userMat = (EditText) findViewById(R.id.user_id);
        userPass = (EditText) findViewById(R.id.user_pass);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                validateUser();
            }
        });
    }

    /**
     * Validates if the login data is correct and starts LaboratoriesActivity.
     */
    void validateUser() {
        final MaterialDialog loginDialog = new MaterialDialog.Builder(this)
                .title("Signing in")
                .content("Please wait...")
                .progress(true, 0)
                .show();

        mAdminId = userMat.getText().toString();
        mAdminPass = userPass.getText().toString();

        if (mAdminId.contains("l0"))
            mAdminId = mAdminId.replace("l0", "L0");

        userMat.setText(mAdminId);

        if (mAdminId.length() == 9) {
            GetAdminInfo getAdminInfo = new GetAdminInfo(getApplicationContext()) {

                @Override
                protected void onPostExecute(Admin admin) {
                    super.onPostExecute(admin);

                    if (admin != null) {
                        loginDialog.dismiss();

                        Intent intent = new Intent(getApplicationContext(), LaboratoriesActivity.class);
                        intent.putExtra("ALLOWEDLABS", admin.getAllowedLabs());
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
                    } else {
                        loginDialog.dismiss();

                        new SnackBar.Builder(mActivity)
                                .withMessage("Matrícula o Contraseña incorrectos.")
                                .withTextColorId(R.color.primary_text_light)
                                .show();
                    }
                }
            };
            getAdminInfo.execute(new String[]{ENDPOINT, mAdminId});
        } else {
            loginDialog.dismiss();

            new SnackBar.Builder(mActivity)
                    .withMessage("Matrícula o Contraseña incorrectos.")
                    .withTextColorId(R.color.primary_text_light)
                    .show();
        }
    }
}