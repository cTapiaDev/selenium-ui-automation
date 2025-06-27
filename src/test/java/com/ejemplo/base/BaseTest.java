package com.ejemplo.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry; 
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected WebDriver driver;
    protected Properties configProperties;

    public String getBaseUrl() { return configProperties.getProperty("app.base.url"); }
    public String getUsername() { return configProperties.getProperty("app.username"); }
    public String getPassword() { return configProperties.getProperty("app.password"); }

    @BeforeAll
    public void setupTest() {
        configProperties = new Properties();
        try {
            configProperties.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            System.err.println("Error al cargar la configuración de propiedades: " + e.getMessage());
            throw new RuntimeException();
        }

        String seleniumGridUrl = System.getProperty("selenium.grid.url", "http://localhost:4444/wd/hub");
        String browserType = System.getProperty("browser.type", "chrome");

        try {
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("goog:loggingPrefs", logPrefs);

            if (browserType.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                capabilities.setBrowserName("chrome");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--handless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080");
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            } else if (browserType.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                capabilities.setBrowserName("firefox");
            } else {
                throw new IllegalArgumentException();
            }

            driver = new RemoteWebDriver(new URL(seleniumGridUrl), capabilities);
            driver.manage().window().maximize();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    @AfterAll
    public void teardownTest() {
        if (driver != null) {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

            for (LogEntry entry : logEntries) {
                System.out.println(entry.getLevel() + " " + entry.getMessage());
            }

            driver.quit();
        }
    }

    
}
