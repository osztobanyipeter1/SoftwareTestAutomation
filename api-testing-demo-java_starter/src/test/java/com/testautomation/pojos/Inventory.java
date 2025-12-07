package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class Inventory {
    @SerializedName("productId")
    private String productId;
    @SerializedName("stockQuantity")
    private Integer stockQuantity;
    @SerializedName("reservedQuantity")
    private Integer reservedQuantity;
    @SerializedName("availableQuantity")
    private Integer availableQuantity;
    @SerializedName("lastUpdated")
    private String lastUpdated;

    public String getProductId() { return productId; }
    public Integer getStockQuantity() { return stockQuantity; }
    public Integer getReservedQuantity() { return reservedQuantity; }
    public Integer getAvailableQuantity() { return availableQuantity; }
    public String getLastUpdated() { return lastUpdated; }

    public Inventory setProductId(String productId) { this.productId = productId; return this; }
    public Inventory setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
    public Inventory setReservedQuantity(Integer reservedQuantity) { this.reservedQuantity = reservedQuantity; return this; }
    public Inventory setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; return this; }
    public Inventory setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; return this; }
}
