package com.parcelapi.restapi.Services;

import java.util.List;

import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.ParcelOrderFeedbackDetails;

public interface OrderService {
    public ParcelOrderDetails createOrder(ParcelOrderDetails parcelOrderDetails);
    public ParcelOrderDetails getParcelOrderDetails(long orderId);
    public List<ParcelOrderDetails> getAllOrdersByCustId(long userId);
    public String deleteParcelOrder(long orderId);
    public ParcelOrderFeedbackDetails createFeedback(ParcelOrderFeedbackDetails feedbackDetails);
}
