package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @GET("/student/?format=json")
    ArrayList<User> getUsers();

    @POST("/student/")
    void postNewUser(@Body User body, Callback<Response> cb);

    @PUT("/student/{user_id}/")
    void editUser(@Path("user_id") String userId, @Body User body, Callback<Response> cb);

    @DELETE("/student/{user_id}/")
    void deleteUser(@Path("user_id") String userId, Callback<Response> cb);
}
