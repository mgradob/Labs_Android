package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mgradob on 11/14/14.
 */
public class Laboratory implements Serializable {

    @SerializedName("name")
    public String name;
    @SerializedName("link")
    public String url;

    @Override
    public String toString() {
        return "Laboratory{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
