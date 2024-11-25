package com.example.testbase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class webUtils {

    private static webUtils webInstance = null;

    public synchronized static webUtils getInstance() {
        if (webInstance == null) {
            webInstance = new webUtils();
        }
        return webInstance;
    }

    public synchronized WebDriver webDriverInit() {
        String browser = "edge"; //temporarily hard coded, TODO: value to be retrieved from globalSettings file
        WebDriver driver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        return driver;
    }

    public void launchWebPage(String url) {
        try {
            WebDriver driver = webFactory.getDriver();
            driver.get(url);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeBrowser(){
        webFactory.getDriver().quit();
        webFactory.removeDriver();
    }


    public String getElementTextByXpath(String xpath){
        WebDriver driver = webFactory.getDriver();
        WebElement element = driver.findElement(By.xpath(xpath));
        return element.getText();
    }

    public Boolean elementIsVisible(String xpath){
        WebDriver driver = webFactory.getDriver();
        WebElement element = driver.findElement(By.xpath(xpath));
        return element.isDisplayed();
    }

    public void elementClick(String xpath){
        WebDriver driver = webFactory.getDriver();
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
    }

    public void enterText(String xpath,String value){
        WebDriver driver = webFactory.getDriver();
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void selectValueByXpath(String xpath, String value){
        WebDriver driver = webFactory.getDriver();
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByValue(value);
    }


    //=======SauceDemo Custom functions============

    public void validateProductsSortedByPrice(String sort) {
        WebDriver driver = webFactory.getDriver();
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".inventory_item_price"));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            prices.add(Double.parseDouble(priceElement.getText().replace("$", "")));
        }

        boolean isSorted = true;

        switch (sort) {
            case "hilo":
                for (int i = 1; i < prices.size(); i++) {
                    if (prices.get(i) > prices.get(i - 1)) {
                        isSorted = false;
                        break;
                    }
                }
                break;
            case "lohi":
                for (int i = 1; i < prices.size(); i++) {
                    if (prices.get(i) < prices.get(i - 1)) {
                        isSorted = false;
                        break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid sort value!");
        }
        if (!isSorted) {
            throw new AssertionError("The products are not sorted in " + sort);
        }
    }

    public void validateProductsSortedByName(String sort){
        //TODO:add logic---currently out of scope
    }

    public void verifyCartItems(int items){
        WebDriver driver = webFactory.getDriver();
        List<WebElement> cartItems = driver.findElements(By.cssSelector("[data-test='inventory-item']"));

        if (cartItems.size() != items) {
            throw new AssertionError("Expected "+items+"items in the cart, but found " + cartItems.size());
        }
    }
}



