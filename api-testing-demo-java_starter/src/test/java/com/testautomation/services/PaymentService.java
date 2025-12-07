package com.testautomation.services;

import com.google.gson.Gson;
import com.testautomation.helpers.RequestSpecFactory;
import com.testautomation.pojos.ProcessPaymentRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PaymentService {
    private final Gson gson = new Gson();

    public Response processPayment(ProcessPaymentRequest req) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .body(gson.toJson(req))
                .when()
                .post("/api/payments/process")  // Changed: /api prefix
                .then().extract().response();
    }

    public Response getPaymentStatus(String paymentId) {
        return given()
                .spec(RequestSpecFactory.jsonSpec())
                .when()
                .get("/api/payments/" + paymentId + "/status")  // Changed: /api prefix
                .then().extract().response();
    }
}
