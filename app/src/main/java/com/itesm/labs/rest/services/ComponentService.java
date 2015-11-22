package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    @GET("/{lab}/component/")
    ArrayList<Component> getAllComponents(@Header("Authorization") String token,
                                          @Path("lab") String lab);

    @GET("/{lab}/component/")
    ArrayList<Component> getComponents(@Header("Authorization") String token,
                                       @Path("lab") String lab,
                                       @Query("id_category_fk") int category);

    @GET("/{lab}/component/{id_component}")
    Component getComponent(@Header("Authorization") String token,
                           @Path("lab") String lab,
                           @Path("id_component") int componentId);

    @POST("/{lab}/component/")
    void postComponent(@Header("Authorization") String token,
                       @Path("lab") String lab,
                       @Body Component item);

    @PUT("/{lab}/component/{component_id}/")
    void putComponent(@Header("Authorization") String token,
                      @Path("lab") String lab,
                      @Path("component_id") int componentId,
                      @Body Component body);

    @DELETE("/{lab}/component/{component_id}/")
    void deleteComponent(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Path("component_id") int componentId);
}
