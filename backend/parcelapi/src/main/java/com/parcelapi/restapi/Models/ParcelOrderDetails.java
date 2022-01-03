package com.parcelapi.restapi.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
public class ParcelOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(length = 45)
    private String receiverName;

    @Column(length = 10)
    private String receiverMobile;

    @Column(length = 100)
    private String description;

    @Column(length = 100)
    private String receiveLocation;

    @Column(length = 100)
    private String pickupLocation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private UserDetails user;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ParcelOrderApprovalDetails orderApprovalDetails;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ParcelOrderFeedbackDetails orderFeedbackDetails;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DeliveryDetails deliveryDetails;

    public ParcelOrderDetails() {
    }

    public ParcelOrderDetails(long orderId, String receiverName, String receiverMobile, String description,
            String receiveLocation, String pickupLocation, UserDetails user) {
        this.orderId = orderId;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.description = description;
        this.receiveLocation = receiveLocation;
        this.pickupLocation = pickupLocation;
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

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public DeliveryDetails getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public ParcelOrderApprovalDetails getOrderApprovalDetails() {
        return orderApprovalDetails;
    }

    public void setOrderApprovalDetails(ParcelOrderApprovalDetails orderApprovalDetails) {
        this.orderApprovalDetails = orderApprovalDetails;
    }
}
