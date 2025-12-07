package com.testautomation.services;

import com.google.gson.Gson;
import com.testautomation.helpers.RequestSpecFactory;
import com.testautomation.pojos.ReserveInventoryRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class InventoryService {
    private final Gson gson = new Gson();

    public Response getInventoryStatus(String productId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/inventory/" + productId + "/status")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response reserveInventory(String productId, ReserveInventoryRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .put("/api/inventory/" + productId + "/reserve")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response releaseInventory(String productId, ReserveInventoryRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .put("/api/inventory/" + productId + "/release")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response commitInventory(String productId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .put("/api/inventory/" + productId + "/commit")  // Changed: /api prefix
                .then().extract().response();
    }
}
