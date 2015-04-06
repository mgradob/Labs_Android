package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Laboratory;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryService {

    @GET("/labs/?format=json")
    ArrayList<Laboratory> getLaboratories();
}
