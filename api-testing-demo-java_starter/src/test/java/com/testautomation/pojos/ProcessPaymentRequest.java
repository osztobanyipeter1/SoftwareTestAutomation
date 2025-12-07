package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class ProcessPaymentRequest {
    @SerializedName("orderId")
    private String orderId;

    @SerializedName("amount")
    private Double amount;

    public ProcessPaymentRequest() {}

    public ProcessPaymentRequest(String orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() { return orderId; }
    public Double getAmount() { return amount; }

    public ProcessPaymentRequest setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public ProcessPaymentRequest setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
