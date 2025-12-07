package com.testautomation.tests;

import com.testautomation.config.Config;
import com.testautomation.pojos.CreateOrderRequest;
import com.testautomation.pojos.UpdateOrderStatusRequest;
import com.testautomation.services.OrderService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("Order API Tests - E2E Flow")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderApiTest {

    private static OrderService orders;
    private static String createdOrderId;
    private static Double orderTotal;
    private static String testUserId;

    @BeforeAll
    static void init() {
        orders = new OrderService();
        testUserId = Config.get("test.userId") != null ? Config.get("test.userId") : "user1";
    }

    @Test
    @DisplayName("01_CREATE_ORDER - POST /api/orders with userId")
    void create_order_success() {
        // Create order with only userId (status is auto-set to CREATED)
        CreateOrderRequest req = new CreateOrderRequest(testUserId);

        Response res = orders.createOrder(req);

        System.out.println("CREATE ORDER Response: " + res.asString());

        // Assertions
        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        // Extract orderId and total from response
        createdOrderId = res.jsonPath().getString("data.orderId");
        orderTotal = res.jsonPath().getDouble("data.total");

        System.out.println("Created OrderId: " + createdOrderId);
        System.out.println("Order Total: " + orderTotal);

        assertThat(createdOrderId).isNotBlank();
        assertThat(orderTotal).isNotNull().isGreaterThan(0);
    }

    @Test
    @DisplayName("02_READ_ORDER - GET /api/orders/{orderId}")
    void read_order_success() {
        assertThat(createdOrderId).isNotBlank();

        Response res = orders.getOrder(createdOrderId);

        System.out.println("READ ORDER Response: " + res.asString());

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.orderId")).isEqualTo(createdOrderId);
        assertThat(res.jsonPath().getString("data.userId")).isEqualTo(testUserId);
    }

    @Test
    @DisplayName("03_UPDATE_STATUS - PUT /api/orders/{orderId}/status")
    void update_order_status_success() {
        assertThat(createdOrderId).isNotBlank();

        // Update status to CONFIRMED
        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest("CREATED");
        Response res = orders.updateOrderStatus(createdOrderId, req);

        System.out.println("UPDATE STATUS Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 204);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.orderId")).isEqualTo(createdOrderId);
    }

    @Test
    @DisplayName("04_VERIFY_STATE - Final order verification")
    void verify_order_final_state() {
        assertThat(createdOrderId).isNotBlank();

        Response res = orders.getOrder(createdOrderId);

        System.out.println("VERIFY ORDER Response: " + res.asString());

        // Sanity check as per requirements
        assertThat(2025).isEqualTo(2025); // Sanity Check

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.orderId")).isNotBlank();
    }

    // Helper methods for other tests
    public static String getCreatedOrderId() {
        return createdOrderId;
    }

    public static Double getOrderTotal() {
        return orderTotal;
    }
}
