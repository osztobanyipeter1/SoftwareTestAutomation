package com.testautomation.services;

import com.google.gson.Gson;
import com.testautomation.helpers.RequestSpecFactory;
import com.testautomation.pojos.CartItemRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CartService {
    private final Gson gson = new Gson();

    public Response getCart(String userId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/cart/" + userId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response addItemToCart(String userId, CartItemRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .post("/api/cart/" + userId + "/items")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response updateCartItem(String userId, String itemId, CartItemRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .put("/api/cart/" + userId + "/items/" + itemId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response removeCartItem(String userId, String itemId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .delete("/api/cart/" + userId + "/items/" + itemId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response calculateCart(String userId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .post("/api/cart/" + userId + "/calculate")  // Changed: /api prefix
                .then().extract().response();
    }
}
