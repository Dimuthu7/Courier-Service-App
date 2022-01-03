package com.parcelapi.restapi.Repos;

import java.util.List;

import com.parcelapi.restapi.Models.DeliveryDetails;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long>{
    List<DeliveryDetails> findByUser(UserDetails user);
    DeliveryDetails findByOrder(ParcelOrderDetails order);
}
