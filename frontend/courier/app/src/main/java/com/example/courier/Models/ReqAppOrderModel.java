package com.example.courier.Models;

public class ReqAppOrderModel {
    private String approvalStatus;
    private double price;
    private String remarks;
    private long orderId;
    private long userId;
    private long driverId;

    public ReqAppOrderModel(){}

    public ReqAppOrderModel(String approvalStatus, double price, String remarks, long orderId,
                            long userId, long driverId){
        this.approvalStatus = approvalStatus;
        this.price = price;
        this.remarks = remarks;
        this.orderId = orderId;
        this.userId = userId;
        this.driverId = driverId;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public double getPrice() {
        return price;
    }

    public String getRemarks() {
        return remarks;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }

    public long getDriverId() {
        return driverId;
    }
}
