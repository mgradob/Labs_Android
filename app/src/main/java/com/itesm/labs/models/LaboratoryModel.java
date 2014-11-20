package com.itesm.labs.models;

/**
 * Created by miguel on 15/11/14.
 */
public class LaboratoryModel {

    private String name;
    private Integer imageResource;

    public LaboratoryModel(String name, Integer imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "LaboratoryModel{" +
                "name='" + name + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
