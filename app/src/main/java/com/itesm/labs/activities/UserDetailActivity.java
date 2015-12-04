package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.adapters.HistoryModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.DetailHistoryClient;
import com.itesm.labs.rest.models.History;
import com.itesm.labs.utils.SwipeDetector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.DataFormatException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UserDetailActivity extends LabsAppBaseActivity {

    private static final String TAG = UserDetailActivity.class.getSimpleName();

    @Bind(R.id.activity_user_detail_user_name)
    TextView mUserDetailUserName;
    @Bind(R.id.activity_user_detail_user_id)
    TextView mUserDetailUserId;
    @Bind(R.id.activity_user_detail_user_career)
    TextView mUserDetailUserCareer;
    @Bind(R.id.activity_user_detail_background)
    LinearLayout mUserDetailBackground;
    @Bind(R.id.activity_user_detail_list)
    ListView mUserDetailList;

    @Inject
    DetailHistoryClient mDetailHistoryClient;

    private String mUserId;

    private HistoryModelAdapter mAdapter;

    private ArrayList<History> mHistoryList = new ArrayList<>();

    private SwipeDetector mSwipeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ButterKnife.bind(this);

        setupWithIntent(getIntent());

        setupDetailList();

        setupSwipeDetector();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserHistory();
    }

    private void setupWithIntent(Intent callingIntent) {
        int[] colors = getResources().getIntArray(R.array.material_colors);
        int[] colorsDark = getResources().getIntArray(R.array.material_colors_dark);

        mUserDetailUserName.setText(callingIntent.getStringExtra("USERNAME"));
        mUserId = callingIntent.getStringExtra("USERID");
        mUserDetailUserId.setText(mUserId);
        mUserDetailUserCareer.setText(callingIntent.getStringExtra("USERCAREER"));
        mUserDetailBackground.setBackgroundColor(colors[callingIntent.getIntExtra("USERCOLOR", 0)]);

        Window window = getWindow();
        window.setStatusBarColor(colorsDark[callingIntent.getIntExtra("USERCOLOR", 0)]);
    }

    private void setupDetailList() {
        mAdapter = new HistoryModelAdapter(mContext);
        mUserDetailList.setAdapter(mAdapter);
    }

    private void setupSwipeDetector() {
        mSwipeDetector = new SwipeDetector();
        mUserDetailList.setOnTouchListener(mSwipeDetector);
        mUserDetailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSwipeDetector.swipeDetected()) {
                    if (mSwipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Toast.makeText(mContext, "Swiped left to right", Toast.LENGTH_SHORT).show();
                        updateHistoryItem(mHistoryList.get(position));
                    }
                }
            }
        });
    }

    private void getUserHistory() {
        mDetailHistoryClient.getDetailHistoryOf(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mUserId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<History>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get user history started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get user history completed");

                        mAdapter.refreh(mHistoryList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get user history error: " + e.getMessage());

                        // TODO: 11/17/15 snackbar
                    }

                    @Override
                    public void onNext(ArrayList<History> histories) {
                        if (histories == null)
                            throw new NullPointerException("Histories is null");

                        mHistoryList = histories;
                    }
                });
    }

    private void updateHistoryItem(final History item) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = df.format(new Date());

        item.setDateIn(date);

        mDetailHistoryClient.putDetailHistoryItem(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), item.getHistoryId(), item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task put user history started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task put user history completed");

                        Snackbar.make(findViewById(R.id.activity_user_detail), getString(R.string.users_put_history_ok), Snackbar.LENGTH_SHORT)
                                .show();

//                        getUserHistory();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task put user history error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_user_detail), getString(R.string.users_put_history_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
}
