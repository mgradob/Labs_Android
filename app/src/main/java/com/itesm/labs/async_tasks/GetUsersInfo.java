package com.itesm.labs.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.service.UserService;

import java.util.ArrayList;
import java.util.Random;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetUsersInfo extends AsyncTask<String, Void, ArrayList<User>> {

    private Context context;

    public GetUsersInfo(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<User> doInBackground(String... ENDPOINT) {
        ArrayList<User> usersList;

        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT[0])
                .setConverter(new GsonConverter(gson))
                .build();

        UserService service = restAdapter.create(UserService.class);

        usersList = service.getUsers();

        int[] colors = context.getResources().getIntArray(R.array.material_colors);
        for (User user : usersList) {
            int color = new Random().nextInt(colors.length - 1);
            user.setUserColor(color);
        }

        return usersList;
    }
}
