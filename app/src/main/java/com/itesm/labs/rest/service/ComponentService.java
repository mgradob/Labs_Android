package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.ComponentWrapper;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    //@GET("/component/?id_category_fk={id}")
    //ComponentWrapper getComponents(@Path("id") Integer categoryId);
    @GET("/component/")
    ComponentWrapper getComponents();
}
