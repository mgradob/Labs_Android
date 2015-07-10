package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @GET("/admins/{admin_id}/")
    Admin loginAdmin(@Path("admin_id") String adminId);

    @GET("/students/{user_id}")
    User getUser(@Path("user_id") String userId);

    @GET("/students/{user_uid}")
    User getUser(@Path("user_uid") long userUid);

    @GET("/students/")
    ArrayList<User> getUsers();

    @POST("/students/")
    void postNewUser(@Body User body, Callback<Response> cb);

    @PUT("/students/{user_id}/")
    void editUser(@Path("user_id") String userId, @Body User body, Callback<Response> cb);

    @DELETE("/students/{user_id}/")
    void deleteUser(@Path("user_id") String userId, Callback<Response> cb);
}
