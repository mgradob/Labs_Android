package com.itesm.labs.bases;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itesm.labs.application.AppGlobals;
import com.itesm.labs.application.LabsAdminApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 11/2/15.
 */
public abstract class LabsAppBaseActivity extends AppCompatActivity {

    @Inject
    public Context mContext;

    @Inject
    public SharedPreferences mSharedPreferences;

    @Inject
    public AppGlobals mAppGlobals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsAdminApp.get().inject(this);
    }


}
