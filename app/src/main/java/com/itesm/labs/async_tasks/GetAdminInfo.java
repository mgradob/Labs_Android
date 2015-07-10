package com.itesm.labs.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.service.UserService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetAdminInfo extends AsyncTask<String, Void, Admin> {

    private Context context;

    public GetAdminInfo(Context context) {
        this.context = context;
    }

    @Override
    protected Admin doInBackground(String... ENDPOINT) {
        try {
            Gson gson = new GsonBuilder()
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT[0])
                    .setConverter(new GsonConverter(gson))
                    .build();

            UserService service = restAdapter.create(UserService.class);

            Admin admin = service.loginAdmin(ENDPOINT[1]);

            return admin;
        } catch (Exception ex) {
            return null;
        }
    }
}
