package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("items")
    private Object items;
    @SerializedName("totalPrice")
    private Double totalPrice;
    @SerializedName("status")
    private String status;
    @SerializedName("createdAt")
    private String createdAt;

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public Object getItems() { return items; }
    public Double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }

    public Order setOrderId(String orderId) { this.orderId = orderId; return this; }
    public Order setUserId(String userId) { this.userId = userId; return this; }
    public Order setItems(Object items) { this.items = items; return this; }
    public Order setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; return this; }
    public Order setStatus(String status) { this.status = status; return this; }
    public Order setCreatedAt(String createdAt) { this.createdAt = createdAt; return this; }
}
