package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    @GET("/component/")
    ArrayList<Component> getComponents();
}
