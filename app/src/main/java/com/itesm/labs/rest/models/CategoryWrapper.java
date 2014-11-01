package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by miguel on 25/10/14.
 */
public class CategoryWrapper {

    /*@SerializedName("count")
    public int count;
    @SerializedName("next")
    public String next;
    @SerializedName("previous")
    public String previous;*/
    @SerializedName("results")
    public List<Category> categoryList;

    @Override
    public String toString() {
        return "CategoryWrapper{" +
                "categoryList=" + categoryList +
                '}';
    }
}
