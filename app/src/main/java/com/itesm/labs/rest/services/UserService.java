package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.LoginAdmin;
import com.itesm.labs.rest.models.TokenWrapper;
import com.itesm.labs.rest.models.User;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @POST("/auth/login/")
    TokenWrapper loginAdmin(@Body LoginAdmin admin);

    @GET("/admins/{admin_id}")
    Admin getAdmin(@Header("Authorization") String token,
                   @Path("admin_id") String adminId);

    @GET("/students/{user_id}")
    User getUser(@Header("Authorization") String token,
                 @Path("user_id") String userId);

    @GET("/students/{user_uid}")
    User getUser(@Header("Authorization") String token,
                 @Path("user_uid") long userUid);

    @GET("/students/")
    ArrayList<User> getUsers(@Header("Authorization") String token);

    @POST("/students/")
    Response postNewUser(@Header("Authorization") String token,
                     @Body User body);

    @PUT("/students/{user_id}/")
    Response editUser(@Header("Authorization") String token,
                  @Path("user_id") String userId,
                  @Body User body);

    @DELETE("/students/{user_id}/")
    Response deleteUser(@Header("Authorization") String token,
                    @Path("user_id") String userId);
}
