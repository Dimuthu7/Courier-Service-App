package com.parcelapi.restapi.Services.Impl;

import com.parcelapi.restapi.Models.UserDetails;
import com.parcelapi.restapi.Repos.UserDetailsRepository;
import com.parcelapi.restapi.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails registerUser(UserDetails userDetails) {
        String encodePassword = passwordEncoder.encode(userDetails.getUserPassword());
        userDetails.setUserPassword(encodePassword);
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails UpdateUserInfo(UserDetails userDetails) {
        UserDetails dbUserDetails = userDetailsRepository.getById(userDetails.getUserId());
        dbUserDetails.setUserName(userDetails.getUserName());
        dbUserDetails.setUserMobile(userDetails.getUserMobile());
        dbUserDetails.setUserEmail(userDetails.getUserEmail());
        dbUserDetails.setUserAddress(userDetails.getUserAddress());

        return userDetailsRepository.save(dbUserDetails);
    }

    @Override
    public UserDetails GetUserDetails(long userId) {
        return userDetailsRepository.getById(userId);
    }

    @Override
    public UserDetails GetUserDetailsByName(String userName) {
        return userDetailsRepository.findOneByUserName(userName);
    }
}
