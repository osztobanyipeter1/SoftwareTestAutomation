package com.testautomation.services;

import com.google.gson.Gson;
import com.testautomation.helpers.RequestSpecFactory;
import com.testautomation.pojos.CreateOrderRequest;
import com.testautomation.pojos.UpdateOrderStatusRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderService {
    private final Gson gson = new Gson();

    public Response createOrder(CreateOrderRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .post("/api/orders")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response getOrder(String orderId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/orders/" + orderId)  // Changed: /api prefix
                .then().extract().response();
    }

    public Response updateOrderStatus(String orderId, UpdateOrderStatusRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .put("/api/orders/" + orderId + "/status")  // Changed: /api prefix
                .then().extract().response();
    }
}
