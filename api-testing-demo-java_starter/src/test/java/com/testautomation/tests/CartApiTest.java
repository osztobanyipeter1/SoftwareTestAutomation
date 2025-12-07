package com.testautomation.tests;

import com.testautomation.config.Config;
import com.testautomation.pojos.CartItemRequest;
import com.testautomation.services.CartService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@DisplayName("Cart API Tests")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartApiTest {

    private static CartService cart;
    private static String testUserId;
    private static String testProductId;

    @BeforeAll
    static void init() {
        cart = new CartService();
        testUserId = Config.get("test.userId") != null ? Config.get("test.userId") : "user1";
        testProductId = Config.get("test.productId") != null ? Config.get("test.productId") : "laptop1";
    }

    @Test
    @DisplayName("A1_ADD_ITEM_TO_CART - POST /api/cart/{userId}/items")
    void add_item_to_cart_success() {
        CartItemRequest req = new CartItemRequest(testProductId, 1);
        Response res = cart.addItemToCart(testUserId, req);

        System.out.println("ADD ITEM TO CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 201);
        assertThat(res.jsonPath().getBoolean("success")).isTrue();
    }

    @Test
    @DisplayName("A1_GET_CART - GET /api/cart/{userId}")
    void get_cart_ok() {
        Response res = cart.getCart(testUserId);

        System.out.println("GET CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 404);
    }

    @Test
    @DisplayName("A1_CALCULATE_CART - POST /api/cart/{userId}/calculate")
    void calculate_cart_ok() {
        Response res = cart.calculateCart(testUserId);

        System.out.println("CALCULATE CART Response: " + res.asString());

        assertThat(res.statusCode()).isIn(200, 404);
    }
}
