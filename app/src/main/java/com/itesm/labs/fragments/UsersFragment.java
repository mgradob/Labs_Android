package com.itesm.labs.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.itesm.labs.R;
import com.itesm.labs.activities.AddUserActivity;
import com.itesm.labs.activities.UserDetailActivity;
import com.itesm.labs.adapters.UsersModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseFragment;
import com.itesm.labs.rest.clients.UserClient;
import com.itesm.labs.rest.models.User;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends LabsAppBaseFragment {

    private final static int SIGNUP_USER_REQUEST = 1;

    private final String TAG = UsersFragment.class.getSimpleName();

    @Bind(R.id.fragment_users_list)
    ListView mListView;

    @Bind(R.id.fragment_users_subtoolbar)
    Toolbar mSubToolbar;

    @Bind(R.id.fragment_users_progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.fragment_users_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    UserClient mUserClient;

    private UsersModelAdapter mAdapter;

    private ArrayList<User> mUsersList = new ArrayList<User>();

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUsersList();

        setupToolbar();

        setupRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        getUsersInfo();
    }

    private void setupUsersList() {
        mAdapter = new UsersModelAdapter(mContext, mUsersList);
        mListView.setAdapter(mAdapter);
    }

    private void setupToolbar() {
        mSubToolbar.setTitle("Usuarios");
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsersInfo();
            }
        });
    }

    @OnItemClick(R.id.fragment_users_list)
    void onItemClick(int position) {
        Intent intent = new Intent(mContext, UserDetailActivity.class);
        intent.putExtra("USERNAME", mUsersList.get(position).getFullName());
        intent.putExtra("USERID", mUsersList.get(position).getUserId());
        intent.putExtra("USERCAREER", mUsersList.get(position).getUserCareer());
        intent.putExtra("USERCOLOR", mUsersList.get(position).getUserColor());
        startActivity(intent);
    }

    @OnItemLongClick(R.id.fragment_users_list)
    boolean onItemLongClick(int position) {
        mAppGlobals.setUser(mUsersList.get(position));
        Intent intent = new Intent(mContext, AddUserActivity.class);
        intent.putExtra("ISEDIT", true);
        intent.putExtra("USERNAME", mUsersList.get(position).getUserName());
        intent.putExtra("USERLASTNAME1", mUsersList.get(position).getUserLastName1());
        intent.putExtra("USERLASTNAME2", mUsersList.get(position).getUserLastName2());
        intent.putExtra("USERID", mUsersList.get(position).getUserId());
        intent.putExtra("USERCAREER", mUsersList.get(position).getUserCareer());
        intent.putExtra("USERUID", mUsersList.get(position).getUserUid());
        intent.putExtra("USERLABS", mUsersList.get(position).getAllowedLabs());
        startActivityForResult(intent, SIGNUP_USER_REQUEST);

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fragment_users_menu_add_user:
                Intent intent = new Intent(mContext, AddUserActivity.class);
                intent.putExtra("ISEDIT", false);
                startActivityForResult(intent, SIGNUP_USER_REQUEST);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getUsersInfo();
    }

    private void progressBarEvent(boolean show) {
        if (show) {
            mProgressBar.setIndeterminate(true);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setIndeterminate(false);
            mProgressBar.setVisibility(View.INVISIBLE);

            mRefreshLayout.setRefreshing(false);
        }
    }

    private void getUsersInfo() {
        mUserClient.getUsers(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<User>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get users started");
                        progressBarEvent(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get users completed");
                        progressBarEvent(false);

                        mAdapter.refreshList(mUsersList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get users error: " + e);
                        progressBarEvent(false);

                        // TODO: 11/14/15 add snackbar.
                    }

                    @Override
                    public void onNext(ArrayList<User> users) {
                        int[] colors = mContext.getResources().getIntArray(R.array.material_colors);
                        for (User user : users) {
                            int color = new Random().nextInt(colors.length - 1);
                            user.setUserColor(color);
                        }

                        mUsersList = users;
                    }
                });
    }
}
