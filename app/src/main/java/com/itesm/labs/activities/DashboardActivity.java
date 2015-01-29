package com.itesm.labs.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.fragments.InventoryFragment;
import com.itesm.labs.fragments.ReportsFragment;
import com.itesm.labs.fragments.RequestDetailFragment;
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UserDetailFragment;
import com.itesm.labs.fragments.UsersFragment;
import com.itesm.labs.rest.models.Request;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.util.NfcHandler;


public class DashboardActivity extends ActionBarActivity
        implements RequestsFragment.RequestFragmentComm,
        UsersFragment.UsersFragmentComm,
        RequestDetailFragment.NfcCommunication {

    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar dashboardToolbar;
    private String ENDPOINT, LAB_NAME;
    private RelativeLayout mDrawerRelativeLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private TextView mDrawerTitle;

    // Fragments
    RequestsFragment mRequestsFragment;
    InventoryFragment mInventoryFragment;
    ReportsFragment mReportsFragment;
    UsersFragment mUsersFragment;
    private final String mRequestsFragmentTag = "REQUESTS_FRAGMENT";
    private final String mInventoryFragmentTag = "INVENTORY_FRAGMENT";
    private final String mReportsFragmentTag = "REPORTS_FRAGMENT";
    private final String mUsersFragmentTag = "USERS_FRAGMENT";

    NfcHandler nfcHandler;
    private String UID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        ENDPOINT = intent.getStringExtra("ENDPOINT");
        LAB_NAME = intent.getStringExtra("LAB_NAME");

        dashboardToolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(dashboardToolbar);

        mDrawerItems = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dashboard_drawer_layout);
        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.dashboard_drawer);
        mDrawerTitle = (TextView) findViewById(R.id.drawer_lab_title);
        mDrawerTitle.setText(LAB_NAME);
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

        nfcHandler = new NfcHandler(this);
    }

    private void fragmentSelector(int idDrawerItem) {
        switch (idDrawerItem) {
            case 0:
                mRequestsFragment = new RequestsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mRequestsFragment, mRequestsFragmentTag)
                        .commit();
                break;
            case 1:
                mInventoryFragment = new InventoryFragment();
                mInventoryFragment.setENDPOINT(ENDPOINT);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mInventoryFragment, mInventoryFragmentTag)
                        .commit();
                break;
            case 2:
                mReportsFragment = new ReportsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mReportsFragment, mReportsFragmentTag)
                        .commit();
                break;
            case 3:
                mUsersFragment = new UsersFragment();
                mUsersFragment.setENDPOINT(ENDPOINT);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mUsersFragment, mUsersFragmentTag)
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
    protected void onSaveInstanceState(Bundle outState) {
        if (getFragmentManager().findFragmentByTag(mRequestsFragmentTag) != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction
                    .remove(getFragmentManager().findFragmentByTag(mRequestsFragmentTag))
                    .commit();
        }
        if (getFragmentManager().findFragmentByTag(mUsersFragmentTag) != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction
                    .remove(getFragmentManager().findFragmentByTag(mUsersFragmentTag))
                    .commit();
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            if (!nfcHandler.getmNfcAdapter().isEnabled())
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    //region RequestFragment.RequestFragmentComm interface methods.
    @Override
    public void loadNewRequestDetail(Request request) {
        RequestDetailFragment requestDetailFragment = new RequestDetailFragment();
        requestDetailFragment.setRequest(request);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_requests_detail_container, requestDetailFragment, mRequestsFragmentTag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(requestDetailFragment)
                .commit();
    }
    //endregion

    //region UsersFragment.UsersFragmentCommunication interface methods.
    @Override
    public void loadNewUsersDetail(User user, int colorCode) {
        UserDetailFragment userDetailFragment = new UserDetailFragment();
        userDetailFragment.setUser(user);
        userDetailFragment.setColorCode(colorCode);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_users_detail_container, userDetailFragment, mUsersFragmentTag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(userDetailFragment)
                .commit();
    }
    //endregion

    //region RequestDetailFragment.NfcCommunication interface methods.
    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void resetUid() {
        UID = "";
    }
    //endregion

    //region NfcHandler methods.
    @Override
    public void onNewIntent(Intent intent) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            UID = nfcHandler.bytesToHex(tag.getId());
        }
    }
    //endregion
}