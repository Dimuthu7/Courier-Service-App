package com.parcelapi.restapi.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateUserDetailsDTO {
    @JsonIgnore
    private long userId = 0;
    
    private String userName;
    private String userPassword;
    private String userMobile;
    private String userEmail;
    private String userAddress;
    private String userType;
    
    @JsonIgnore
    private String userStatus = "A";

    public CreateUserDetailsDTO() {
    }

    public CreateUserDetailsDTO(String userName, String userPassword, String userMobile, String userEmail,
            String userAddress, String userType, String userStatus) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userStatus = userStatus;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
