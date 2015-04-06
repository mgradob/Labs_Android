package com.itesm.labs.activities;

import android.content.Intent;
import android.nfc.NfcAdapter;
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
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UsersFragment;


public class DashboardActivity extends ActionBarActivity {

    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar dashboardToolbar;
    private String ENDPOINT, LAB_NAME;
    private String USERS_ENDPOINT = "http://labs.chi.itesm.mx:8080/";
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

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!nfcAdapter.isEnabled())
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
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
                mUsersFragment.setENDPOINT(USERS_ENDPOINT);
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
        super.onSaveInstanceState(outState);
    }
}