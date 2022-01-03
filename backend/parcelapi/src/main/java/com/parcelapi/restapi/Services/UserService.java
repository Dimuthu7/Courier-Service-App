package com.parcelapi.restapi.Services;


import com.parcelapi.restapi.Models.UserDetails;

public interface UserService {
    public UserDetails registerUser(UserDetails userDetails);
    public UserDetails UpdateUserInfo(UserDetails userDetails);
    public UserDetails GetUserDetails(long userId);
    public UserDetails GetUserDetailsByName(String userName);
}
