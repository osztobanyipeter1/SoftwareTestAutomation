package com.testautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//Login page object
//U1 & U2: Basic page with reasonable locators

public class LoginPage extends BasePage {

    // U2: Good locators - using data-test and id attributes
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToLoginPage(String baseUrl) {
        driver.get(baseUrl);
        System.out.println("Navigated to login page: " + baseUrl);
    }

    public void login(String username, String password) {
        System.out.println("Attempting login with username: " + username);
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        System.out.println("Login clicked");
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginButton);
    }
}
