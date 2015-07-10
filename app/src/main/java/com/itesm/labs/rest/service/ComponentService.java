package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    @GET("/component/")
    ArrayList<Component> getAllComponents();

    @GET("/component/")
    ArrayList<Component> getComponents(@Query("id_category_fk") String category);

    @GET("/component/{id_component}")
    Component getComponent(@Path("id_component") int componentId);

    @POST("/component/")
    void postComponent(@Body Component item, Callback<Response> cb);

    @PUT("/component/{component_id}/")
    void putComponent(@Path("component_id") int componentId, @Body Component body, Callback<Response> cb);

    @DELETE("/component/{component_id}/")
    void deleteComponent(@Path("component_id") int componentId, Callback<Response> cb);
}
