package com.testautomation.helpers;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecFactory {
    private ResponseSpecFactory() {}

    public static ResponseSpecification jsonResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType("application/json")
                .build();
    }

    public static ResponseSpecification successResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType("application/json")
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification createdResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType("application/json")
                .expectStatusCode(201)
                .build();
    }
}
