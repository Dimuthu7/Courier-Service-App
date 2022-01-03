package com.parcelapi.restapi.Services.Impl;

import java.util.List;

import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;
import com.parcelapi.restapi.Repos.ParcelDetailsRepository;
import com.parcelapi.restapi.Repos.ParcelOrderFeedbackRepository;
import com.parcelapi.restapi.Repos.UserDetailsRepository;
import com.parcelapi.restapi.Services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderSericeImpl implements OrderService{

    @Autowired
    private ParcelDetailsRepository parcelDetailsRepository;

    @Autowired
    private ParcelOrderFeedbackRepository feedbackRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public ParcelOrderDetails createOrder(ParcelOrderDetails parcelOrderDetails) {
        return parcelDetailsRepository.save(parcelOrderDetails);
    }

    @Override
    public ParcelOrderDetails getParcelOrderDetails(long orderId) {
        return parcelDetailsRepository.getById(orderId);
    }

    @Override
    public String deleteParcelOrder(long orderId) {
        try {
            parcelDetailsRepository.deleteById(orderId);
            return "Successfully Delete Order ID : " + orderId;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @Override
    public ParcelOrderFeedbackDetails createFeedback(ParcelOrderFeedbackDetails feedbackDetails) {
        return feedbackRepository.save(feedbackDetails);
    }

    @Override
    public List<ParcelOrderDetails> getAllOrdersByCustId(long userId) {
        return parcelDetailsRepository.findByUser(userDetailsRepository.getById(userId));
    }
    
}
