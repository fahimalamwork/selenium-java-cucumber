package com.fahimalam.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final String URL = "https://www.saucedemo.com/";

    private static final By USERNAME = By.cssSelector("[data-test='username']");
    private static final By PASSWORD = By.cssSelector("[data-test='password']");
    private static final By SUBMIT = By.cssSelector("[data-test='login-button']");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    public void loginAs(String user, String pass) {
        if (user != null) visible(USERNAME).sendKeys(user);
        if (pass != null) visible(PASSWORD).sendKeys(pass);
        clickable(SUBMIT).click();
    }

    public String errorText() {
        return visible(ERROR).getText();
    }
}
