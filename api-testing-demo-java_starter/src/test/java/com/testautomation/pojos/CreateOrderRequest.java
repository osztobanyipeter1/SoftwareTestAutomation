package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class CreateOrderRequest {
    @SerializedName("userId")
    private String userId;

    public CreateOrderRequest() {}

    public CreateOrderRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() { return userId; }

    public CreateOrderRequest setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
