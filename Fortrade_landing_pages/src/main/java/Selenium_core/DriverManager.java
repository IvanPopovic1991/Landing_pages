package Selenium_core;

import org.openqa.selenium.WebDriver;

/**
 * Create abstract class DriverManager - method is used for creating driver
 */
public abstract class DriverManager {
    /**
     * Create WebDriver field named driver. Field has keyword protected because it should not be accessible to
     * anyone except classes that inherit from the class DriverManager
     */
    protected WebDriver driver;

    /**
     * Method for creating WebDriver;
     */
    public abstract void createWebDriver(String version);

    /**
     *  If driver is not null quit it and set null value
     */
    public void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    /**
     * If driver value is null before WebDriver is returned create WebDriver; Method getWebDriver has
     * return type WebDriver (driver)
     * @param version
     * @return
     */
    public WebDriver getWebDriver(String version) {
        if (driver == null) {
            createWebDriver(version);
        }
        return driver;
    }
}