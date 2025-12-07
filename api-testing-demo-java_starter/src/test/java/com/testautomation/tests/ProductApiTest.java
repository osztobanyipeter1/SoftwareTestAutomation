package com.testautomation.tests;

import com.google.gson.Gson;
import com.testautomation.config.Config;
import com.testautomation.pojos.Product;
import com.testautomation.services.ProductService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductApiTest {

    private static ProductService products;

    private static String productId() {
        return System.getProperty("test.productId",
                Config.get("test.productId") != null ? Config.get("test.productId") : "demo");
    }

    @BeforeAll
    static void init() {
        products = new ProductService();
    }

    @Test
    @Order(1)
    void get_all_products_ok() {
        Response res = products.getAllProducts();
        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.contentType()).contains(ContentType.JSON.toString());
        assertThat(res.jsonPath().getList("$")).isNotNull();
    }

    @Test
    @Order(2)
    void get_product_by_id_ok() {
        Response res = products.getProduct(productId());
        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.contentType()).contains(ContentType.JSON.toString());
        assertThat(res.jsonPath().getString("productId")).isNotNull();
    }

    @Test
    @Order(3)
    void get_inventory_for_product_ok() {
        Response res = products.getInventory(productId());
        assertThat(res.statusCode()).isEqualTo(200);
        assertThat(res.contentType()).contains(ContentType.JSON.toString());
        assertThat(res.jsonPath().getMap("$")).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName("Get product maps to Product POJO and all fields are present")
    void get_product_maps_to_pojo_with_all_fields() {
        Response res = products.getProduct(productId());
        assertThat(res.statusCode()).isEqualTo(200);

        // Map JSON -> Product POJO using Gson
        Product p = new Gson().fromJson(res.asString(), Product.class);

        // Basic presence assertions
        assertThat(p).isNotNull();
        assertThat(p.getProductId()).isNotBlank();
        assertThat(p.getName()).isNotBlank();
        assertThat(p.getCategory()).isNotBlank();

        // Numeric fields should be present (you can tighten to >= 0 if your API guarantees)
        assertThat(p.getPrice()).isNotNull();
        assertThat(p.getStockQuantity()).isNotNull();
        assertThat(p.getReservedQuantity()).isNotNull();
        assertThat(p.getAvailableQuantity()).isNotNull();
    }
}
