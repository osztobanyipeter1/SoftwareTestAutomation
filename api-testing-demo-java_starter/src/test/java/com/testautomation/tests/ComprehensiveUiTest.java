package com.testautomation.tests;

import com.testautomation.config.Config;
import com.testautomation.helpers.TestLogger;
import com.testautomation.helpers.TestSetupHelper;
import com.testautomation.pages.LoginPage;
import com.testautomation.pages.ProductPage;
import com.testautomation.pages.CartPage;
import com.testautomation.pages.CheckoutPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@DisplayName("Comprehensive UI Tests - Sauce Demo E2E")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ComprehensiveUiTest {

    private static String baseUrl;
    private WebDriver driver;

    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeAll
    static void setUpClass() {
        System.out.println("Setting up WebDriver");
        WebDriverManager.chromedriver().setup();
        baseUrl = Config.get("uiBaseUrl") != null ? Config.get("uiBaseUrl") : "https://www.saucedemo.com";
        System.out.println("Base URL: " + baseUrl);
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);

        //Clear cart before each test to ensure clean state
        TestSetupHelper.clearCart(driver);

        System.out.println("Chrome driver initialized");
    }


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Chrome driver closed");
        }
    }

    // ===== U1 & U2: BASIC LOGIN TESTS =====

    @Test
    @DisplayName("U1_01_LOGIN_PAGE_LOADS - Check login page displays")
    void test_login_page_loads() {
        TestLogger.logTestStart("test_login_page_loads");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);

        assertThat(loginPage.isLoginPageDisplayed()).isTrue();
        System.out.println("Login page loaded");

        TestLogger.logTestEnd("test_login_page_loads");
    }

    @Test
    @DisplayName("U1_02_SUCCESSFUL_LOGIN - Login with valid credentials")
    void test_successful_login() {
        TestLogger.logTestStart("test_successful_login");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        assertThat(productPage.isProductsPageDisplayed()).isTrue();
        System.out.println("Successfully logged in");

        TestLogger.logTestEnd("test_successful_login");
    }

    @Test
    @DisplayName("U1_03_INVALID_LOGIN - Login fails with wrong credentials")
    void test_invalid_login() {
        TestLogger.logTestStart("test_invalid_login");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("invalid_user", "wrong_password");

        assertThat(loginPage.isErrorMessageDisplayed()).isTrue();
        System.out.println("Error message displayed");

        TestLogger.logTestEnd("test_invalid_login");
    }

    // ===== U3: COMPONENT-LEVEL TESTS (PRODUCTS) =====

    @Test
    @DisplayName("U3_04_PRODUCTS_LOAD - Verify products page loads with items")
    void test_products_page_loaded() {
        TestLogger.logTestStart("test_products_page_loaded");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        assertThat(productPage.isProductsPageDisplayed()).isTrue();

        int productCount = productPage.getProductCount();
        assertThat(productCount).isGreaterThan(0);
        System.out.println("Products page loaded with " + productCount + " products");

        TestLogger.logTestEnd("test_products_page_loaded");
    }

    @Test
    @DisplayName("U3_05_PRODUCT_NAMES - Get product names from list")
    void test_get_product_list() {
        TestLogger.logTestStart("test_get_product_list");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        var productNames = productPage.getProductNames();

        assertThat(productNames).isNotEmpty();
        System.out.println("Found " + productNames.size() + " product names");
        productNames.forEach(name -> System.out.println("   - " + name));

        TestLogger.logTestEnd("test_get_product_list");
    }

    @Test
    @DisplayName("U3_06_ADD_TO_CART - Add product to shopping cart")
    void test_add_to_cart() {
        TestLogger.logTestStart("test_add_to_cart");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();

        int cartCount = productPage.getCartItemCount();
        assertThat(cartCount).isGreaterThan(0);
        System.out.println("Product added. Cart count: " + cartCount);

        TestLogger.logTestEnd("test_add_to_cart");
    }

    @Test
    @DisplayName("U3_07_PRODUCT_PRICE - Verify product price displayed")
    void test_verify_price() {
        TestLogger.logTestStart("test_verify_price");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        String price = productPage.getFirstProductPrice();

        assertThat(price).isNotBlank().contains("$");
        System.out.println("Product price: " + price);

        TestLogger.logTestEnd("test_verify_price");
    }

    // ===== U3: COMPONENT-LEVEL TESTS (CART) =====

    @Test
    @DisplayName("U3_08_CART_PAGE - Open and verify cart page")
    void test_cart_page() {
        TestLogger.logTestStart("test_cart_page");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();
        productPage.goToCart();

        cartPage = new CartPage(driver);
        assertThat(cartPage.isCartPageDisplayed()).isTrue();
        int cartCount = cartPage.getCartItemCount();
        assertThat(cartCount).isGreaterThan(0);
        System.out.println("Cart page displayed with " + cartCount + " items");

        TestLogger.logTestEnd("test_cart_page");
    }

    @Test
    @DisplayName("U3_09_REMOVE_FROM_CART - Remove item from cart")
    void test_remove_from_cart() {
        TestLogger.logTestStart("test_remove_from_cart");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();
        productPage.goToCart();

        cartPage = new CartPage(driver);
        int initialCount = cartPage.getCartItemCount();
        cartPage.removeFirstItem();
        int finalCount = cartPage.getCartItemCount();

        assertThat(finalCount).isLessThan(initialCount);
        System.out.println("Item removed. Cart: " + initialCount + " → " + finalCount);

        TestLogger.logTestEnd("test_remove_from_cart");
    }

    // ===== U4: WAITS TEST =====

    @Test
    @DisplayName("U4_WAITS - Test explicit waits without Thread.sleep")
    void test_waits_without_thread_sleep() {
        TestLogger.logTestStart("test_waits_without_thread_sleep");
        System.out.println("⏳ Testing waits (no Thread.sleep)");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);

        // Uses RandomDelayHelper waits internally
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        assertThat(productPage.isProductsPageDisplayed()).isTrue();

        System.out.println("All waits completed without Thread.sleep");

        TestLogger.logTestEnd("test_waits_without_thread_sleep");
    }

    // ===== U7: E2E FLOWS =====

    @Test @DisplayName("U7_10_E2E_COMPLETE_CHECKOUT - Full purchase flow")
    void test_e2e_complete_checkout() {
        TestLogger.logTestStart("test_e2e_complete_checkout");

        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();
        productPage.goToCart();

        cartPage = new CartPage(driver);
        cartPage.checkout();

        checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        checkoutPage.clickContinue();

        assertThat(checkoutPage.isCheckoutStepTwoDisplayed()).isTrue();
        checkoutPage.clickFinish();
        assertThat(checkoutPage.isOrderCompleted()).isTrue();

        TestLogger.logTestEnd("test_e2e_complete_checkout");
    }



    @Test
    @DisplayName("U7_11_E2E_MULTIPLE_PRODUCTS - Add multiple products and checkout")
    void test_e2e_multiple_products() {
        TestLogger.logTestStart("test_e2e_multiple_products");
        System.out.println("E2E Flow: Multiple Products → Checkout");

        // Login
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.login("standard_user", "secret_sauce");

        // Add multiple products
        productPage = new ProductPage(driver);
        productPage.addProductToCartByIndex(0);
        productPage.addProductToCartByIndex(1);
        productPage.addProductToCartByIndex(2);

        int cartCount = productPage.getCartItemCount();
        System.out.println("Added 3 products. Cart count: " + cartCount);
        assertThat(cartCount).isGreaterThanOrEqualTo(3);

        // Go to cart
        productPage.goToCart();
        cartPage = new CartPage(driver);
        assertThat(cartPage.getCartItemCount()).isGreaterThanOrEqualTo(3);
        System.out.println("All products in cart");

        // Checkout
        cartPage.checkout();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCheckoutForm("Peter", "Kope", "1234");
        checkoutPage.clickContinue();

        // Wait for Step 2 (explicit wait)
        assertThat(checkoutPage.isCheckoutStepTwoDisplayed()).isTrue();

        checkoutPage.clickFinish();

        // Wait for completion (explicit wait)
        assertThat(checkoutPage.isOrderCompleted()).isTrue();
        System.out.println("Multiple products checkout complete!");

        TestLogger.logTestEnd("test_e2e_multiple_products");
    }

    @Test
    @DisplayName("U7_12_E2E_CANCEL_CHECKOUT - Verify cancel button works")
    void test_e2e_cancel_checkout() {
        TestLogger.logTestStart("test_e2e_cancel_checkout");
        System.out.println("E2E Flow: Cancel Checkout");

        try {
            loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage(baseUrl);
            loginPage.login("standard_user", "secret_sauce");

            productPage = new ProductPage(driver);
            productPage.addFirstProductToCart();
            productPage.goToCart();

            cartPage = new CartPage(driver);
            assertThat(cartPage.isCartPageDisplayed()).isTrue();
            System.out.println("Cart page displayed");

            // Go to checkout
            cartPage.checkout();

            // Wait a moment for checkout page to load
            Thread.sleep(1000);

            checkoutPage = new CheckoutPage(driver);

            // Click cancel button
            checkoutPage.clickCancel();

            // Wait for navigation back to cart
            Thread.sleep(1000);

            // Verify we're back at cart page
            assertThat(cartPage.isCartPageDisplayed()).isTrue();
            System.out.println("Cancel worked - back to cart page");

            // Optional: verify item is still in cart
            int cartCount = cartPage.getCartItemCount();
            assertThat(cartCount).isGreaterThan(0);
            System.out.println("Item still in cart after cancel: " + cartCount);

        } catch (InterruptedException e) {
            System.err.println("Test interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        TestLogger.logTestEnd("test_e2e_cancel_checkout");
    }

}
