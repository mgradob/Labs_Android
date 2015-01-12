package com.itesm.labs.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.CategoriesModelAdapter;
import com.itesm.labs.models.CategoryModel;
import com.itesm.labs.rest.deserializers.CategoryDeserializer;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.CategoryWrapper;
import com.itesm.labs.rest.service.CategoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class CategoriesActivity extends ActionBarActivity {

    public String ENDPOINT;

    GridView mGridView;
    ArrayList<CategoryModel> mCategoryModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Intent intent = getIntent();
        ENDPOINT = intent.getStringExtra("ENDPOINT");

        mGridView = (GridView) findViewById(R.id.categories_list);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CategoriesActivity.this, "position: " + position + " , id: " + id, Toast.LENGTH_SHORT).show();
            }
        });

        new getDbInfo().execute();
    }

    private class getDbInfo extends AsyncTask<Void, Void, Void> {

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

            mCategoryModelList = new ArrayList<CategoryModel>();

            for (Category category : categoryWrapper.categoryList) {
                CategoryModel mCategoryModel = new CategoryModel(category.name, category.id, R.drawable.ic_dummy_category);

                /*if (category.name.equals("Resistencia")) mCategoryModel.setImageResource(R.drawable.ic_resistencia);
                else if (category.name.equals("Capacitor")) mCategoryModel.setImageResource(R.drawable.ic_capacitores);
                else if (category.name.equals("Equipo")) mCategoryModel.setImageResource(R.drawable.ic_equipo);
                else if (category.name.equals("Kit")) mCategoryModel.setImageResource(R.drawable.ic_kits);*/

                mCategoryModelList.add(mCategoryModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mGridView.setAdapter(new CategoriesModelAdapter(CategoriesActivity.this, mCategoryModelList));
        }
    }
}