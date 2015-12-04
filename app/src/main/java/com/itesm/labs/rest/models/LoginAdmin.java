package com.itesm.labs.rest.models;

/**
 * Created by mgradob on 11/3/15.
 */
public class LoginAdmin {

    private String id_student;
    private String password;

    public LoginAdmin(String id_student, String password) {
        this.id_student = id_student;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id_student='" + id_student + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}