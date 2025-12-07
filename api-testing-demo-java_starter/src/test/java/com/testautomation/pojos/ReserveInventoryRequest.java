package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class ReserveInventoryRequest {
    @SerializedName("quantity")
    private Integer quantity;

    public ReserveInventoryRequest() {}

    public ReserveInventoryRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() { return quantity; }

    public ReserveInventoryRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
