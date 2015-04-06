package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Request;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgradob on 2/28/15.
 */
public interface CartService {
    @GET("/detailcart/?format=json")
    ArrayList<Request> getAllCarts();
}
