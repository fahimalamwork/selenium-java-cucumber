package com.fahimalam.qa.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/** Thread-local WebDriver supplier so parallel scenarios stay isolated. */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver get() {
        WebDriver d = DRIVER.get();
        if (d == null) {
            d = build();
            d.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            DRIVER.set(d);
        }
        return d;
    }

    public static void quit() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            d.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver build() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        return switch (browser) {
            case "firefox" -> {
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("-headless");
                yield new FirefoxDriver(opts);
            }
            default -> {
                ChromeOptions opts = new ChromeOptions();
                opts.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1366,900");
                if (headless) opts.addArguments("--headless=new");
                yield new ChromeDriver(opts);
            }
        };
    }
}
