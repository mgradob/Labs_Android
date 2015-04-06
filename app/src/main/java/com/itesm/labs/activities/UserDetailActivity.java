package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class UserDetailActivity extends ActionBarActivity {

    private TextView userName, userId, userCareer;
    private ListView userHistoryList;
    private FloatingActionButton mFab;
    private LinearLayout userBackgroud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        int[] colors = getResources().getIntArray(R.array.material_colors);
        int[] colorsDark = getResources().getIntArray(R.array.material_colors_dark);

        Intent callingIntent = getIntent();
        userName = (TextView) findViewById(R.id.activity_user_detail_user_name);
        userName.setText(callingIntent.getStringExtra("USERNAME"));
        userId = (TextView) findViewById(R.id.activity_user_detail_user_id);
        userId.setText(callingIntent.getStringExtra("USERID"));
        userCareer = (TextView) findViewById(R.id.activity_user_detail_user_career);
        userCareer.setText(callingIntent.getStringExtra("USERCAREER"));
        userBackgroud = (LinearLayout) findViewById(R.id.activity_user_detail_background);
        userBackgroud.setBackgroundColor(colors[callingIntent.getIntExtra("USERCOLOR", 0)]);

        Window window = getWindow();
        window.setStatusBarColor(colorsDark[callingIntent.getIntExtra("USERCOLOR", 0)]);

        mFab = (FloatingActionButton) findViewById(R.id.activity_user_detail_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mFab.setBackgroundColor(colors[callingIntent.getIntExtra("USERCOLOR", 0)]);

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

        mFab.attachToListView(userHistoryList);

        window.getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                userBackgroud.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_top));
                mFab.setVisibility(View.INVISIBLE);
                userHistoryList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Animation fabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_bottom);
                fabAnim.setStartOffset(500);
                mFab.startAnimation(fabAnim);
                mFab.setVisibility(View.VISIBLE);
                userHistoryList.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_from_top));
                userHistoryList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        mFab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_bottom));
        mFab.setVisibility(View.INVISIBLE);
        Animation listAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_top);
        listAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                userHistoryList.setVisibility(View.INVISIBLE);

                Animation bkgAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_top);
                bkgAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        userBackgroud.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                userBackgroud.startAnimation(bkgAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        userHistoryList.startAnimation(listAnim);
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
