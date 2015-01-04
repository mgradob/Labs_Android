package com.itesm.labs.models;

/**
 * Created by miguel on 30/10/14.
 */
public class CategoryModel {

    private String title;
    private int idApi;
    private Integer imageResource;

    public CategoryModel(String title, int idApi, Integer imageResource) {
        this.title = title;
        this.idApi = idApi;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "title='" + title + '\'' +
                ", idApi=" + idApi +
                ", imageResource=" + imageResource +
                '}';
    }
}
