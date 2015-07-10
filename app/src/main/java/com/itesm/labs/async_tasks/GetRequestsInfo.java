package com.itesm.labs.async_tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.service.CartService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetRequestsInfo extends AsyncTask<String, Void, ArrayList<CartItem>> {

    @Override
    protected ArrayList<CartItem> doInBackground(String... ENDPOINT) {
        ArrayList<CartItem> componentsData;

        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT[0])
                .setConverter(new GsonConverter(gson))
                .build();

        CartService service = restAdapter.create(CartService.class);

        componentsData = service.getCartsItems();

        return componentsData;
    }
}
