package com.testautomation.helpers;

import com.testautomation.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.HttpClientConfig.httpClientConfig;

public final class RequestSpecFactory {
    private RequestSpecFactory() {}

    public static RequestSpecification jsonSpec() {

        RestAssuredConfig cfg = RestAssuredConfig.config()
                .httpClient(
                        httpClientConfig()
                                .setParam("http.connection.timeout", Config.connectTimeoutMs())
                                .setParam("http.socket.timeout", Config.readTimeoutMs())
                );

        return new RequestSpecBuilder()
                .setBaseUri(Config.baseUri())
                .setBasePath("/api")
                .setConfig(cfg)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)  // Log everything
                .build();
    }
}
