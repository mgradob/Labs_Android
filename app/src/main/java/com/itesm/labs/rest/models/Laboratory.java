package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mgradob on 11/14/14.
 */
public class Laboratory implements Serializable {

    @SerializedName("url")
    public String url;
    @SerializedName("name")
    public String name;
    @SerializedName("link")
    public String link;
    public Integer imageResource;

    public Laboratory(String name, Integer imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
