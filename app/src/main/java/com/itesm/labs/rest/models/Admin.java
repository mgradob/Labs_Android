package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class Admin {

    @SerializedName("id_administrator")
    private String adminId;
    @SerializedName("name")
    private String adminName;
    @SerializedName("last_name_1")
    private String adminLastName1;
    @SerializedName("last_name_2")
    private String adminLastName2;
    @SerializedName("mail")
    private String adminMail;
    @SerializedName("labs")
    private ArrayList<String> allowedLabs;

    public Admin(String adminId, String adminName, String adminLastName1, String adminLastName2, String adminMail, ArrayList<String> allowedLabs) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminLastName1 = adminLastName1;
        this.adminLastName2 = adminLastName2;
        this.adminMail = adminMail;
        this.allowedLabs = allowedLabs;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getFullName() {
        return this.adminName + " " + this.adminLastName1 + " " + this.adminLastName2;
    }

    public String getAdminLastName1() {
        return adminLastName1;
    }

    public void setAdminLastName1(String adminLastName1) {
        this.adminLastName1 = adminLastName1;
    }

    public String getAdminLastName2() {
        return adminLastName2;
    }

    public void setAdminLastName2(String adminLastName2) {
        this.adminLastName2 = adminLastName2;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    public ArrayList<String> getAllowedLabs() {
        return allowedLabs;
    }

    public void setAllowedLabs(ArrayList<String> allowedLabs) {
        this.allowedLabs = allowedLabs;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminLastName1='" + adminLastName1 + '\'' +
                ", adminLastName2='" + adminLastName2 + '\'' +
                ", adminMail='" + adminMail + '\'' +
                ", allowedLabs=" + allowedLabs +
                '}';
    }
}
