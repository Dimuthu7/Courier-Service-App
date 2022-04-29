package com.example.courier.Models;

public class ReqLoginModel {
    private String userName;
    private String userPassword;

    public ReqLoginModel() {
    }

    public ReqLoginModel(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
