package com.itesm.labs.rest.service;

import com.itesm.labs.models.UserModel;
import com.itesm.labs.rest.models.UserWrapper;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @GET("/student/?format=json")
    UserWrapper getUsers();

    @POST("/student")
    void postNewUser(@Body UserModel user, Callback<String> cb);
}
