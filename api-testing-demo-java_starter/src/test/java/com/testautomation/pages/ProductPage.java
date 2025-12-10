package com.testautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

//Products page object
//U3: Component-level coverage

public class ProductPage extends BasePage {

    // Data-test locators (U2: good locators)
    private final By productList = By.cssSelector("[data-test='inventory-list']");
    private final By productItems = By.cssSelector("[data-test='inventory-item']");
    private final By productName = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrice = By.cssSelector("[data-test='inventory-item-price']");
    private final By addToCartButton = By.xpath("//button[contains(@data-test, 'add-to-cart')]");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By filterDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.cssSelector("[data-test='logout-sidebar-link']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductsPageDisplayed() {
        return isDisplayed(productList);
    }

    public int getProductCount() {
        List<WebElement> products = driver.findElements(productItems);
        System.out.println("Found " + products.size() + " products");
        return products.size();
    }

    public List<String> getProductNames() {
        List<WebElement> names = driver.findElements(productName);
        return names.stream().map(WebElement::getText).toList();
    }

    public void addFirstProductToCart() {
        System.out.println("🛒 Adding first product to cart");
        List<WebElement> addButtons = driver.findElements(addToCartButton);
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            System.out.println("Product added to cart");
        }
    }

    public void addProductToCartByIndex(int index) {
        System.out.println("🛒 Adding product " + index + " to cart");
        List<WebElement> addButtons = driver.findElements(addToCartButton);
        if (index < addButtons.size()) {
            addButtons.get(index).click();
            System.out.println("Product " + index + " added to cart");
        }
    }

    public void goToCart() {
        System.out.println("Going to cart");
        click(cartLink);
    }

    public int getCartItemCount() {
        try {
            String badgeText = getText(cartBadge);
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            return 0;
        }
    }

    public String getFirstProductPrice() {
        List<WebElement> prices = driver.findElements(productPrice);
        return prices.isEmpty() ? "" : prices.get(0).getText();
    }

    public void logout() {
        System.out.println("Logging out");
        click(menuButton);
        try {
            Thread.sleep(500); // Menu animation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        click(logoutLink);
    }
}
