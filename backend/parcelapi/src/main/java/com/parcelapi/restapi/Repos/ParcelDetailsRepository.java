package com.parcelapi.restapi.Repos;

import java.util.List;

import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelDetailsRepository extends JpaRepository<ParcelOrderDetails, Long>{
    List<ParcelOrderDetails> findByUser(UserDetails user);
}
