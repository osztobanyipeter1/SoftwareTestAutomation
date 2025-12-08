package com.testautomation.annotations;

import io.qameta.allure.Step;

public class AllureSteps {

    @Step("API GET request to {endpoint}")
    public static void stepApiGet(String endpoint) {
        // Placeholder for Allure step tracking
    }

    @Step("API POST request to {endpoint} with body: {body}")
    public static void stepApiPost(String endpoint, String body) {
        // Placeholder for Allure step tracking
    }

    @Step("API PUT request to {endpoint} with body: {body}")
    public static void stepApiPut(String endpoint, String body) {
        // Placeholder for Allure step tracking
    }

    @Step("API DELETE request to {endpoint}")
    public static void stepApiDelete(String endpoint) {
        // Placeholder for Allure step tracking
    }

    @Step("Assert that status code is {statusCode}")
    public static void stepAssertStatusCode(int statusCode) {
        // Placeholder for Allure step tracking
    }

    @Step("Assert that response contains {fieldName} = {expectedValue}")
    public static void stepAssertField(String fieldName, Object expectedValue) {
        // Placeholder for Allure step tracking
    }

    @Step("Create test data: {description}")
    public static void stepTestData(String description) {
        // Placeholder for Allure step tracking
    }
}
