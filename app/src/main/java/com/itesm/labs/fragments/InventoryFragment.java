package com.itesm.labs.fragments;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.activities.InventoryDetailActivity;
import com.itesm.labs.adapters.CategoriesModelAdapter;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.async_tasks.GetCategoriesInfo;
import com.itesm.labs.async_tasks.GetComponentsInfo;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.service.ComponentService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {

    ProgressBar mProgressBar;
    private ListView categoriesListView, componentsListView;
    private ArrayList<Category> categoriesData;
    private ArrayList<Component> componentsData;
    private String ENDPOINT;
    private Context mContext;
    private Toolbar mSubtoolbar;

    public InventoryFragment() {
        // Required empty public constructor
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (savedInstanceState != null) setENDPOINT(savedInstanceState.getString("ENDPOINT"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_inventory_progressbar);
        mProgressBar.setIndeterminate(true);
        categoriesListView = (ListView) view.findViewById(R.id.fragment_inventory_categories_list);

        try {
            categoriesData = new GetCategoriesInfo().execute(ENDPOINT).get();
            categoriesListView.setAdapter(new CategoriesModelAdapter(mContext, categoriesData));
        } catch (ExecutionException | InterruptedException ex){
            Toast.makeText(mContext, "Unable to get the data", Toast.LENGTH_SHORT).show();
        }

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    try {
                        componentsData = new GetComponentsInfo().execute(ENDPOINT).get();
                        componentsListView.setAdapter(new ComponentModelAdapter(mContext, componentsData));
                    } catch (ExecutionException | InterruptedException ex){
                        Toast.makeText(mContext, "Unable to get the data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(mContext, InventoryDetailActivity.class);
                    intent.putExtra("CATEGORYID", categoriesData.get(position).getId());
                    intent.putExtra("CATEGORYTITLE", categoriesData.get(position).getName());
                    intent.putExtra("CATEGORYICON", categoriesData.get(position).getImageResource());
                    intent.putExtra("ENDPOINT", ENDPOINT);
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                            getActivity(),
                            Pair.create(view.findViewById(R.id.inventory_item_category_icon), getResources().getString(R.string.inventory_fragment_transition_icon)),
                            Pair.create(view.findViewById(R.id.inventory_item_category_text), getResources().getString(R.string.inventory_fragment_transition_name))
                    );
                    startActivity(intent, activityOptions.toBundle());
                }
            }
        });

        componentsListView = (ListView) view.findViewById(R.id.fragment_inventory_components_list);

        mSubtoolbar = (Toolbar) view.findViewById(R.id.fragment_inventory_subtoolbar);
        mSubtoolbar.setTitle("Inventario");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ENDPOINT", ENDPOINT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_inventory, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inventory_menu_settings:
                break;
        }
        return true;
    }
}
