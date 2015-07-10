package com.itesm.labs.rest.queries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.service.UserService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/11/15.
 */
public class UserQuery {

    private String ENDPOINT;

    public UserQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public User getUser(String userId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        UserService service = restAdapter.create(UserService.class);

        return service.getUser(userId);
    }

    public User getUser(long userUid) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        UserService service = restAdapter.create(UserService.class);

        return service.getUser(userUid);

    }
}
