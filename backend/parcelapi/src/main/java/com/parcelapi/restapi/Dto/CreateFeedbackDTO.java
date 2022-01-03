package com.parcelapi.restapi.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcelapi.restapi.Models.ParcelOrderDetails;
import com.parcelapi.restapi.Models.UserDetails;

public class CreateFeedbackDTO {
    @JsonIgnore
    private long feedbackId = 0;

    private String feedback;
    private long orderId;
    private long userId;

    @JsonIgnore
    private UserDetails user;

    @JsonIgnore
    private ParcelOrderDetails order;

    public CreateFeedbackDTO() {
    }

    public CreateFeedbackDTO(String feedback, long orderId, long userId) {
        this.feedback = feedback;
        this.orderId = orderId;
        this.userId = userId;
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
