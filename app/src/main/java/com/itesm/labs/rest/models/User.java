package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class User {

    @SerializedName("name")
    private String userName;
    @SerializedName("last_name_1")
    private String userLastName1;
    @SerializedName("last_name_2")
    private String userLastName2;
    @SerializedName("id_student")
    private String userId;
    @SerializedName("career")
    private String userCareer;
    @SerializedName("id_credential")
    private long userUid;
    @SerializedName("mail")
    private String userMail;
    @SerializedName("labs")
    private ArrayList<String> allowedLabs;
    private Integer userColor;

    public User() {
    }

    public User(String userName, String userLastName1, String userLastName2, String userId, String userCareer, long userUid, String userMail, ArrayList<String> allowedLabs) {
        this.userName = userName;
        this.userLastName1 = userLastName1;
        this.userLastName2 = userLastName2;
        this.userId = userId;
        this.userCareer = userCareer;
        this.userUid = userUid;
        this.userMail = userMail;
        this.allowedLabs = allowedLabs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return this.userName + " " + this.userLastName1 + " " + this.userLastName2;
    }

    public String getUserLastName1() {
        return userLastName1;
    }

    public void setUserLastName1(String userLastName1) {
        this.userLastName1 = userLastName1;
    }

    public String getUserLastName2() {
        return userLastName2;
    }

    public void setUserLastName2(String userLastName2) {
        this.userLastName2 = userLastName2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCareer() {
        return userCareer;
    }

    public void setUserCareer(String userCareer) {
        this.userCareer = userCareer;
    }

    public long getUserUid() {
        return userUid;
    }

    public void setUserUid(long userUid) {
        this.userUid = userUid;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public ArrayList<String> getAllowedLabs() {
        return allowedLabs;
    }

    public void setAllowedLabs(ArrayList<String> allowedLabs) {
        this.allowedLabs = allowedLabs;
    }

    public Integer getUserColor() {
        return userColor;
    }

    public void setUserColor(Integer userColor) {
        this.userColor = userColor;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userLastName1='" + userLastName1 + '\'' +
                ", userLastName2='" + userLastName2 + '\'' +
                ", userId='" + userId + '\'' +
                ", userCareer='" + userCareer + '\'' +
                ", userUid=" + userUid +
                ", userMail='" + userMail + '\'' +
                ", allowedLabs=" + allowedLabs +
                ", userColor=" + userColor +
                '}';
    }
}
