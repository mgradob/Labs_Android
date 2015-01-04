package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class Component {

    @SerializedName("id_component")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("note")
    public String note;
    @SerializedName("total")
    public int total;
    @SerializedName("available")
    public int available;

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", available=" + available +
                '}';
    }
}
