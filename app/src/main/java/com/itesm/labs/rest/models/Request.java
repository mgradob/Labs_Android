package com.itesm.labs.rest.models;

/**
 * Created by miguel on 21/11/14.
 */
public class Request {

    public Integer imageResource;
    public String userName;
    public String userId;
    public String userDate;

    public Request(Integer imageResource, String userName, String userId, String userDate) {
        this.imageResource = imageResource;
        this.userName = userName;
        this.userId = userId;
        this.userDate = userDate;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    @Override
    public String toString() {
        return "Request{" +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", userDate='" + userDate + '\'' +
                '}';
    }
}
