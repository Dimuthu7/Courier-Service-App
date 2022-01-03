package com.parcelapi.restapi.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcelapi.restapi.Models.UserDetails;

public class CreateOrderDTO {
    @JsonIgnore
    private long orderId;

    private String receiverName;
    private String receiverMobile;
    private String description;
    private String receiveLocation;
    private String pickupLocation;
    private long userId;

    @JsonIgnore
    private UserDetails user;

    public CreateOrderDTO() {
    }

    public CreateOrderDTO(long orderId, String receiverName, String receiverMobile, String description,
            String receiveLocation, String pickupLocation, long userId, UserDetails user) {
        this.orderId = orderId;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.description = description;
        this.receiveLocation = receiveLocation;
        this.pickupLocation = pickupLocation;
        this.userId = userId;
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiveLocation() {
        return receiveLocation;
    }

    public void setReceiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
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
}
