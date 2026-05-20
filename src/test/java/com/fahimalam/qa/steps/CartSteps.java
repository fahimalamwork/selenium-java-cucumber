package com.fahimalam.qa.steps;

import com.fahimalam.qa.pages.CartPage;
import com.fahimalam.qa.pages.InventoryPage;
import com.fahimalam.qa.support.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class CartSteps {

    private InventoryPage inventory() {
        return new InventoryPage(DriverFactory.get());
    }

    private CartPage cart() {
        return new CartPage(DriverFactory.get());
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String item) {
        inventory().addItemToCart(item);
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int expected) {
        assertThat(inventory().cartCount()).isEqualTo(expected);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        inventory().openCart();
    }

    @Then("the cart contains {string}")
    public void theCartContains(String name) {
        assertThat(cart().hasItem(name)).isTrue();
    }
}
