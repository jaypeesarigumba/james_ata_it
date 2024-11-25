package com.example.testbase;

import org.openqa.selenium.WebDriver;

public class webFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webdriver){
        driver.set(webdriver);
    }

    public static void removeDriver(){
        driver.remove();
    }
}
