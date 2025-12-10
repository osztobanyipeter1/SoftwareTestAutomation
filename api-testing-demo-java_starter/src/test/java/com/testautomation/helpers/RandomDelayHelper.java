package com.testautomation.helpers;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;


//Helper class for waits without using Thread.sleep()
public class RandomDelayHelper {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public static void waitABit(WebDriver driver, By locator) {
        System.out.println("Waiting for element to be visible: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("Element found and visible!");
    }

    public static void waitForClickable(WebDriver driver, By locator) {
        System.out.println("Waiting for element to be clickable: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        System.out.println("Element is clickable!");
    }

    public static void waitForInvisibility(WebDriver driver, By locator) {
        System.out.println("⏳ Waiting for element to disappear: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        System.out.println("✅ Element disappeared!");
    }

    public static void waitForTitle(WebDriver driver, String titlePart) {
        System.out.println("⏳ Waiting for title to contain: " + titlePart);
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.titleContains(titlePart));
        System.out.println("✅ Title found!");
    }
}
