package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.CategoryWrapper;

import retrofit.http.GET;

/**
 * Created by miguel on 7/10/14.
 */
public interface CategoryService {

    @GET("/elec/category/?format=json")
    CategoryWrapper getCategories();
}
