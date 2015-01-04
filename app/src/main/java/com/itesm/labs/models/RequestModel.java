package com.itesm.labs.models;

import java.util.ArrayList;

/**
 * Created by miguel on 12/12/14.
 */
public class RequestModel {

    private String userName, userId;
    private ArrayList<String> userRequestList;

    public RequestModel(String userName, String userId, ArrayList<String> userRequestList) {
        this.userName = userName;
        this.userId = userId;
        this.userRequestList = userRequestList;
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

    public ArrayList<String> getUserRequestList() {
        return userRequestList;
    }

    public void setUserRequestList(ArrayList<String> userRequestList) {
        this.userRequestList = userRequestList;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", userRequestList=" + userRequestList +
                '}';
    }
}
