package com.example.courier.dto;

public class PendingOrderDetails {
    private String order_id;
    private String description;
    private String pickup_location;
    private String receive_location;
    private String receiver_mobile;
    private String receiver_name;
    private String status;
    private String userName;

    public PendingOrderDetails(String order_id, String description, String pickup_location,
                               String receive_location, String receiver_mobile,
                               String receiver_name, String status){
        this.order_id = order_id;
        this.description = description;
        this.pickup_location = pickup_location;
        this.receive_location = receive_location;
        this.receiver_mobile = receiver_mobile;
        this.receiver_name = receiver_name;
        this.status = status;
    }

    public PendingOrderDetails(String order_id, String description, String pickup_location,
                               String receive_location, String receiver_mobile,
                               String receiver_name, String status, String userName){
        this.order_id = order_id;
        this.description = description;
        this.pickup_location = pickup_location;
        this.receive_location = receive_location;
        this.receiver_mobile = receiver_mobile;
        this.receiver_name = receiver_name;
        this.status = status;
        this.userName = userName;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setReceive_location(String receive_location) {
        this.receive_location = receive_location;
    }

    public String getReceive_location() {
        return receive_location;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
