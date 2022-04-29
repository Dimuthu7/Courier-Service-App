package com.example.courier.dto;

public class FeedbackDto {
    private String feedback;
    private long orderId;
    private String userName;

    public FeedbackDto(){}

    public FeedbackDto(String feedback, long orderId, String userName){
        this.feedback = feedback;
        this.orderId = orderId;
        this.userName = userName;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeedback() {
        return feedback;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }
}
