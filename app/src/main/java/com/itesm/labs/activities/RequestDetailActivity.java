package com.itesm.labs.activities;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.util.NfcHandler;
import com.itesm.labs.util.NfcHandler.UidCallback;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class RequestDetailActivity extends ActionBarActivity implements UidCallback {

    private TextView userName, userId;
    private ListView userRequestList;
    private FloatingActionButton mFab;
    private boolean mRequestStatus;
    private String UID;

    public NfcHandler nfcHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        userName = (TextView) findViewById(R.id.activity_request_detail_user_name);
        userName.setText(getIntent().getStringExtra("USERNAME"));
        userId = (TextView) findViewById(R.id.activity_request_detail_user_id);
        userId.setText(getIntent().getStringExtra("USERID"));
        mRequestStatus = getIntent().getBooleanExtra("STATUS", false);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");
        list.add("Resistencia");
        list.add("Capacitor");
        list.add("Inductor");
        list.add("PIC16F877A");

        userRequestList = (ListView) findViewById(R.id.activity_request_detail_list);
        userRequestList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));

        mFab = (FloatingActionButton) findViewById(R.id.activity_request_detail_fab);
        mFab.attachToListView(userRequestList);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRequest(mRequestStatus);
            }
        });

        if (!mRequestStatus)
            mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white));
        else
            mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_uid_white));

        nfcHandler = new NfcHandler(this);
        enableReader();
    }

    private void validateRequest(Boolean requestStatus) {
        if (requestStatus)
            Toast.makeText(this, "UID: " + UID, Toast.LENGTH_SHORT).show();
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
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(activity, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getUid(String uid) {
        this.UID = uid;
    }
}
