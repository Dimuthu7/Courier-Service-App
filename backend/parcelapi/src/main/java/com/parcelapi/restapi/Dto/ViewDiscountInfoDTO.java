package com.parcelapi.restapi.Dto;

public class ViewDiscountInfoDTO {
    private long userId;
    private String userName;
    private long userLevel;

    public ViewDiscountInfoDTO() {
    }

    public ViewDiscountInfoDTO(long userId, String userName, long userLevel) {
        this.userId = userId;
        this.userName = userName;
        this.userLevel = userLevel;
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

    public long getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(long userLevel) {
        this.userLevel = userLevel;
    }
}
