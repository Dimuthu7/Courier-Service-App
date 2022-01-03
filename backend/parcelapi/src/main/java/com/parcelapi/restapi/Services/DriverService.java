package com.parcelapi.restapi.Services;

import java.util.List;

import com.parcelapi.restapi.Models.ParcelOrderDetails;

public interface DriverService {
    public List<ParcelOrderDetails> GetAllOrders(long userId);
    public String UpdateOrderStatus(long orderId, String status);
    public String UpdateDriverStatus(long userId, String status);
}
