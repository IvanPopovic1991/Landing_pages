package Tests;

import Pages.AccountRegistrationFullNamePage;
import Pages.BasePage;
import jdk.jfr.Description;
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

public class RegistrationTestLp2 extends  BaseTest{

    @BeforeMethod
    public void setUp(){
        baseSetUp("CHROME","112");
    }
    @Test(description = "User register account successfully on - www.fortrade.com/minilps/sl/pro-trader-dark-2/")
    @Description("User register account successfully under certain regulation")
    @Parameters({"countryCodeNumber","regulative"})
    public void accountRegistrationFullName(String countryCodeNumber, String regulative) throws IOException {
        driver.get("https://www.fortrade.com/minilps/sl/pro-trader-dark-2/");
        AccountRegistrationFullNamePage accountRegistrationFullNamePage = new AccountRegistrationFullNamePage(driver);
        accountRegistrationFullNamePage.accountRegistrationFullNamePageMethod("Testq Testa","test"
        +System.currentTimeMillis()+"@mailinator.com",""+countryCodeNumber+"",""+System.currentTimeMillis());
       // accountRegistrationFullNamePage.clickElement(accountRegistrationFullNamePage.continueBtn, "Continue button");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[data-lcreg='"+regulative+"']")));
        WebElement attribute = driver.findElement(By.cssSelector("body[data-lcreg='"+regulative+"']"));
        String regulativeValue = attribute.getAttribute("data-lcreg");
        Assert.assertEquals(regulativeValue,regulative);
        new BasePage(driver).reportScreenshot("Screenshot "+regulative+" regulative");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
