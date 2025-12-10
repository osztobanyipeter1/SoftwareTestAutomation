package com.testautomation.pages;

import com.testautomation.helpers.RandomDelayHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

//Base page class with common functionality for all pages
//U5 & U6: Page Object Model with inheritance

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    // U5: Private field for neptune code
    private final String neptuneCode = "InsertYourCodeHere";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    //Find element with explicit wait

    protected WebElement find(By locator) {
        RandomDelayHelper.waitABit(driver, locator);
        return driver.findElement(locator);
    }

    //Click element

    protected void click(By locator) {
        RandomDelayHelper.waitForClickable(driver, locator);
        find(locator).click();
    }

    //Type text into element

    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    //Get element text

    protected String getText(By locator) {
        return find(locator).getText();
    }

    //Check if element is displayed

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //Wait for URL to contain text

    protected void waitForUrl(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    //Get current URL

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    //Get page title

    public String getTitle() {
        return driver.getTitle();
    }

    protected void waitForClickable(By locator) {
        System.out.println("Waiting for element to be clickable: " + locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        System.out.println("Element is clickable!");
    }

    //Wait for element to be visible - returns boolean

    protected boolean waitForVisible(By locator, java.time.Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            );
            System.out.println("Element visible: " + locator);
            return true;
        } catch (Exception e) {
            System.out.println("Element NOT visible (timeout): " + locator);
            return false;
        }
    }

    //Wait for element to be visible - default 10s timeout

    protected boolean waitForVisible(By locator) {
        return waitForVisible(locator, java.time.Duration.ofSeconds(10));
    }



}
