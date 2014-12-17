package com.itesm.labs.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.itesm.labs.R;
import com.itesm.labs.fragments.InventoryFragment;
import com.itesm.labs.fragments.ReportsFragment;
import com.itesm.labs.fragments.RequestDetailFragment;
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UsersFragment;
import com.itesm.labs.models.RequestModel;
import com.itesm.labs.rest.models.Request;


public class DashboardActivity extends Activity implements RequestsFragment.RequestFragmentComm {

    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mDrawerItems = getResources().getStringArray(R.array.drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_dashboard);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        mDrawerListView = (ListView) findViewById(R.id.drawer_list_dashboard);
        mDrawerListView.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerItems));
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentSelector(position);
            }
        });

        if (findViewById(R.id.drawer_layout_dashboard) != null) {
            if (savedInstanceState != null) return;

            RequestsFragment requestsFragment = new RequestsFragment();

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_dashboard, requestsFragment)
                    .commit();
        }
    }

    private void fragmentSelector(int idDrawerItem) {
        switch (idDrawerItem) {
            case 0:
                RequestsFragment mRequestsFragment = new RequestsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mRequestsFragment)
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.fragment_container_dashboard_detail))
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                InventoryFragment mInventoryFragment = new InventoryFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mInventoryFragment)
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.fragment_container_dashboard_detail))
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                ReportsFragment mReportsFragment = new ReportsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mReportsFragment)
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.fragment_container_dashboard_detail))
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                UsersFragment mUsersFragment = new UsersFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_dashboard, mUsersFragment)
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.fragment_container_dashboard_detail))
                        .addToBackStack(null)
                        .commit();
                break;
            default: break;
        }
    }

    @Override
    public void loadNewRequestDetail(Request request) {
        RequestDetailFragment requestDetailFragment = new RequestDetailFragment();
        requestDetailFragment.setmRequestModel(
                new RequestModel(request.getUserName(), request.getUserId(), null));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_dashboard_detail,requestDetailFragment)
                .commit();
    }
}