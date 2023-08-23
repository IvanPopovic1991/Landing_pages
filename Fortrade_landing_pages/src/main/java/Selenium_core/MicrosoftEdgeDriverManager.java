package Selenium_core;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class MicrosoftEdgeDriverManager extends DriverManager{
    /**
     * Method for creating WebDriver;
     *
     * @param version
     */
    @Override
    public void createWebDriver(String version) {
        System.setProperty("webdriver.edge.driver","C:\\Users\\ivanp\\Desktop\\Automation projects\\Landing pages\\" +
                "Fortrade_landing_pages\\src\\main\\resources\\msedgedriver"+version+".exe");
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
    }
}
