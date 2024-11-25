@saucedemo-tests @saucedemo-checkout
Feature: Product Page Sorting

  Background:
    Given User navigates to "https://www.saucedemo.com/"
    When User login with username "standard_user" and password "secret_sauce"

  @sauce-demo-positive-test
  Scenario: User is able to add 2 items to cart and checkout successfully
    When User adds 2 items priced at $"15.99" each to the cart
    And User proceeds to the Your Cart page
    Then The cart should contain 2 items

    When User completes adding personal details with:
      | First Name | James     |
      | Last Name  | Sarigumba |
      | Zip Code   | 1234      |
    Then User should see the "Checkout: Overview" page
    And User completes the checkout
    Then User should see the "Checkout: Complete!" page

