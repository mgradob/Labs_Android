package com.itesm.labs.models;

import java.util.ArrayList;

/**
 * Created by miguel on 12/12/14.
 */
public class RequestModel {

    private String UserName, UserId;
    private ArrayList<String> UserRequestList;

    public RequestModel(String userName, String userId, ArrayList<String> userRequestList) {
        UserName = userName;
        UserId = userId;
        UserRequestList = userRequestList;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ArrayList<String> getUserRequestList() {
        return UserRequestList;
    }

    public void setUserRequestList(ArrayList<String> userRequestList) {
        UserRequestList = userRequestList;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "UserName='" + UserName + '\'' +
                ", UserId='" + UserId + '\'' +
                ", UserRequestList=" + UserRequestList +
                '}';
    }
}
