package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.itesm.labs.R;
import com.itesm.labs.adapters.LaboratoriesModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.LaboratoryClient;
import com.itesm.labs.rest.models.Laboratory;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LaboratoriesActivity extends LabsAppBaseActivity {

    private final String TAG = LaboratoriesActivity.class.getSimpleName();

    @Bind(R.id.laboratories_list)
    ListView mListView;

    @Bind(R.id.laboratories_progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.laboratories_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.laboratories_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    LaboratoryClient mLaboratoryClient;

    private LaboratoriesModelAdapter mAdapter;

    private ArrayList<Laboratory> mLabsModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratories);

        ButterKnife.bind(this);

        setupLabsList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getLabs();
    }

    private void setupLabsList() {
        mAdapter = new LaboratoriesModelAdapter(mContext, mLabsModelsList);
        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    private void reload() {
        mAdapter.refreshList(mLabsModelsList);
    }

    private void getLabs() {
        mLaboratoryClient.getLaboratories(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Laboratory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get labs started");
                        eventProgressbar(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get labs completed");
                        eventProgressbar(false);

                        reload();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get labs error");
                        eventProgressbar(false);

                        Snackbar.make(findViewById(R.id.activity_laboratories),
                                getResources().getString(R.string.activity_labs_snackbar_get_labs_error),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(ArrayList<Laboratory> laboratories) {
                        if (laboratories == null)
                            throw new NullPointerException("Laboratories are null");

                        for (Laboratory laboratory : laboratories) {
                            laboratory.setImageResource(R.drawable.ic_dummy_category);
                        }

                        mLabsModelsList = laboratories;
                    }
                });
    }

    @OnItemClick(R.id.laboratories_list)
    void goToLabDashboard(int position) {
        mAppGlobals.setLaboratory(mLabsModelsList.get(position));
        mAppGlobals.setLabLink(Laboratory.extractLabEndpoint(mAppGlobals.getLaboratory().getLink()));

        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
    }

    private void eventProgressbar(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setIndeterminate(true);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mProgressBar.setIndeterminate(false);
            mProgressBar.setProgress(0);

            mRefreshLayout.setRefreshing(false);
        }
    }
}