package com.testautomation.tests;

import com.testautomation.annotations.ManualReview;
import com.testautomation.config.Config;
import com.testautomation.pojos.Inventory;
import com.testautomation.pojos.ReserveInventoryRequest;
import com.testautomation.services.InventoryService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@ManualReview
@DisplayName("Inventory API Tests")
public class InventoryApiTest {

    private static InventoryService inventory;
    private static String testProductId;

    @BeforeAll
    static void init() {
        inventory = new InventoryService();
        testProductId = Config.get("test.productId") != null ? Config.get("test.productId") : "laptop1";
    }

    @Test
    @DisplayName("A1_GET_INVENTORY - GET /api/inventory/{productId}/status")
    void get_inventory_status_ok() {
        Response res = inventory.getInventoryStatus(testProductId);

        System.out.println("GET INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
        assertThat(res.jsonPath().getString("data.productId")).isEqualTo(testProductId);
        assertThat(res.jsonPath().getInt("data.availableQuantity")).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("A4_NEGATIVE_INVALID_PRODUCT - GET inventory for non-existent product")
    void get_inventory_invalid_product_not_found() {
        Response res = inventory.getInventoryStatus("invalid-product-xyz-999");

        System.out.println("GET INVALID INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isIn(404, 400);
    }

    @Test
    @DisplayName("A1_RESERVE_INVENTORY - PUT /api/inventory/{productId}/reserve")
    void reserve_inventory_success() {
        ReserveInventoryRequest req = new ReserveInventoryRequest(1);
        Response res = inventory.reserveInventory(testProductId, req);

        System.out.println("RESERVE INVENTORY Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
    }

    @Test
    @DisplayName("A4_NEGATIVE_INVALID_QUANTITY - PUT inventory release with negative quantity")
    void release_inventory_invalid_quantity() {
        ReserveInventoryRequest req = new ReserveInventoryRequest(-1);
        Response res = inventory.releaseInventory(testProductId, req);

        System.out.println("RELEASE INVALID Response: " + res.asString());

        assertThat(res.statusCode()).isIn(400, 422);
    }
}
