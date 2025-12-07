package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OrderResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private OrderData data;

    @SerializedName("timestamp")
    private String timestamp;

    public Boolean getSuccess() { return success; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public OrderData getData() { return data; }
    public String getTimestamp() { return timestamp; }

    public static class OrderData {
        @SerializedName("orderId")
        private String orderId;

        @SerializedName("userId")
        private String userId;

        @SerializedName("items")
        private List<OrderItem> items;

        @SerializedName("total")
        private Double total;

        @SerializedName("status")
        private String status;

        @SerializedName("paymentId")
        private String paymentId;

        @SerializedName("createdAt")
        private String createdAt;

        public String getOrderId() { return orderId; }
        public String getUserId() { return userId; }
        public List<OrderItem> getItems() { return items; }
        public Double getTotal() { return total; }
        public String getStatus() { return status; }
        public String getPaymentId() { return paymentId; }
        public String getCreatedAt() { return createdAt; }
    }

    public static class OrderItem {
        @SerializedName("itemId")
        private String itemId;

        @SerializedName("productId")
        private String productId;

        @SerializedName("productName")
        private String productName;

        @SerializedName("quantity")
        private Integer quantity;

        @SerializedName("unitPrice")
        private Double unitPrice;

        @SerializedName("totalPrice")
        private Double totalPrice;

        public String getItemId() { return itemId; }
        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public Double getUnitPrice() { return unitPrice; }
        public Double getTotalPrice() { return totalPrice; }
    }
}
