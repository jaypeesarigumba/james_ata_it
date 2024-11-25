package com.example.stepdefinitions;

import com.example.testbase.webFactory;
import com.example.testbase.webUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHooks {
    webUtils webInstance = webUtils.getInstance();

    @Before
    public void before(){
        webFactory.setDriver(webInstance.webDriverInit());
    }

    @After
    public void after(){
       webInstance.closeBrowser();
    }
}
