package com.fahimalam.qa.support;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @After(order = 100)
    public void screenshotOnFailure(Scenario scenario) {
        WebDriver d = DriverFactory.get();
        if (scenario.isFailed() && d instanceof TakesScreenshot ts) {
            scenario.attach(ts.getScreenshotAs(OutputType.BYTES), "image/png", "failure");
        }
    }

    @After(order = 0)
    public void tearDown() {
        DriverFactory.quit();
    }
}
