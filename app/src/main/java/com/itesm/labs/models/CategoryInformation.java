package com.itesm.labs.models;

/**
 * Created by miguel on 30/10/14.
 */
public class CategoryInformation {

    private String title;
    private int imageResource;

    public CategoryInformation(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
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
