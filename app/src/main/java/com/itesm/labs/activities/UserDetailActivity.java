package com.itesm.labs.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.animations.RevealAnimation;
import com.itesm.labs.fragments.AddMaterialFragment;

import java.util.ArrayList;


public class UserDetailActivity extends ActionBarActivity {

    private TextView userName, userId, userCareer;
    private ListView userHistoryList;
    private ImageButton mFab;
    private View userBackgroud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        userName = (TextView) findViewById(R.id.activity_user_detail_user_name);
        userName.setText(getIntent().getStringExtra("USERNAME"));
        userId = (TextView) findViewById(R.id.activity_user_detail_user_id);
        userId.setText(getIntent().getStringExtra("USERID"));
        userCareer = (TextView) findViewById(R.id.activity_user_detail_user_career);
        userCareer.setText(getIntent().getStringExtra("USERCAREER"));
        userBackgroud = findViewById(R.id.activity_user_detail_background);
        userBackgroud.setBackgroundColor(getIntent().getIntExtra(
                        "USERCOLOR",
                        getResources().getColor(R.color.material_teal))
        );

        Window window = getWindow();
        window.setStatusBarColor(getIntent().getIntExtra("USERCOLOR", R.color.primary_dark) - 0x0f1216);

        mFab = (ImageButton) findViewById(R.id.activity_user_detail_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMaterial(v);
            }
        });

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

        userHistoryList = (ListView) findViewById(R.id.activity_user_detail_list);
        userHistoryList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
    }

    /**
     * Replaces the ListView with the layout for adding new users and sets transition animations for
     * the replacement.
     */
    private void showAddMaterial(final View view) {
        FrameLayout listLayoutFrame = (FrameLayout) findViewById(R.id.activity_user_detail_list_frame);
        listLayoutFrame.removeAllViews();

        AddMaterialFragment addMaterialFragment = new AddMaterialFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.activity_user_detail_list_frame, addMaterialFragment, "ADD_MATERIAL_FRAGMENT")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .show(addMaterialFragment)
                .commit();

        RevealAnimation revealAnimation = new RevealAnimation(view);
        revealAnimation.unvealFromCenter(200, 100);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
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
