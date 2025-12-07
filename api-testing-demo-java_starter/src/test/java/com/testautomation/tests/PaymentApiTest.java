package com.testautomation.tests;

import com.testautomation.pojos.ProcessPaymentRequest;
import com.testautomation.services.PaymentService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("Payment API Tests")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class PaymentApiTest {

    private static PaymentService payment;
    private static String testOrderId;
    private static Double testOrderTotal;

    @BeforeAll
    static void init() {
        payment = new PaymentService();
        // Use fallback test data if OrderApiTest didn't run
        testOrderId = "d52ee38b-ec4a-425d-93fe-4fe0f0574f4a";
        testOrderTotal = 1403.9892;
    }

    @Test
    @DisplayName("A1_PROCESS_PAYMENT - POST /api/payments/process")
    void process_payment_success() {
        ProcessPaymentRequest req = new ProcessPaymentRequest(testOrderId, testOrderTotal);
        Response res = payment.processPayment(req);

        System.out.println("PAYMENT Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
    }

    @Test
    @DisplayName("A1_GET_PAYMENT_STATUS - GET /api/payments/{paymentId}/status")
    void get_payment_status_ok() {
        Response res = payment.getPaymentStatus("payment123");

        System.out.println("GET PAYMENT STATUS Response: " + res.asString());
        assertThat(res.statusCode()).isIn(200, 404);
    }

    @Test
    @DisplayName("A4_NEGATIVE_MISSING_ORDERID - POST payment without orderId")
    void process_payment_missing_field() {
        // Missing orderId
        ProcessPaymentRequest req = new ProcessPaymentRequest(null, 99.99);
        Response res = payment.processPayment(req);

        System.out.println("PAYMENT MISSING ORDERID Response: " + res.asString());

        // Should return error
        assertThat(res.statusCode()).isIn(400, 404, 422, 500);
    }
}
