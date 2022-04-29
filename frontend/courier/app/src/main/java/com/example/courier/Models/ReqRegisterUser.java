package com.example.courier.Models;

public class ReqRegisterUser {
    private String userName;
    private String userPassword;
    private String userMobile;
    private String userEmail;
    private String userAddress;
    private String userType;

    public ReqRegisterUser() {
    }

    public ReqRegisterUser(String userName, String userPassword, String userMobile, String userEmail, String userAddress, String userType) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userType = userType;
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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
