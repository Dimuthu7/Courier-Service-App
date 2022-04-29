package com.example.courier.Models;

public class ResLogin {
    private long userId;
    private String userName;
    private String userType;
    private String token;

    public ResLogin() {
    }

    public ResLogin(long userId, String userName, String userType, String token) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
