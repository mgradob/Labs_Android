package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class UserWrapper {

    @SerializedName("results")
    public List<User> userList;

    @Override
    public String toString() {
        return "UserWrapper{" +
                "userList=" + userList +
                '}';
    }
}
