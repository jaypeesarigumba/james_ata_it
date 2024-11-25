package com.example.stepdefinitions;

import com.example.testbase.webFactory;
import com.example.testbase.webUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;
import java.util.Objects;

public class CommonStepDefinitions {

    webUtils webLibrary = webUtils.getInstance();

    @Given("User navigates to {string}")
    public void iNavigateTo(String url) {
        webLibrary.launchWebPage(url);
    }

    @When("User login with username {string} and password {string}")
    public void userLoginWithUsernameAndPassword(String username, String password) {
        String username_xpath = "//input[@id='user-name']";
        String password_xpath = "//input[@id='password']";
        String login_xpath = "//input[@id='login-button']";
        webLibrary.enterText(username_xpath,username);
        webLibrary.enterText(password_xpath,password);
        webLibrary.elementClick(login_xpath);
    }

    @Then("User should see the {string} page")
    public void userShouldSeeThePage(String pageName) {
        String product_xpath="//span[@class='title']";
        String actualText = webLibrary.getElementTextByXpath(product_xpath);
        Assert.assertEquals(actualText, pageName, "Incorrect Page!");
    }

    @When("User sort products by price from {string}")
    public void userSortProductsByPriceFrom(String sort) {
        String sort_xpath="//select[@class='product_sort_container']";
        switch(sort){
            case "High to Low":
                webLibrary.selectValueByXpath(sort_xpath,"hilo");
                break;
            case "Low to High":
                webLibrary.selectValueByXpath(sort_xpath,"lohi");
                break;
            case "A to Z":
                webLibrary.selectValueByXpath(sort_xpath,"az");
                break;
            case "Z to A":
                webLibrary.selectValueByXpath(sort_xpath,"za");
                break;
            default: Assert.fail("Invalid sort value!");
        }
    }

    @Then("Products should be sorted from {string}")
    public void productsShouldBeSortedFrom(String sort) {
        switch(sort) {
            case "High to Low":
                webLibrary.validateProductsSortedByPrice("hilo");
                break;
            case "Low to High":
                webLibrary.validateProductsSortedByPrice("lohi");
                break;
            case "A to Z":
                webLibrary.validateProductsSortedByName("az");
                break;
            case "Z to A":
                webLibrary.validateProductsSortedByName("za");
                break;
            default:
                Assert.fail("Invalid sort value!");
        }
    }

    @Then("User adds 2 items priced at ${string} each to the cart")
    public void userAddsItemsPricedAtEachToTheCart(String price) {
        String item1_xpath="//div[text()='"+price+"']/following-sibling::button[@id='add-to-cart-sauce-labs-bolt-t-shirt']";
        String item2_xpath="//div[text()='"+price+"']/following-sibling::button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']";

        webLibrary.elementClick(item1_xpath);
        webLibrary.elementClick(item2_xpath);

    }

    @And("User proceeds to the Your Cart page")
    public void userProceedsToTheCheckoutPage() {
        String cart_xpath="//a[@class='shopping_cart_link']";
        webLibrary.elementClick(cart_xpath);
    }


    @Then("The cart should contain {int} items")
    public void theCartShouldContainItems(int items) {
        webLibrary.verifyCartItems(items);
    }

    @When("User completes adding personal details with:")
    public void userCompletesAddingPersonalDetailsWith(DataTable dt) {
        String checkout_xpath="//button[@id='checkout']";

        String firstName_xpath="//input[@id='first-name']";
        String lastName_xpath="//input[@id='last-name']";
        String zip_xpath="//input[@id='postal-code']";

        String continue_xpath="//input[@id='continue']";

        webLibrary.elementClick(checkout_xpath);

        Map<String, String> userDetails = dt.asMap(String.class, String.class);

        webLibrary.enterText(firstName_xpath,userDetails.get("First Name"));
        webLibrary.enterText(lastName_xpath,userDetails.get("Last Name"));
        webLibrary.enterText(zip_xpath,userDetails.get("Zip Code"));

        webLibrary.elementClick(continue_xpath);
    }

    @Then("User completes the checkout")
    public void userCompletesTheCheckout() {
        String paymentInfo_xpath="//div[@data-test='payment-info-value']";
        String shippingInfo_xpath="//div[@data-test='shipping-info-value']";
        String itemTotal_xpath="//div[@data-test='subtotal-label']";
        String tax_xpath="//div[@data-test='tax-label']";
        String totalPrice_xpath="//div[@data-test='total-label']";

        String finish_xpath="//button[@id='finish']";

        System.out.println("Payment Info: " + webLibrary.getElementTextByXpath(paymentInfo_xpath));
        System.out.println("Shipping Info: " + webLibrary.getElementTextByXpath(shippingInfo_xpath));
        System.out.println(webLibrary.getElementTextByXpath(itemTotal_xpath));
        System.out.println(webLibrary.getElementTextByXpath(tax_xpath));
        System.out.println(webLibrary.getElementTextByXpath(totalPrice_xpath));

        webLibrary.elementClick(finish_xpath);

    }

    @Then("User should see an error message saying {string}")
    public void userShouldSeeAnErrorMessageSaying(String message) {
        String error_xpath="//h3[@data-test='error']";
        String actualText = webLibrary.getElementTextByXpath(error_xpath);
        Assert.assertEquals(actualText, message, "Incorrect Error Message!");
    }
}
