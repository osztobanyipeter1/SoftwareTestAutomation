package com.testautomation.services;

import com.google.gson.Gson;
import com.testautomation.helpers.RequestSpecFactory;
import com.testautomation.pojos.Product;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductService {
    private final Gson gson = new Gson();

    public Response getAllProducts() {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/products")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response getProduct(String productId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/products/" + productId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response getInventory(String productId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/products/" + productId + "/inventory")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response createProduct(Product product) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(product))
                .when()
                .post("/api/products")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response updateProduct(String productId, Product product) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(product))
                .when()
                .put("/api/products/" + productId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response deleteProduct(String productId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .delete("/api/products/" + productId)  // Changed: /api prefix
                .then().extract().response();
    }
}
