package com.itesm.labs.models;

/**
 * Created by miguel on 30/10/14.
 */
public class CategoryModel {

    private String title;
    private Integer imageResource;

    public CategoryModel(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "CategoryInformation{" +
                "title='" + title + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
