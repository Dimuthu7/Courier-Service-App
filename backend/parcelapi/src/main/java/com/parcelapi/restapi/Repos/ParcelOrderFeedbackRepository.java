package com.parcelapi.restapi.Repos;

import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelOrderFeedbackRepository extends JpaRepository<ParcelOrderFeedbackDetails, Long>{
    
}
