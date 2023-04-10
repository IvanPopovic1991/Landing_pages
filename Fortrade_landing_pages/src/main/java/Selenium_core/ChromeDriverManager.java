package Selenium_core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/** Indicates how the driver will be created;
 * Implements abstract method : public abstract void createWebDriver();
 */
public class ChromeDriverManager extends DriverManager {
    @Override
    public void createWebDriver(String version) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ivanp\\Desktop\\Automation projects\\Landing pages\\Fortrade_landing_pages\\src\\main\\resources" +
                "\\chromedriver" + version + ".exe");
        // options is defined if we want to run headless test
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }
}