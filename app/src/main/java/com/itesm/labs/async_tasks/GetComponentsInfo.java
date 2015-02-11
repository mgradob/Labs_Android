package com.itesm.labs.async_tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.service.ComponentService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetComponentsInfo extends AsyncTask<String, Void, ArrayList<Component>> {

    @Override
    protected ArrayList<Component> doInBackground(String... ENDPOINT) {
        ArrayList<Component> componentsData;

        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT[0])
                .setConverter(new GsonConverter(gson))
                .build();

        ComponentService service = restAdapter.create(ComponentService.class);

        componentsData = service.getComponents();

        return componentsData;
    }
}
