package com.testautomation.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

//Test setup and cleanup helper class

public class TestSetupHelper {

    //Clear all items from cart - navigate to cart and remove all items

    public static void clearCart(WebDriver driver) {
        System.out.println("Clearing cart...");
        try {
            // Navigate to cart
            driver.navigate().to("https://www.saucedemo.com/cart.html");
            Thread.sleep(1000);

            // Find and click ALL remove buttons
            List<WebElement> removeButtons = driver.findElements(
                    By.xpath("//button[contains(@data-test, 'remove-')]")
            );

            int itemsRemoved = 0;
            int maxAttempts = 10;

            while (!removeButtons.isEmpty() && itemsRemoved < maxAttempts) {
                System.out.println("  → Removing item " + (itemsRemoved + 1));
                try {
                    removeButtons.get(0).click();
                    Thread.sleep(600);
                    removeButtons = driver.findElements(
                            By.xpath("//button[contains(@data-test, 'remove-')]")
                    );
                    itemsRemoved++;
                } catch (Exception e) {
                    System.out.println("Error clicking remove button: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Cart cleared! Removed " + itemsRemoved + " items");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("InterruptedException while clearing cart");
        } catch (Exception e) {
            System.out.println("Error clearing cart: " + e.getMessage());
        }
    }

    //Clear cart and navigate to products page

    public static void resetState(WebDriver driver) {
        clearCart(driver);
        try {
            driver.navigate().to("https://www.saucedemo.com/inventory.html");
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
