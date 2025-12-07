package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class CartItemRequest {
    @SerializedName("productId")
    private String productId;

    @SerializedName("quantity")
    private Integer quantity;

    public CartItemRequest() {}

    public CartItemRequest(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }

    public CartItemRequest setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public CartItemRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
