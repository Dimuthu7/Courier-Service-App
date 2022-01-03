package com.parcelapi.restapi.Repos;

import com.parcelapi.restapi.Models.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
    UserDetails findOneByUserName(String userName);
}
