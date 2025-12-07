package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("userId")
    private String userId;
    @SerializedName("items")
    private Object items;
    @SerializedName("totalPrice")
    private Double totalPrice;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public String getUserId() { return userId; }
    public Object getItems() { return items; }
    public Double getTotalPrice() { return totalPrice; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    public Cart setUserId(String userId) { this.userId = userId; return this; }
    public Cart setItems(Object items) { this.items = items; return this; }
    public Cart setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; return this; }
    public Cart setCreatedAt(String createdAt) { this.createdAt = createdAt; return this; }
    public Cart setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; return this; }
}
