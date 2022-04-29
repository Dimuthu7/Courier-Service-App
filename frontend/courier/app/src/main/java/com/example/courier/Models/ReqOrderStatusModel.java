package com.example.courier.Models;

public class ReqOrderStatusModel {
    private long orderId;
    private String status;

    public ReqOrderStatusModel(){}

    public ReqOrderStatusModel(long orderId, String status){
        this.orderId = orderId;
        this.status = status;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}
