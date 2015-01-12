package com.itesm.labs.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;

import java.util.ArrayList;

public class RequestDetailActivity extends ActionBarActivity {

    private TextView userName, userId;
    private ListView userRequestList;
    private ImageButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        userName = (TextView) findViewById(R.id.activity_request_detail_user_name);
        userName.setText(getIntent().getStringExtra("USERNAME"));
        userId = (TextView) findViewById(R.id.activity_request_detail_user_id);
        userId.setText(getIntent().getStringExtra("USERID"));

        mFab = (ImageButton) findViewById(R.id.activity_request_detail_fab);

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

        userRequestList = (ListView) findViewById(R.id.activity_request_detail_list);
        userRequestList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
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
}
