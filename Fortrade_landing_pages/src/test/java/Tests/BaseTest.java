package Tests;

import Selenium_core.DriverManager;
import Selenium_core.DriverManagerFactory;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    WebDriver driver;
    DriverManager driverManager;

    public void baseSetUp(String browser, String version){
        driverManager = DriverManagerFactory.getDriverManager(browser);
        driver = driverManager.getWebDriver(version);
        driver.manage().window().maximize();
    }
}
