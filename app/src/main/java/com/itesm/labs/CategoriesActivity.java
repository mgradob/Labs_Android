package com.itesm.labs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.adapters.CategoriesInformationAdapter;
import com.itesm.labs.models.CategoryInformation;
import com.itesm.labs.rest.deserializers.CategoryDeserializer;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.CategoryWrapper;
import com.itesm.labs.rest.service.CategoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class CategoriesActivity extends ActionBarActivity {

    public String ENDPOINT = "http://labs.chi.itesm.mx:8080";

    GridView mGridView;
    ArrayList<CategoryInformation> mCategoryInformationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = (Toolbar) findViewById(R.id.categories_toolbar);
        setSupportActionBar(toolbar);

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

            mCategoryInformationList = new ArrayList<CategoryInformation>();

            for (Category category : categoryWrapper.categoryList) {
                CategoryInformation mCategoryInformation = new CategoryInformation(category.name, R.drawable.ic_test_icon);

                if (category.name.equals("Resistencia")) mCategoryInformation.setImageResource(R.drawable.ic_resistencia);
                else if (category.name.equals("Capacitor")) mCategoryInformation.setImageResource(R.drawable.ic_capacitores);
                else if (category.name.equals("Equipo")) mCategoryInformation.setImageResource(R.drawable.ic_equipo);
                else if (category.name.equals("Kit")) mCategoryInformation.setImageResource(R.drawable.ic_kits);
                else if (category.name.equals("Cables")) mCategoryInformation.setImageResource(R.drawable.cables);

                mCategoryInformationList.add(mCategoryInformation);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mGridView.setAdapter(new CategoriesInformationAdapter(CategoriesActivity.this, mCategoryInformationList));
        }
    }
}