package Selenium_core;

import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class OperaDriverManager extends DriverManager{
    /**
     * Method for creating WebDriver;
     *
     * @param version
     */
    @Override
    public void createWebDriver(String version) {
        System.setProperty("webdriver.opera.driver","C:\\Users\\ivanp\\Desktop\\Automation projects\\Landing pages\\" +
                "Fortrade_landing_pages\\src\\main\\resources\\operadriver"+version+".exe");
        OperaOptions options = new OperaOptions();
        options.addArguments("start-maximized");
        driver = new OperaDriver(options);
    }
}
