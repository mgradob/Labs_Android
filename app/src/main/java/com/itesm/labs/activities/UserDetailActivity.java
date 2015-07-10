package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.adapters.HistoryModelAdapter;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.History;
import com.itesm.labs.rest.queries.CategoryQuery;
import com.itesm.labs.rest.queries.ComponentQuery;
import com.itesm.labs.rest.queries.DetailHistoryQuery;
import com.itesm.labs.util.SwipeDetector;
import com.melnykov.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class UserDetailActivity extends AppCompatActivity {

    private TextView userName, userId, userCareer;
    private ListView userHistoryList;
    private LinearLayout userBackground;

    private Activity mActivity;
    private String ENDPOINT;
    private String mUserId;
    private ArrayList<History> mHistoryList;

    private SwipeDetector mSwipeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        mActivity = this;

        int[] colors = getResources().getIntArray(R.array.material_colors);
        int[] colorsDark = getResources().getIntArray(R.array.material_colors_dark);

        Intent callingIntent = getIntent();
        ENDPOINT = callingIntent.getStringExtra("ENDPOINT");
        userName = (TextView) findViewById(R.id.activity_user_detail_user_name);
        userName.setText(callingIntent.getStringExtra("USERNAME"));
        userId = (TextView) findViewById(R.id.activity_user_detail_user_id);
        mUserId = callingIntent.getStringExtra("USERID");
        userId.setText(mUserId);
        userCareer = (TextView) findViewById(R.id.activity_user_detail_user_career);
        userCareer.setText(callingIntent.getStringExtra("USERCAREER"));
        userBackground = (LinearLayout) findViewById(R.id.activity_user_detail_background);
        userBackground.setBackgroundColor(colors[callingIntent.getIntExtra("USERCOLOR", 0)]);

        Window window = getWindow();
        window.setStatusBarColor(colorsDark[callingIntent.getIntExtra("USERCOLOR", 0)]);

        userHistoryList = (ListView) findViewById(R.id.activity_user_detail_list);
        mSwipeDetector = new SwipeDetector();
        userHistoryList.setOnTouchListener(mSwipeDetector);
        userHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSwipeDetector.swipeDetected()) {
                    if (mSwipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Toast.makeText(mActivity, "Swiped left to right", Toast.LENGTH_SHORT).show();
                        updateHistoryItem(mHistoryList.get(position));
                    }
                }
            }
        });

        getUserHistory();

        window.getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                userBackground.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_top));
                userHistoryList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Animation fabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_bottom);
                fabAnim.setStartOffset(500);
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
                        userBackground.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                userBackground.startAnimation(bkgAnim);
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

    private void getUserHistory(){
        new AsyncTask<Void, Void, ArrayList<History>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<History> doInBackground(Void... params) {
                ArrayList<History> histories = new DetailHistoryQuery(ENDPOINT).getDetailHistoryOf(mUserId);
                for (History history : histories) {
                    Component component = new ComponentQuery(ENDPOINT).getComponent(history.getComponentId());
                    Category category = new CategoryQuery(ENDPOINT).getCategoryOf(component.getCategory());

                    history.setCategoryName(category.getName());
                    history.setComponentNameNote(component.getName() + component.getNote());
                }

                return histories;
            }

            @Override
            protected void onPostExecute(ArrayList<History> histories) {
                super.onPostExecute(histories);

                mHistoryList = histories;

                userHistoryList.setAdapter(new HistoryModelAdapter(getApplicationContext(), mHistoryList));
            }
        }.execute();
    }

    private void updateHistoryItem(final History item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                item.setDateIn(df.format(new Date()));
                new DetailHistoryQuery(ENDPOINT).putDetailHistory(item);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                getUserHistory();
            }
        }.execute();
    }
}
