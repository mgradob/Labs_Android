package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.itesm.labs.R;
import com.itesm.labs.adapters.LaboratoriesModelAdapter;
import com.itesm.labs.async_tasks.GetLabsInfo;
import com.itesm.labs.rest.models.Laboratory;

import java.util.ArrayList;


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

        GetLabsInfo getLabsInfo = new GetLabsInfo() {
            @Override
            protected void onPostExecute(ArrayList<Laboratory> laboratories) {
                super.onPostExecute(laboratories);
                mLaboratoryModelsList = laboratories;
                mGridView.setAdapter(new LaboratoriesModelAdapter(getApplicationContext(), mLaboratoryModelsList));

                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };
        getLabsInfo.execute(ENDPOINT);

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
    }
}
