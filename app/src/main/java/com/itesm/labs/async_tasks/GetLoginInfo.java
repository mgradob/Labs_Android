package com.itesm.labs.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.service.UserService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetLoginInfo extends AsyncTask<String, Void, User> {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected User doInBackground(String... ENDPOINT) {
        User user;

        try {
            Gson gson = new GsonBuilder()
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT[0])
                    .setConverter(new GsonConverter(gson))
                    .build();

            UserService service = restAdapter.create(UserService.class);

            user = service.loginUser(ENDPOINT[1]);
        } catch (Exception ex) {
            Log.e("Error:", "User not found");
            ex.printStackTrace();
            user = null;
        }

        return user;
    }
}
