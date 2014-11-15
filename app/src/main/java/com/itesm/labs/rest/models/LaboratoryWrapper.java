package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgradob on 11/14/14.
 */
public class LaboratoryWrapper {

    @SerializedName("results")
    public List<Laboratory> laboratoryList;

    @Override
    public String toString() {
        return "LaboratoryWrapper{" +
                "laboratoryList=" + laboratoryList +
                '}';
    }
}
