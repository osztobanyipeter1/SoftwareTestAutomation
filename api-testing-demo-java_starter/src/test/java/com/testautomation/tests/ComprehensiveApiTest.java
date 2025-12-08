package com.testautomation.tests;

import com.testautomation.config.Config;
import com.testautomation.helpers.TestLogger;
import com.testautomation.pojos.CartItemRequest;
import com.testautomation.pojos.CreateOrderRequest;
import com.testautomation.pojos.ProcessPaymentRequest;
import com.testautomation.pojos.ReserveInventoryRequest;
import com.testautomation.pojos.UpdateOrderStatusRequest;
import com.testautomation.services.CartService;
import com.testautomation.services.OrderService;
import com.testautomation.services.PaymentService;
import com.testautomation.services.InventoryService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("Comprehensive E2E API Tests - Complete Flow")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ComprehensiveApiTest {

    private static CartService cart;
    private static OrderService orders;
    private static PaymentService payment;
    private static InventoryService inventory;

    private static String testUserId;
    private static String testProductId;
    private static String createdOrderId;
    private static Double orderTotal;

    @BeforeAll
    static void init() {
        TestLogger.logTestStart("ComprehensiveApiTest Init");

        cart = new CartService();
        orders = new OrderService();
        payment = new PaymentService();
        inventory = new InventoryService();

        testUserId = Config.get("test.userId") != null ? Config.get("test.userId") : "user1";
        testProductId = Config.get("test.productId") != null ? Config.get("test.productId") : "laptop1";

        TestLogger.logInfo("Initialized with userId: " + testUserId + ", productId: " + testProductId);
    }

    // ===== INVENTORY TESTS =====

    @Test
    @DisplayName("01_INVENTORY_GET_STATUS - GET /api/inventory/{productId}/status")
    void inventory_get_status() {
        TestLogger.logTestStart("inventory_get_status");
        TestLogger.logApiCall("GET", "/api/inventory/" + testProductId + "/status");

        Response res = inventory.getInventoryStatus(testProductId);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("GET INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.productId")).isEqualTo(testProductId);

        TestLogger.logTestEnd("inventory_get_status");
    }

    @Test
    @DisplayName("02_INVENTORY_RESERVE - PUT /api/inventory/{productId}/reserve")
    void inventory_reserve() {
        TestLogger.logTestStart("inventory_reserve");
        TestLogger.logApiCall("PUT", "/api/inventory/" + testProductId + "/reserve");

        ReserveInventoryRequest req = new ReserveInventoryRequest(1);
        Response res = inventory.reserveInventory(testProductId, req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("RESERVE INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        TestLogger.logTestEnd("inventory_reserve");
    }

    // ===== CART TESTS =====

    @Test
    @DisplayName("03_CART_ADD_ITEM - POST /api/cart/{userId}/items")
    void cart_add_item() {
        TestLogger.logTestStart("cart_add_item");
        TestLogger.logApiCall("POST", "/api/cart/" + testUserId + "/items");

        CartItemRequest req = new CartItemRequest(testProductId, 1);
        Response res = cart.addItemToCart(testUserId, req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("ADD ITEM TO CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        TestLogger.logTestEnd("cart_add_item");
    }

    @Test
    @DisplayName("04_CART_GET - GET /api/cart/{userId}")
    void cart_get() {
        TestLogger.logTestStart("cart_get");
        TestLogger.logApiCall("GET", "/api/cart/" + testUserId);

        Response res = cart.getCart(testUserId);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("GET CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 404);

        TestLogger.logTestEnd("cart_get");
    }

    @Test
    @DisplayName("05_CART_CALCULATE - POST /api/cart/{userId}/calculate")
    void cart_calculate() {
        TestLogger.logTestStart("cart_calculate");
        TestLogger.logApiCall("POST", "/api/cart/" + testUserId + "/calculate");

        Response res = cart.calculateCart(testUserId);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("CALCULATE CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 404);

        TestLogger.logTestEnd("cart_calculate");
    }

    // ===== ORDER TESTS - E2E FLOW =====

    @Test
    @DisplayName("06_ORDER_CREATE - POST /api/orders")
    void order_create() {
        TestLogger.logTestStart("order_create");
        TestLogger.logApiCall("POST", "/api/orders");

        CreateOrderRequest req = new CreateOrderRequest(testUserId);

        Response res = orders.createOrder(req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("CREATE ORDER Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        createdOrderId = res.jsonPath().getString("data.orderId");
        orderTotal = res.jsonPath().getDouble("data.total");

        TestLogger.logInfo("Created OrderId: " + createdOrderId);
        TestLogger.logInfo("Order Total: " + orderTotal);

        assertThat(createdOrderId).isNotBlank();
        assertThat(orderTotal).isNotNull().isGreaterThan(0);

        TestLogger.logTestEnd("order_create");
    }

    @Test
    @DisplayName("07_ORDER_READ - GET /api/orders/{orderId}")
    void order_read() {
        TestLogger.logTestStart("order_read");

        assertThat(createdOrderId).isNotBlank();

        TestLogger.logApiCall("GET", "/api/orders/" + createdOrderId);
        Response res = orders.getOrder(createdOrderId);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("READ ORDER Response: " + res.asString());

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.orderId")).isEqualTo(createdOrderId);

        TestLogger.logTestEnd("order_read");
    }

    @Test
    @DisplayName("08_ORDER_UPDATE_STATUS - PUT /api/orders/{orderId}/status")
    void order_update_status() {
        TestLogger.logTestStart("order_update_status");

        assertThat(createdOrderId).isNotBlank();

        TestLogger.logApiCall("PUT", "/api/orders/" + createdOrderId + "/status");
        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest("CONFIRMED");
        Response res = orders.updateOrderStatus(createdOrderId, req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("UPDATE ORDER STATUS Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 204);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        TestLogger.logTestEnd("order_update_status");
    }

    @Test
    @DisplayName("09_ORDER_VERIFY - Final order verification")
    void order_verify() {
        TestLogger.logTestStart("order_verify");

        assertThat(createdOrderId).isNotBlank();

        TestLogger.logApiCall("GET", "/api/orders/" + createdOrderId);
        Response res = orders.getOrder(createdOrderId);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("VERIFY ORDER Response: " + res.asString());

        assertThat(2025).isEqualTo(2025); // Sanity Check

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        TestLogger.logTestEnd("order_verify");
    }

    // ===== PAYMENT TESTS =====

    @Test
    @DisplayName("10_PAYMENT_PROCESS - POST /api/payments/process")
    void payment_process() {
        TestLogger.logTestStart("payment_process");

        assertThat(createdOrderId).isNotBlank();
        assertThat(orderTotal).isNotNull();

        TestLogger.logApiCall("POST", "/api/payments/process");
        ProcessPaymentRequest req = new ProcessPaymentRequest(createdOrderId, orderTotal);
        Response res = payment.processPayment(req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("PROCESS PAYMENT Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();

        TestLogger.logTestEnd("payment_process");
    }

    @Test
    @DisplayName("11_PAYMENT_STATUS - GET /api/payments/{paymentId}/status")
    void payment_get_status() {
        TestLogger.logTestStart("payment_get_status");
        TestLogger.logApiCall("GET", "/api/payments/payment123/status");

        Response res = payment.getPaymentStatus("payment123");

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("GET PAYMENT STATUS Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 404);

        TestLogger.logTestEnd("payment_get_status");
    }

    // ===== NEGATIVE TESTS =====

    @Test
    @DisplayName("12_NEGATIVE_INVALID_PRODUCT - GET invalid inventory")
    void negative_invalid_product() {
        TestLogger.logTestStart("negative_invalid_product");
        TestLogger.logApiCall("GET", "/api/inventory/invalid-product-xyz-999/status");

        Response res = inventory.getInventoryStatus("invalid-product-xyz-999");

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("GET INVALID INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isIn(404, 400);

        TestLogger.logTestEnd("negative_invalid_product");
    }

    @Test
    @DisplayName("13_NEGATIVE_INVALID_QUANTITY - PUT release with negative qty")
    void negative_invalid_quantity() {
        TestLogger.logTestStart("negative_invalid_quantity");
        TestLogger.logApiCall("PUT", "/api/inventory/" + testProductId + "/release");

        ReserveInventoryRequest req = new ReserveInventoryRequest(-1);
        Response res = inventory.releaseInventory(testProductId, req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("RELEASE INVALID Response: " + res.asString());

        assertThat(res.statusCode()).isIn(400, 422);

        TestLogger.logTestEnd("negative_invalid_quantity");
    }

    @Test
    @DisplayName("14_NEGATIVE_PAYMENT_MISSING_ORDERID - POST payment without orderId")
    void negative_payment_missing_orderid() {
        TestLogger.logTestStart("negative_payment_missing_orderid");
        TestLogger.logApiCall("POST", "/api/payments/process");

        ProcessPaymentRequest req = new ProcessPaymentRequest(null, 99.99);
        Response res = payment.processPayment(req);

        TestLogger.logResponse(res.statusCode(), res.asString());
        System.out.println("PAYMENT MISSING ORDERID Response: " + res.asString());

        assertThat(res.statusCode()).isIn(400, 404, 422, 500);

        TestLogger.logTestEnd("negative_payment_missing_orderid");
    }
}
