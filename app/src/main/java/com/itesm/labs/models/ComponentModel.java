package com.itesm.labs.models;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class ComponentModel {

    private int idApi;
    private String name;
    private String note;
    private int total;
    private int available;
    private Integer imageResource;

    public ComponentModel(int idApi, String name, String note, int total, int available, Integer imageResource) {
        this.idApi = idApi;
        this.name = name;
        this.note = note;
        this.total = total;
        this.available = available;
        this.imageResource = imageResource;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
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
        return "ComponentModel{" +
                "idApi=" + idApi +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", available=" + available +
                ", imageResource=" + imageResource +
                '}';
    }
}
