package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.Laboratory;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryService {

    @GET("/")
    Laboratory getLaboratory(@Header("Authorization") String token,
                             @Query("link") String link);

    @GET("/labs/")
    ArrayList<Laboratory> getLaboratories(@Header("Authorization") String token);
}
