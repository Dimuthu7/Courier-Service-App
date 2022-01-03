package com.parcelapi.restapi.Services;

import java.util.List;

import com.parcelapi.restapi.Dto.ViewDiscountInfoDTO;
import com.parcelapi.restapi.Models.ParcelOrderApprovalDetails;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;
import com.parcelapi.restapi.Models.UserDetails;

public interface AdminService {
    public List<ParcelOrderDetails> GetAllOrders();
    public ParcelOrderApprovalDetails ApproveOrder(ParcelOrderApprovalDetails approvalDetails, long driverId);
    public List<UserDetails> GetAllUsers();
    public List<ParcelOrderFeedbackDetails> GetAllFeedbacks();
    public ViewDiscountInfoDTO getDiscountInfo(long orderId);
}
