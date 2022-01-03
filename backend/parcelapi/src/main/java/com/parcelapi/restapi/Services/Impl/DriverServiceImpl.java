package com.parcelapi.restapi.Services.Impl;

import java.util.ArrayList;
import java.util.List;

import com.parcelapi.restapi.Models.DeliveryDetails;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.UserDetails;
import com.parcelapi.restapi.Repos.DeliveryDetailsRepository;
import com.parcelapi.restapi.Repos.ParcelDetailsRepository;
import com.parcelapi.restapi.Repos.UserDetailsRepository;
import com.parcelapi.restapi.Services.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private DeliveryDetailsRepository deliveryRepository;

    @Autowired
    private ParcelDetailsRepository parcelDetailsRepository;
    
    @Override
    public List<ParcelOrderDetails> GetAllOrders(long userId) {
        List<DeliveryDetails> listDelivery = deliveryRepository.findByUser(userDetailsRepository.getById(userId));
        List<ParcelOrderDetails> listOrders = new ArrayList<ParcelOrderDetails>();

        for(DeliveryDetails obj : listDelivery) {
            listOrders.add(parcelDetailsRepository.getById(obj.getOrder().getOrderId()));
        }

        return listOrders;
    }

    @Override
    public String UpdateOrderStatus(long orderId, String status) {
        DeliveryDetails delivery = deliveryRepository.findByOrder(parcelDetailsRepository.getById(orderId));
        delivery.setStatus(status);
        deliveryRepository.save(delivery);

        return "Successfully update the order status! Order ID : " + orderId;
    }

    @Override
    public String UpdateDriverStatus(long userId, String status) {
        UserDetails user = userDetailsRepository.getById(userId);
        user.setUserStatus(status);
        userDetailsRepository.save(user);

        return "Successfully Update driver status! Driver ID : " + userId;
    }
    
}
