package Tests;

import Pages.AccountRegistrationPage;
import Pages.BasePage;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestLp9 extends BaseTest{
    @BeforeMethod
    public void setUp() {
        baseSetUp("CHROME", "112");
    }

    @Test(description = "User register account successfully on " +
            "www.fortrade.com/minilps/en/reg-place-stocks-in-your-future/ page")
    @Description("User register account successfully under certain regulation")
    @Parameters({"countryCodeNumber", "regulative"})
    public void accountRegistration(String countryCodeNumber, String regulative) throws IOException {
        driver.get("https://www.fortrade.com/minilps/en/reg-place-stocks-in-your-future/");
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        accountRegistrationPage.accountRegistrationMethod("Testq", "Testa",
                "test" + System.currentTimeMillis() + "@mailinator.com", countryCodeNumber, "" +
                        System.currentTimeMillis());
        //Waiting for data-lcreg attribute to bi visible
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[data-lcreg='" + regulative + "']")));
        //Finding element in Elements
        WebElement attribute = driver.findElement(By.cssSelector("body[data-lcreg='" + regulative + "']"));
        //Taking the value from attribute data-lcreg (FSC, FCA, cysec, Asic, iiroc) and storage as a String type
        String regulativeValue = attribute.getAttribute("data-lcreg");
        //Verifying they are matching
        Assert.assertEquals(regulativeValue, regulative);
        new BasePage(driver).reportScreenshot("Screenshot " + regulative + " regulative");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
