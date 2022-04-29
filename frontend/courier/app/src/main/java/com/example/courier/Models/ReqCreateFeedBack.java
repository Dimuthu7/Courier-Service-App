package com.example.courier.Models;

public class ReqCreateFeedBack {
    private String feedback;
    private long orderId;
    private long userId;

    public ReqCreateFeedBack(){}

    public ReqCreateFeedBack(String feedback, long orderId, long userId){
        this.feedback = feedback;
        this.orderId = orderId;
        this.userId = userId;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }
}
