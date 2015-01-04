package com.itesm.labs.models;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class UserModel {

    private String firstName, lastName1, lastName2, userId, userCareer, userMail;
    private Integer userUID;
    private int backgroundColor;

    public UserModel(String firstName, String lastName1, String lastName2, String userId, String userCareer, String userMail, Integer userUID) {
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.userId = userId;
        this.userCareer = userCareer;
        this.userMail = userMail;
        this.userUID = userUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
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

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Integer getUserUID() {
        return userUID;
    }

    public void setUserUID(Integer userUID) {
        this.userUID = userUID;
    }

    public String getFullName() {
        return firstName + ' ' + lastName1 + ' ' + lastName2;
    }

    public void setFullName(String fn, String ln1, String ln2) {
        this.firstName = fn;
        this.lastName1 = ln1;
        this.lastName2 = ln2;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName1='" + lastName1 + '\'' +
                ", lastName2='" + lastName2 + '\'' +
                ", userId='" + userId + '\'' +
                ", userCareer='" + userCareer + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userUID=" + userUID +
                '}';
    }
}
