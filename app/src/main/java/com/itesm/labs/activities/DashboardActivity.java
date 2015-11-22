package com.itesm.labs.activities;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.fragments.InventoryFragment;
import com.itesm.labs.fragments.ReportsFragment;
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UsersFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DashboardActivity extends LabsAppBaseActivity {

    private final String mRequestsFragmentTag = "REQUESTS_FRAGMENT";
    private final String mInventoryFragmentTag = "INVENTORY_FRAGMENT";
    private final String mReportsFragmentTag = "REPORTS_FRAGMENT";
    private final String mUsersFragmentTag = "USERS_FRAGMENT";

    @Bind(R.id.dashboard_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.dashboard_toolbar)
    Toolbar mDashboardToolbar;

    @Bind(R.id.dashboard_navigation_view)
    NavigationView mNavigationView;

    // Fragments
    RequestsFragment mRequestsFragment = new RequestsFragment();
    InventoryFragment mInventoryFragment = new InventoryFragment();
    ReportsFragment mReportsFragment = new ReportsFragment();
    UsersFragment mUsersFragment = new UsersFragment();

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

        setupActionBar();

        setupNavigationView();

        setupDrawer();

        setupRestoreInstance(savedInstanceState);

        setupNfcAdapter();
    }

    private void setupActionBar() {
        setSupportActionBar(mDashboardToolbar);

        getSupportActionBar().setTitle(null);
    }

    private void setupNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                fragmentSelector(menuItem);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void setupDrawer() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mDashboardToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mActionBarDrawerToggle.syncState();
    }

    private void setupRestoreInstance(Bundle savedInstanceState) {
        if (findViewById(R.id.dashboard_drawer_layout) != null) {
            if (savedInstanceState != null) return;

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.dashboard_fragment_container, mRequestsFragment, mRequestsFragmentTag)
                    .commit();
        }
    }

    private void setupNfcAdapter() {
        try {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (!nfcAdapter.isEnabled())
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        } catch (Exception ex) {
            Toast.makeText(this, "No NFC Adapter available", Toast.LENGTH_SHORT).show();
        }
    }

    private void fragmentSelector(MenuItem drawerItem) {
        switch (drawerItem.getItemId()) {
            case R.id.navigation_item_requests:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dashboard_fragment_container, mRequestsFragment, mRequestsFragmentTag)
                        .commit();
                break;
            case R.id.navigation_item_inventory:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dashboard_fragment_container, mInventoryFragment, mInventoryFragmentTag)
                        .commit();
                break;
            case R.id.navigation_item_reports:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dashboard_fragment_container, mReportsFragment, mReportsFragmentTag)
                        .commit();
                break;
            case R.id.navigation_item_users:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dashboard_fragment_container, mUsersFragment, mUsersFragmentTag)
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