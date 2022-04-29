package com.example.courier.Models;

public class ResUserInfoModel {
    private long userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userAddress;

    public ResUserInfoModel(long userId, String userName, String userMobile, String userEmail, String userAddress){
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
    }

    public ResUserInfoModel() {
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }
}
