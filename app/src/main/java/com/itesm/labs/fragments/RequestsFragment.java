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
import com.itesm.labs.activities.RequestDetailActivity;
import com.itesm.labs.adapters.RequestsModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseFragment;
import com.itesm.labs.rest.clients.CartClient;
import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.services.UserService;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends LabsAppBaseFragment {

    private final static int REQUEST_DETAIL_REQUEST = 1;
    private final String TAG = RequestsFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_list)
    ListView mListView;

    @Bind(R.id.fragment_requests_subtoolbar)
    Toolbar mSubtoolbar;

    @Bind(R.id.fragment_requests_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    CartClient mCartClient;

    @Inject
    UserService mUserService;

    private RequestsModelAdapter mAdapter;

    private ArrayList<Cart> cartsList = new ArrayList<>();

    public RequestsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSubToolbar();

        setupRequestsList();

        setupRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        getRequests();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getRequests();
    }

    @OnItemClick(R.id.fragment_requests_list)
    void onCartClicked(int position) {
        mAppGlobals.setCart(cartsList.get(position));

        Intent intent = new Intent(mContext, RequestDetailActivity.class);
        intent.putExtra("USERNAME", cartsList.get(position).getUserName());
        intent.putExtra("USERID", cartsList.get(position).getUserId());
        intent.putExtra("REQUESTSTATUS", cartsList.get(position).isReady());
        startActivityForResult(intent, REQUEST_DETAIL_REQUEST);
    }

    private void setupSubToolbar() {
        mSubtoolbar.setTitle(getResources().getString(R.string.requests_fragment_title));
    }

    private void setupRequestsList() {
        mAdapter = new RequestsModelAdapter(mContext, cartsList);
        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRequests();
            }
        });
    }

    private void getRequests() {
        mCartClient.getCarts(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Cart>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get requests started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get requests completed");

                        mAdapter.refreshList(cartsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get requests error" + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Cart> carts) {
                        if (carts == null)
                            throw new NullPointerException("CartItems was null");

                        cartsList.clear();
                        cartsList = carts;
                    }
                });
    }
}
