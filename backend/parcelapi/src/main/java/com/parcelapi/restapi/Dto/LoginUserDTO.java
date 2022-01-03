package com.parcelapi.restapi.Dto;

import javax.validation.constraints.NotEmpty;

public class LoginUserDTO {

    @NotEmpty(message = "Please provide username!")
    private String userName;

    @NotEmpty(message = "Please provide userpassword!")
    private String userPassword;

    public LoginUserDTO() {
    }

    public LoginUserDTO(String userName, String userPassword) {
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
