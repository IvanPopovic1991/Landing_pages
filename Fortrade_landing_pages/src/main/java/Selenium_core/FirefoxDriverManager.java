package Selenium_core;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
/** Indicates how the driver will be created;
 * Implements abstract method : public abstract void createWebDriver();
 */
public class FirefoxDriverManager extends DriverManager{
    @Override
    public void createWebDriver(String version) {
        System.setProperty("webdriver.gecko.driver","C:\\Users\\ivanp\\Desktop\\Automation projects\\Landing pages\\" +
                "Fortrade_landing_pages\\src\\main\\resources\\geckodriver"+version+".exe");
        // options is defined if we want to run headless test
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("start-maximized");
        driver = new FirefoxDriver(options);
    }
}
