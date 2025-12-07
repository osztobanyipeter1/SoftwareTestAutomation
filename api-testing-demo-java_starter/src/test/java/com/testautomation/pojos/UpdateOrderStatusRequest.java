package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderStatusRequest {
    @SerializedName("status")
    private String status;

    public UpdateOrderStatusRequest() {}

    public UpdateOrderStatusRequest(String status) {
        this.status = status;
    }

    public String getStatus() { return status; }

    public UpdateOrderStatusRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
