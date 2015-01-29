package com.itesm.labs.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.activities.LaboratoriesActivity;
import com.itesm.labs.adapters.LaboratoriesModelAdapter;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.service.LaboratoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetLabsInfo extends AsyncTask<String, Void, ArrayList<Laboratory>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Laboratory> doInBackground(String... ENDPOINT) {
        ArrayList<Laboratory> mLaboratoryModelsList;

        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT[0])
                .setConverter(new GsonConverter(gson))
                .build();

        LaboratoryService service = restAdapter.create(LaboratoryService.class);

        mLaboratoryModelsList = service.getLaboratories();

        for (Laboratory laboratory : mLaboratoryModelsList) {
            laboratory.setImageResource(R.drawable.ic_dummy_category);
        }

        return mLaboratoryModelsList;
    }

    @Override
    protected void onPostExecute(ArrayList<Laboratory> laboratories) {
        super.onPostExecute(laboratories);
    }
}
