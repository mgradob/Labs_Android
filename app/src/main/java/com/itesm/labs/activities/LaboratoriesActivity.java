package com.itesm.labs.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.LaboratoriesModelAdapter;
import com.itesm.labs.async_tasks.GetLabsInfo;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.service.LaboratoryService;

import org.apache.http.protocol.ExecutionContext;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

        try {
            mLaboratoryModelsList = new GetLabsInfo().execute(ENDPOINT).get();

            mGridView.setAdapter(new LaboratoriesModelAdapter(getApplicationContext(), mLaboratoryModelsList));

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
        } catch (ExecutionException | InterruptedException ex) {
            Toast.makeText(this, "Unable to get the data", Toast.LENGTH_SHORT).show();
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
