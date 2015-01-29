package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @GET("/student/?format=json")
    ArrayList<User> getUsers();

    @POST("/student")
    void postNewUser(@Body User user, Callback<String> cb);
}
