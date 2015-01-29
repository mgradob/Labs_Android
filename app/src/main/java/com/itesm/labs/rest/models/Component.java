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
    public Integer imageResource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", available=" + available +
                ", imageResource=" + imageResource +
                '}';
    }
}
