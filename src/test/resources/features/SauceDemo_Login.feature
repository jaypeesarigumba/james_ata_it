@saucedemo-tests @saucedemo-login
Feature: User login to SauceDemo website

  Background:
    Given User navigates to "https://www.saucedemo.com/"

  @saucedemo-login-success @saucedemo-positive-test
  Scenario: User is able to login successfully
    When User login with username "standard_user" and password "secret_sauce"
    Then User should see the "Products" page


  @saucedemo-login-failed @saucedemo-negative-test
  Scenario: User is unable to login due to locked account
    When User login with username "locked_out_user" and password "secret_sauce"
    Then User should see an error message saying "Epic sadface: Sorry, this user has been locked out."
