package com.parcelapi.restapi.Models;

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

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ParcelOrderApprovalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long approvalId;

    @Column(length = 1)
    private String approvalStatus;

    @Column
    private Double price;

    @Column
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private UserDetails user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_order_id", nullable = false)
    private ParcelOrderDetails order;

    public ParcelOrderApprovalDetails() {
    }

    public ParcelOrderApprovalDetails(long approvalId, String approvalStatus, Double price, String remarks,
            UserDetails user, ParcelOrderDetails order) {
        this.approvalId = approvalId;
        this.approvalStatus = approvalStatus;
        this.price = price;
        this.remarks = remarks;
        this.user = user;
        this.order = order;
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

    public long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(long approvalId) {
        this.approvalId = approvalId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
