package com.parcelapi.restapi.Repos;

import com.parcelapi.restapi.Models.ParcelOrderApprovalDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelOrderApprovalRepository extends JpaRepository<ParcelOrderApprovalDetails, Long>{
    
}
