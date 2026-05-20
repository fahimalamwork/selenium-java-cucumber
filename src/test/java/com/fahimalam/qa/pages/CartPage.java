package com.fahimalam.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasItem(String name) {
        return !driver.findElements(By.xpath(String.format("//div[@class='cart_item']//div[text()='%s']", name)))
                .isEmpty();
    }

    public int itemCount() {
        return driver.findElements(By.className("cart_item")).size();
    }
}
