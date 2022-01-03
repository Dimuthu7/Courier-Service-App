package com.parcelapi.restapi.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.UserDetails;

public class CreateApproveOrderDTO {
    @JsonIgnore
    private long approvalId = 0;
    
    private String approvalStatus;
    private Double price;
    private String remarks;
    private long orderId;
    private long userId;
    private long driverId;

    @JsonIgnore
    private UserDetails user;

    @JsonIgnore
    private ParcelOrderDetails order;

    public CreateApproveOrderDTO() {
    }

    public CreateApproveOrderDTO(long approvalId, String approvalStatus, Double price, String remarks, long orderId,
            long userId, long driverId) {
        this.approvalId = approvalId;
        this.approvalStatus = approvalStatus;
        this.price = price;
        this.remarks = remarks;
        this.orderId = orderId;
        this.userId = userId;
        this.driverId = driverId;
    }

    public long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(long approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public ParcelOrderDetails getOrder() {
        return order;
    }

    public void setOrder(ParcelOrderDetails order) {
        this.order = order;
    }
}
