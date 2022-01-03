package com.parcelapi.restapi.Services;

import java.util.ArrayList;

import com.parcelapi.restapi.Repos.UserDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        com.parcelapi.restapi.Models.UserDetails dbUser = userDetailsRepository.findOneByUserName(userName);
        if(dbUser == null){
            throw new UsernameNotFoundException("Cannot find user object for : " + userName);
        }

        return new User(dbUser.getUserName(), dbUser.getUserPassword(), new ArrayList<>());
    }
    
}
