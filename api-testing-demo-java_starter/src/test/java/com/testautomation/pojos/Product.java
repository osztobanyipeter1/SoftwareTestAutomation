package com.testautomation.pojos;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productId")
    private String productId;
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("price")
    private Double price;
    @SerializedName("stockQuantity")
    private Integer stockQuantity;
    @SerializedName("reservedQuantity")
    private Integer reservedQuantity;
    @SerializedName("availableQuantity")
    private Integer availableQuantity;

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public Double getPrice() { return price; }
    public Integer getStockQuantity() { return stockQuantity; }
    public Integer getReservedQuantity() { return reservedQuantity; }
    public Integer getAvailableQuantity() { return availableQuantity; }

    public Product setProductId(String productId) { this.productId = productId; return this; }
    public Product setName(String name) { this.name = name; return this; }
    public Product setCategory(String category) { this.category = category; return this; }
    public Product setPrice(Double price) { this.price = price; return this; }
    public Product setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
    public Product setReservedQuantity(Integer reservedQuantity) { this.reservedQuantity = reservedQuantity; return this; }
    public Product setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; return this; }
}
