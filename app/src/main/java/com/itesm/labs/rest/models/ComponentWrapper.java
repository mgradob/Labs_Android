package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class ComponentWrapper {

    @SerializedName("results")
    public List<Component> componentList;

    @Override
    public String toString() {
        return "ComponentWrapper{" +
                "componentList=" + componentList +
                '}';
    }
}
