package com.itesm.labs.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.itesm.labs.R;
import com.itesm.labs.fragments.InventoryFragment;
import com.itesm.labs.fragments.ReportsFragment;
import com.itesm.labs.fragments.RequestDetailFragment;
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UserDetailFragment;
import com.itesm.labs.fragments.UsersFragment;
import com.itesm.labs.models.RequestModel;
import com.itesm.labs.models.UserModel;
import com.itesm.labs.rest.models.Request;


public class DashboardActivity extends ActionBarActivity
        implements RequestsFragment.RequestFragmentComm, UsersFragment.UsersFragmentComm {

    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar dashboardToolbar;
    private String ENDPOINT, LAB_NAME;
    private RelativeLayout mDrawerRelativeLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    // Fragments
    RequestsFragment mRequestsFragment;
    InventoryFragment mInventoryFragment;
    ReportsFragment mReportsFragment;
    UsersFragment mUsersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBarDrawerToggle toggle;

        Intent intent = getIntent();
        ENDPOINT = intent.getStringExtra("ENDPOINT");
        LAB_NAME = intent.getStringExtra("LAB_NAME");

        mDrawerItems = getResources().getStringArray(R.array.drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dashboard_drawer_layout);

        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.dashboard_drawer);

        dashboardToolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(dashboardToolbar);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                dashboardToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mActionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(null);

        mDrawerListView = (ListView) findViewById(R.id.dashboard_drawer_list);
        mDrawerListView.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerItems));
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentSelector(position);
                mDrawerLayout.closeDrawer(mDrawerRelativeLayout);
            }
        });

        if (findViewById(R.id.dashboard_drawer_layout) != null) {
            if (savedInstanceState != null) return;

            mRequestsFragment = new RequestsFragment();

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_dashboard, mRequestsFragment)
                    .commit();
        }
    }

    private void fragmentSelector(int idDrawerItem) {
        switch (idDrawerItem) {
            case 0:
                mRequestsFragment = new RequestsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mRequestsFragment)
                        .commit();
                break;
            case 1:
                mInventoryFragment = new InventoryFragment();
                mInventoryFragment.setENDPOINT(ENDPOINT);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mInventoryFragment)
                        .commit();
                break;
            case 2:
                mReportsFragment = new ReportsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mReportsFragment)
                        .commit();
                break;
            case 3:
                mUsersFragment = new UsersFragment();
                mUsersFragment.setENDPOINT(ENDPOINT);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mUsersFragment)
                        .commit();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void loadNewRequestDetail(Request request) {
        RequestDetailFragment requestDetailFragment = new RequestDetailFragment();
        requestDetailFragment.setmRequestModel(
                new RequestModel(request.getUserName(), request.getUserId(), null));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_requests_detail_container, requestDetailFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(requestDetailFragment)
                .commit();
    }

    @Override
    public void loadNewUsersDetail(UserModel user, int colorCode) {
        UserDetailFragment userDetailFragment = new UserDetailFragment();
        userDetailFragment.setUserModel(user);
        userDetailFragment.setColorCode(colorCode);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_users_detail_container, userDetailFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(userDetailFragment)
                .commit();
    }
}