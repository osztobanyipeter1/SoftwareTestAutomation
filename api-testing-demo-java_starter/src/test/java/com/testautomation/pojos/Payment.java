package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("paymentId")
    private String paymentId;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("amount")
    private Double amount;
    @SerializedName("status")
    private String status;
    @SerializedName("paymentMethod")
    private String paymentMethod;
    @SerializedName("createdAt")
    private String createdAt;

    public String getPaymentId() { return paymentId; }
    public String getOrderId() { return orderId; }
    public Double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getCreatedAt() { return createdAt; }

    public Payment setPaymentId(String paymentId) { this.paymentId = paymentId; return this; }
    public Payment setOrderId(String orderId) { this.orderId = orderId; return this; }
    public Payment setAmount(Double amount) { this.amount = amount; return this; }
    public Payment setStatus(String status) { this.status = status; return this; }
    public Payment setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
    public Payment setCreatedAt(String createdAt) { this.createdAt = createdAt; return this; }
}
