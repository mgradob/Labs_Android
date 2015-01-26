package com.itesm.labs.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.LaboratoriesModelAdapter;
import com.itesm.labs.rest.deserializers.LaboratoryDeserializer;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.models.LaboratoryWrapper;
import com.itesm.labs.rest.service.LaboratoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class LaboratoriesActivity extends ActionBarActivity {

    public String ENDPOINT = "http://labs.chi.itesm.mx:8080";

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private ArrayList<Laboratory> mLaboratoryModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratories);

        mGridView = (GridView) findViewById(R.id.laboratories_list);
        mProgressBar = (ProgressBar) findViewById(R.id.laboratories_progressbar);
        mProgressBar.setIndeterminate(true);

        mToolbar = (Toolbar) findViewById(R.id.laboratories_toolbar);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                intent.putExtra("ENDPOINT", mLaboratoryModelsList.get(position).getUrl());
                intent.putExtra("LAB_NAME", mLaboratoryModelsList.get(position).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
            }
        });

        new getDbInfo().execute();
    }

    private class getDbInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LaboratoryWrapper.class, new LaboratoryDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            LaboratoryService service = restAdapter.create(LaboratoryService.class);

            LaboratoryWrapper laboratoryWrapper = service.getLaboratories();

            mLaboratoryModelsList = new ArrayList<Laboratory>();

            for (Laboratory laboratory : laboratoryWrapper.laboratoryList) {
                laboratory.setImageResource(R.drawable.ic_dummy_category);
                mLaboratoryModelsList.add(laboratory);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProgressBar.setVisibility(View.INVISIBLE);

            mGridView.setAdapter(new LaboratoriesModelAdapter(LaboratoriesActivity.this, mLaboratoryModelsList));
        }
    }
}
