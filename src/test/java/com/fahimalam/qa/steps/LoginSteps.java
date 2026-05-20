package com.fahimalam.qa.steps;

import com.fahimalam.qa.pages.InventoryPage;
import com.fahimalam.qa.pages.LoginPage;
import com.fahimalam.qa.support.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private LoginPage login;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        login = new LoginPage(DriverFactory.get()).open();
    }

    @When("I sign in as {string} with password {string}")
    public void iSignInAsWithPassword(String user, String pass) {
        login.loginAs(user.isEmpty() ? null : user, pass.isEmpty() ? null : pass);
    }

    @Then("I see the inventory page")
    public void iSeeTheInventoryPage() {
        assertThat(new InventoryPage(DriverFactory.get()).isLoaded()).isTrue();
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String expected) {
        assertThat(login.errorText()).contains(expected);
    }
}
