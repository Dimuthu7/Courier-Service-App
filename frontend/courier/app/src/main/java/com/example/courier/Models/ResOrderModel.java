package com.example.courier.Models;

public class ResOrderModel {
    private long orderId;
    private String receiverName;
    private String receiverMobile;
    private String description;
    private String receiveLocation;
    private String pickupLocation;
    private String userName;
    private String status;

    public ResOrderModel(){}

    public ResOrderModel( long orderId, String receiverName, String receiverMobile, String description, String receiveLocation,
                         String pickupLocation, String userName, String status){
        this.orderId = orderId;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.description = description;
        this.receiveLocation = receiveLocation;
        this.pickupLocation = pickupLocation;
        this.userName = userName;
        this.status = status;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
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

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }
}
