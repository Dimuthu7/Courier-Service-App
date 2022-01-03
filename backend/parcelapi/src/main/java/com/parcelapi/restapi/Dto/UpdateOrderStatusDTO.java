package com.parcelapi.restapi.Dto;

public class UpdateOrderStatusDTO {
    private long orderId;
    private String status;

    public UpdateOrderStatusDTO() {
    }

    public UpdateOrderStatusDTO(long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
