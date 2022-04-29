package com.example.courier.Models;

public class ReqOrderModel {
    private String receiverName;
    private String receiverMobile;
    private String description;
    private String receiveLocation;
    private String pickupLocation;
    private long userId;

    public ReqOrderModel(){}

    public ReqOrderModel(String receiverName, String receiverMobile, String description, String receiveLocation,
                         String pickupLocation, long userId){
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.description = description;
        this.receiveLocation = receiveLocation;
        this.pickupLocation = pickupLocation;
        this.userId = userId;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReceiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public String getDescription() {
        return description;
    }

    public String getReceiveLocation() {
        return receiveLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public long getUserId() {
        return userId;
    }
}
