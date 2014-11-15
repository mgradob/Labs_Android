package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.LaboratoryWrapper;

import retrofit.http.GET;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryService {

    @GET("/links/?format=json")
    LaboratoryWrapper getLaboratories();
}
