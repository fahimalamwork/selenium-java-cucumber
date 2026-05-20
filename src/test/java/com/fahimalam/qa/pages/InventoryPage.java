package com.fahimalam.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

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
    }

    public int cartCount() {
        try {
            return Integer.parseInt(driver.findElement(CART_BADGE).getText());
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public void openCart() {
        clickable(CART_LINK).click();
    }
}
