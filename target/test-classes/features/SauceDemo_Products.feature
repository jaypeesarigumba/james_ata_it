@saucedemo-tests @saucedemo-products
Feature: Login and complete a purchase on SauceDemo

  Background:
    Given User navigates to "https://www.saucedemo.com/"
    When User login with username "standard_user" and password "secret_sauce"

  @saucedemo-positive-test
  Scenario: User is able to sort products from High to Low
    When User sort products by price from "High to Low"
    Then Products should be sorted from "High to Low"

  @saucedemo-positive-test
  Scenario: User is able to sort products from High to Low
    When User sort products by price from "Low to High"
    Then Products should be sorted from "Low to High"


