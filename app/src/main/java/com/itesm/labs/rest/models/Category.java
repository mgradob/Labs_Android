package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by miguel on 7/10/14.
 */
public class Category implements Serializable {

    @SerializedName("id_category")
    public int id;
    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
