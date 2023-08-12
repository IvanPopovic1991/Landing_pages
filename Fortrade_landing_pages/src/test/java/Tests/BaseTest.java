package Tests;

import Selenium_core.DriverManager;
import Selenium_core.DriverManagerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BaseTest {
    WebDriver driver;
    DriverManager driverManager;

    public void baseSetUp(String browser, String version){
        driverManager = DriverManagerFactory.getDriverManager(browser);
        driver = driverManager.getWebDriver(version);
        driver.manage().window().maximize();
    }
    public void newWindowIsOpen(){
        /**
         * ID of the original window
         */
        String originalWindow=driver.getWindowHandle();
        /**
         *Checking if there is no other windows open already
         */
        //      assert driver.getWindowHandles().size() ==1;
        /**
         * Wait for the new tab or the window
         */
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(numberOfWindowsToBe(2));
        /**
         * Loop through until we find a new window handle or tab
         */
        for(String windowHandle : driver.getWindowHandles()){
            if(!originalWindow.contentEquals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        /**
         * Wait for the new tab to finish loading content
         */
    }
}
