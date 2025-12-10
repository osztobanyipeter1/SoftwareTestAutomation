package com.testautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

//Checkout page object
//U3 & U7: Checkout flow

public class CheckoutPage extends BasePage {

    // Checkout Step One
    private final By firstNameInput = By.cssSelector("[data-test='firstName']");
    private final By lastNameInput = By.cssSelector("[data-test='lastName']");
    private final By postalCodeInput = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By cancelButton = By.cssSelector("[data-test='cancel']");

    // Checkout Step Two
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By completeMessage = By.cssSelector("[data-test='complete-text']");
    private final By successMessage = By.cssSelector("[data-test='complete-header']");
    private final By backHomeButton = By.cssSelector("[data-test='back-to-products']");

    // Summary info for Step 2
    private final By summaryInfo = By.className("summary_info");
    private final By checkoutStepTwo = By.xpath("//*[contains(text(), 'Checkout: Overview')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        System.out.println("Filling checkout form");
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        System.out.println("Form filled");
    }

    public void clickContinue() {
        System.out.println("Clicking continue button");
        waitForClickable(continueButton);
        click(continueButton);
        System.out.println("Continue clicked");
    }

    public void clickCancel() {
        System.out.println("Clicking cancel button");
        waitForClickable(cancelButton);
        click(cancelButton);
        System.out.println("Cancel clicked - returning to cart");
    }

    public void clickFinish() {
        System.out.println("Clicking finish button");
        waitForClickable(finishButton);
        click(finishButton);
        System.out.println("Finish clicked");
    }

    public boolean isCheckoutStepTwoDisplayed() {
        System.out.println("Checking for checkout step 2 (overview)");
        return waitForVisible(summaryInfo, Duration.ofSeconds(5));
    }

    public boolean isOrderCompleted() {
        System.out.println("Checking for order completion");
        return waitForVisible(completeMessage, Duration.ofSeconds(5));
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public void backHome() {
        click(backHomeButton);
    }
}
