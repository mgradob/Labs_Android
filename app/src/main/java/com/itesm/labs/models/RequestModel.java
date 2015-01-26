package com.itesm.labs.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by miguel on 12/12/14.
 */
public class RequestModel implements Parcelable {

    private String userName, userId;
    private Boolean requestStatus;
    private ArrayList<String> userRequestList;

    public RequestModel(String userName, String userId, ArrayList<String> userRequestList, Boolean status) {
        this.userName = userName;
        this.userId = userId;
        this.userRequestList = userRequestList;
        this.requestStatus = status;
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

    public Boolean getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", requestStatus=" + requestStatus +
                ", userRequestList=" + userRequestList +
                '}';
    }

    //region Parcelable methods.
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeValue(requestStatus);
        dest.writeList(userRequestList);
    }

    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(Parcel source) {
            RequestModel requestModel = new RequestModel(
                    source.readString(),
                    source.readString(),
                    source.readArrayList(String.class.getClassLoader()),
                    (Boolean) source.readValue(Object.class.getClassLoader())
            );
            return requestModel;
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };
    //endregion
}
