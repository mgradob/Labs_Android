package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.Category;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by miguel on 7/10/14.
 */
public interface CategoryService {

    @GET("/category/?format=json")
    ArrayList<Category> getCategories();
}
