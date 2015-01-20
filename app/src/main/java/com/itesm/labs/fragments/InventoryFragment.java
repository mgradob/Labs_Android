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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.activities.InventoryDetailActivity;
import com.itesm.labs.adapters.CategoriesModelAdapter;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.models.CategoryModel;
import com.itesm.labs.models.ComponentModel;
import com.itesm.labs.rest.deserializers.CategoryDeserializer;
import com.itesm.labs.rest.deserializers.ComponentDeserializer;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.CategoryWrapper;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.ComponentWrapper;
import com.itesm.labs.rest.service.CategoryService;
import com.itesm.labs.rest.service.ComponentService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {

    ProgressBar mProgressBar;
    private ListView categoriesListView, componentsListView;
    private ArrayList<CategoryModel> categoriesData;
    private ArrayList<ComponentModel> componentsData;
    private String ENDPOINT;
    private Context context;
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

        context = view.getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_inventory_progressbar);
        mProgressBar.setIndeterminate(true);

        new getCategoriesInfo().execute();

        categoriesListView = (ListView) view.findViewById(R.id.fragment_inventory_categories_list);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    new getComponentInfo().execute(categoriesData.get(position).getIdApi());
                } else {
                    Intent intent = new Intent(context, InventoryDetailActivity.class);
                    intent.putExtra("CATEGORYID", categoriesData.get(position).getIdApi());
                    intent.putExtra("CATEGORYTITLE", categoriesData.get(position).getTitle());
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

    private class getCategoriesInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(CategoryWrapper.class, new CategoryDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            CategoryService service = restAdapter.create(CategoryService.class);

            CategoryWrapper categoryWrapper = service.getCategories();

            categoriesData = new ArrayList<>();

            for (Category category : categoryWrapper.categoryList) {
                CategoryModel mCategoryModel = new CategoryModel(category.name, category.id, R.drawable.rounded_letter);

                if (category.id == 0) mCategoryModel.setImageResource(R.drawable.ic_resistencias);
                else if (category.id == 1) mCategoryModel.setImageResource(R.drawable.ic_capacitores);
                else if (category.id == 2) mCategoryModel.setImageResource(R.drawable.ic_equipo);
                else if (category.id == 3) mCategoryModel.setImageResource(R.drawable.ic_kits);
                else if (category.id == 4) mCategoryModel.setImageResource(R.drawable.ic_cables);
                else if (category.id == 5) mCategoryModel.setImageResource(R.drawable.ic_integrados);
                else if (category.id == 6) mCategoryModel.setImageResource(R.drawable.ic_diodos);
                else if (category.id == 7) mCategoryModel.setImageResource(R.drawable.ic_herramientas);
                else if (category.id == 8) mCategoryModel.setImageResource(R.drawable.ic_switches);
                else if (category.id == 9) mCategoryModel.setImageResource(R.drawable.ic_adaptadores);
                else if (category.id == 10) mCategoryModel.setImageResource(R.drawable.ic_displays);
                else if (category.id == 11) mCategoryModel.setImageResource(R.drawable.ic_inductores);
                else if (category.id == 12) mCategoryModel.setImageResource(R.drawable.ic_sensores);
                else if (category.id == 13) mCategoryModel.setImageResource(R.drawable.ic_motores);
                else if (category.id == 14) mCategoryModel.setImageResource(R.drawable.ic_potenciometro);
                else if (category.id == 15) mCategoryModel.setImageResource(R.drawable.ic_transformadores);
                else if (category.id == 16) mCategoryModel.setImageResource(R.drawable.ic_transistores);

                categoriesData.add(mCategoryModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(View.INVISIBLE);

            categoriesListView.setAdapter(new CategoriesModelAdapter(context, categoriesData));
        }
    }

    private class getComponentInfo extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ComponentWrapper.class, new ComponentDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            ComponentService service = restAdapter.create(ComponentService.class);

            //ComponentWrapper componentWrapper = service.getComponents(params[0]);
            ComponentWrapper componentWrapper = service.getComponents();

            componentsData = new ArrayList<>();

            for (Component component : componentWrapper.componentList) {
                ComponentModel mComponentModel = new ComponentModel(
                        component.id,
                        component.name,
                        component.note,
                        component.total,
                        component.available,
                        R.drawable.rounded_letter
                );

                /*if (category.name.equals("Resistencia")) mCategoryModel.setImageResource(R.drawable.ic_resistencia);
                else if (category.name.equals("Capacitor")) mCategoryModel.setImageResource(R.drawable.ic_capacitores);
                else if (category.name.equals("Equipo")) mCategoryModel.setImageResource(R.drawable.ic_equipo);
                else if (category.name.equals("Kit")) mCategoryModel.setImageResource(R.drawable.ic_kits);*/

                componentsData.add(mComponentModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(View.INVISIBLE);

            componentsListView.setAdapter(new ComponentModelAdapter(context, componentsData));
        }
    }
}
