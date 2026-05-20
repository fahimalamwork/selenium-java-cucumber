package com.fahimalam.qa.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage extends BasePage {

    private static final By TITLE = By.className("title");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Products".equals(visible(TITLE).getText());
    }

    public void addItemToCart(String itemName) {
        String id = itemName.toLowerCase().replaceAll("\\s+", "-");
        clickable(By.cssSelector(String.format("[data-test='add-to-cart-%s']", id))).click();
        // After click, saucedemo swaps the "Add to cart" button for a "Remove" button.
        // Waiting for that swap confirms the click registered before the next assertion runs.
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(String.format("[data-test='remove-%s']", id))));
    }

    public int cartCount() {
        // Short wait so the badge has time to appear after an add — but a missing badge
        // (empty cart) shouldn't block the test for the full implicit-wait window.
        try {
            String text = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(CART_BADGE))
                    .getText();
            return Integer.parseInt(text);
        } catch (TimeoutException | NoSuchElementException e) {
            return 0;
        }
    }

    public void openCart() {
        clickable(CART_LINK).click();
    }
}
