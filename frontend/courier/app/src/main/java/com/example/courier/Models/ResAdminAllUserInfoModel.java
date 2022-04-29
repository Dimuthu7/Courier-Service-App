package com.example.courier.Models;

public class ResAdminAllUserInfoModel {
    private long userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userAddress;
    private String userStatus;
    private String userType;

    public ResAdminAllUserInfoModel(long userId, String userName, String userMobile, String userEmail,
                                    String userAddress, String userStatus, String userType){
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.userType = userType;
    }

    public ResAdminAllUserInfoModel() {
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

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getUserStatus() {
        return userStatus;
    }

    public String getUserType() {
        return userType;
    }
}
