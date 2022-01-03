package com.parcelapi.restapi.Dto;

public class ViewFeedbackDTO {
    private long feedbackId;
    private String feedback;
    private long orderId;
    private long userId;
    private String userName;

    public ViewFeedbackDTO() {
    }

    public ViewFeedbackDTO(long feedbackId, String feedback, long orderId, long userId,
            String userName) {
        this.feedbackId = feedbackId;
        this.feedback = feedback;
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    } 
}
