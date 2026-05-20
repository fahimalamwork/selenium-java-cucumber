@cart-ui
Feature: Shopping cart

  Background:
    Given I am on the login page
    When I sign in as "standard_user" with password "secret_sauce"
    Then I see the inventory page

  Scenario: Add a single item to the cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge shows 1

  Scenario: Add multiple items to the cart
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    Then the cart badge shows 2
    When I open the cart
    Then the cart contains "Sauce Labs Backpack"
    And the cart contains "Sauce Labs Bike Light"
