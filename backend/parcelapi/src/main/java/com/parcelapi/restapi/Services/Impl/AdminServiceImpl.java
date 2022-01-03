package com.parcelapi.restapi.Services.Impl;

import java.util.List;

import com.parcelapi.restapi.Dto.ViewDiscountInfoDTO;
import com.parcelapi.restapi.Models.DeliveryDetails;
import com.parcelapi.restapi.Models.ParcelOrderApprovalDetails;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;
import com.parcelapi.restapi.Models.UserDetails;
import com.parcelapi.restapi.Repos.DeliveryDetailsRepository;
import com.parcelapi.restapi.Repos.ParcelDetailsRepository;
import com.parcelapi.restapi.Repos.ParcelOrderApprovalRepository;
import com.parcelapi.restapi.Repos.ParcelOrderFeedbackRepository;
import com.parcelapi.restapi.Repos.UserDetailsRepository;
import com.parcelapi.restapi.Services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ParcelDetailsRepository parcelDetailsRepository;

    @Autowired
    private ParcelOrderFeedbackRepository feedbackRepository;

    @Autowired
    private ParcelOrderApprovalRepository approvalRepository;

    @Autowired
    private DeliveryDetailsRepository deliveryRepository;

    @Override
    public List<ParcelOrderDetails> GetAllOrders() {
        return parcelDetailsRepository.findAll();
    }

    @Override
    public ParcelOrderApprovalDetails ApproveOrder(ParcelOrderApprovalDetails approvalDetails, long driverId) {
        ParcelOrderApprovalDetails saveOrder = approvalRepository.save(approvalDetails);
        if(approvalDetails.getApprovalStatus().equals("A")) {
            deliveryRepository.save(new DeliveryDetails(0, "A", userDetailsRepository.getById(driverId), approvalDetails.getOrder()));
        }
        return saveOrder;
    }

    @Override
    public List<UserDetails> GetAllUsers() {
        return userDetailsRepository.findAll();
    }

    @Override
    public List<ParcelOrderFeedbackDetails> GetAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public ViewDiscountInfoDTO getDiscountInfo(long orderId) {
        ParcelOrderDetails order = parcelDetailsRepository.getById(orderId);
        UserDetails user = userDetailsRepository.getById(order.getUser().getUserId());
        List<ParcelOrderDetails> listOrders = parcelDetailsRepository.findByUser(user);

        long userLevel = 0;
        if(listOrders.size() >= 5) userLevel = 1;
        else if(listOrders.size() >= 10) userLevel = 2;
        else if(listOrders.size() >= 15) userLevel = 3;
        else if(listOrders.size() >= 20) userLevel = 4; 

        return new ViewDiscountInfoDTO(user.getUserId(), user.getUserName(), userLevel);
    }
 
}
