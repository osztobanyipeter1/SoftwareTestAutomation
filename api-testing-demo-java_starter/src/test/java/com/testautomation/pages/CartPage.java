package com.testautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

//Shopping cart page object
//U3: Cart component coverage

public class CartPage extends BasePage {

    // Cart items - exclude removed items
    private final By cartItems = By.xpath("//div[@data-test='inventory-item' and not(contains(@class, 'removed'))]");
    private final By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrice = By.cssSelector("[data-test='inventory-item-price']");

    // Remove button
    private final By removeButton = By.xpath("//button[contains(@data-test, 'remove-')]");

    private final By checkoutButton = By.cssSelector("[data-test='checkout']");
    private final By continueShoppingButton = By.cssSelector("[data-test='continue-shopping']");
    private final By cartSummaryInfo = By.className("summary_info");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageDisplayed() {
        try {
            return driver.getCurrentUrl().contains("cart.html");
        } catch (Exception e) {
            return false;
        }
    }

    //Get cart item count - counts actual inventory-item divs (not removed ones)

    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        System.out.println("🛒 Cart has " + items.size() + " items");
        return items.size();
    }

    public List<String> getCartItemNames() {
        List<WebElement> names = driver.findElements(itemName);
        return names.stream().map(WebElement::getText).toList();
    }

    //Remove first item from cart
    //The button has data-test like "remove-sauce-labs-backpack"

    public void removeFirstItem() {
        System.out.println("Removing first item from cart");
        try {
            // Wait for remove buttons to be visible
            waitForVisible(removeButton, java.time.Duration.ofSeconds(3));

            List<WebElement> removeButtons = driver.findElements(removeButton);
            if (!removeButtons.isEmpty()) {
                System.out.println("   Found " + removeButtons.size() + " remove button(s)");
                removeButtons.get(0).click();
                System.out.println("Item remove button clicked");

                // Wait for item to be removed from DOM
                Thread.sleep(800);
            } else {
                System.out.println("No remove buttons found!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //Remove all items from cart

    public void removeAllItems() {
        System.out.println("Removing ALL items from cart");
        int removed = 0;
        try {
            while (true) {
                List<WebElement> removeButtons = driver.findElements(removeButton);
                if (removeButtons.isEmpty()) break;

                removeButtons.get(0).click();
                removed++;
                Thread.sleep(500);
            }
            System.out.println("Removed " + removed + " items");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void checkout() {
        System.out.println("Proceeding to checkout");
        click(checkoutButton);
    }

    public void continueShopping() {
        click(continueShoppingButton);
    }
}
