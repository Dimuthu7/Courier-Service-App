package com.parcelapi.restapi.Utility;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean status;
    private T data;
    private String error;

    public ApiResponse(boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(boolean status, T data, String errors) {
        this.status = status;
        this.data = data;
        this.error = errors;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
