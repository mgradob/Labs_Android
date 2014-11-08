package com.itesm.labs;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.adapters.CategoriesInformationAdapter;
import com.itesm.labs.adapters.ImageAdapter;
import com.itesm.labs.content.CategoriesContent;
import com.itesm.labs.models.CategoryInformation;
import com.itesm.labs.rest.deserializers.CategoryDeserializer;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.CategoryWrapper;
import com.itesm.labs.rest.service.CategoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class MainActivity extends Activity {

    public String ENDPOINT = "http://labs.chi.itesm.mx:8080";

    GridView mGridView;
    ArrayList<CategoryInformation> mCategoryInformationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.category_list);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position: " + position + " , id: " + id, Toast.LENGTH_SHORT).show();

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

            for (Category category : categoryWrapper.categoryList)
                mCategoryInformationList.add(new CategoryInformation(category.name, R.drawable.ic_dummy_category));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mGridView.setAdapter(new CategoriesInformationAdapter(MainActivity.this, mCategoryInformationList));
        }
    }
}