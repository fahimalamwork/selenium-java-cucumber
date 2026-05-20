Feature: Login

  Background:
    Given I am on the login page

  Scenario: Standard user signs in successfully
    When I sign in as "standard_user" with password "secret_sauce"
    Then I see the inventory page

  Scenario: Locked-out user is rejected
    When I sign in as "locked_out_user" with password "secret_sauce"
    Then I see the error message "Sorry, this user has been locked out."

  Scenario Outline: Invalid credentials surface a clear error
    When I sign in as "<user>" with password "<password>"
    Then I see the error message "<message>"

    Examples:
      | user           | password         | message                                                       |
      | standard_user  | wrong_password   | Username and password do not match any user in this service   |
      |                | secret_sauce     | Username is required                                          |
      | standard_user  |                  | Password is required                                          |
