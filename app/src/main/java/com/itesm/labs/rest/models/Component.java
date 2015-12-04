package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class Component {

    @SerializedName("id_category_fk")
    private int category;
    @SerializedName("id_component")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("note")
    private String note;
    @SerializedName("total")
    private int total;
    @SerializedName("available")
    private int available;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

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
        return note == null || note.equals("null") ? "" : note;
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

    @Override
    public String toString() {
        return "Component{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", available=" + available +
                '}';
    }
}
