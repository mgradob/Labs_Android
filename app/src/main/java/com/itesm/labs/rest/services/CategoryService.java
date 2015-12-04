package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.Category;

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
 * Created by miguel on 7/10/14.
 */
public interface CategoryService {

    @GET("/{lab}/category/")
    ArrayList<Category> getCategories(@Header("Authorization") String token,
                                      @Path("lab") String lab);

    @GET("/{lab}/category/{id_category}")
    Category getCategory(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Path("id_category") int category);

    @POST("/{lab}/category/")
    Response postNewCategory(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Body Category body);

    @PUT("/{lab}/category/{category_id}/")
    Response editCategory(@Header("Authorization") String token,
                      @Path("lab") String lab,
                      @Path("category_id") int catId,
                      @Body Category body);

    @DELETE("/{lab}/category/{category_id}/")
    Response deleteCategory(@Header("Authorization") String token,
                        @Path("lab") String lab,
                        @Path("category_id") int catId);
}
