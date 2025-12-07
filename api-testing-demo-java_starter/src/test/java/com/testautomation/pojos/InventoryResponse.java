package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class InventoryResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private InventoryData data;

    @SerializedName("timestamp")
    private String timestamp;

    public Boolean getSuccess() { return success; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public InventoryData getData() { return data; }
    public String getTimestamp() { return timestamp; }

    // Inner class for data
    public static class InventoryData {
        @SerializedName("productId")
        private String productId;

        @SerializedName("productName")
        private String productName;

        @SerializedName("totalStock")
        private Integer totalStock;

        @SerializedName("reservedQuantity")
        private Integer reservedQuantity;

        @SerializedName("availableQuantity")
        private Integer availableQuantity;

        @SerializedName("price")
        private Double price;

        @SerializedName("category")
        private String category;

        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public Integer getTotalStock() { return totalStock; }
        public Integer getReservedQuantity() { return reservedQuantity; }
        public Integer getAvailableQuantity() { return availableQuantity; }
        public Double getPrice() { return price; }
        public String getCategory() { return category; }
    }
}
