package Tests;

import Pages.AccountRegistrationFullNamePage;
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

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class RegistrationTestLp5 extends BaseTest {
    @BeforeMethod
    public void setUp() {
        baseSetUp("CHROME", "113");
        driver.get("https://www.fortrade.com/minilps/en/invest-together-vid/");
    }

    @Test(description = "User register account successfully on - www.fortrade.com/minilps/en/invest-together-vid/ page")
    @Description("User register account successfully under certain regulation")
    @Parameters({"countryCodeNumber","regulative"})
    public void accountRegistration(String countryCodeNumber, String regulative) throws IOException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        accountRegistrationPage.accountRegistrationMethod("Testq", "Testa",
                "test" + System.currentTimeMillis() + "@mailinator.com",countryCodeNumber , ""+
                        System.currentTimeMillis());
//        /**
//         * ID of the original window
//         */
//        String originalWindow=driver.getWindowHandle();
//        /**
//         *Checking if there is no other windows open already
//         */
//        assert driver.getWindowHandles().size() ==1;
//        /**
//         * Wait for the new tab or the window
//         */
        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(numberOfWindowsToBe(2));
//        /**
//         * Loop through until we find a new window handle or tab
//         */
//        for(String windowHandle : driver.getWindowHandles()){
//            if(!originalWindow.contentEquals(windowHandle)){
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//        /**
//         * Wait for the new tab to finish loading content
//         */
//        wait.until(titleIs("Access trading platform | Fortrade"));

        //Waiting for continue button to bi visible and clickable
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='welcomePopup']//..//" +
                "div[@id='startTradingButton']")));
        wait.until(ExpectedConditions.visibilityOf(accountRegistrationPage.continueBtn));
        wait.until(ExpectedConditions.elementToBeClickable(accountRegistrationPage.continueBtn));
        accountRegistrationPage.clickElement(accountRegistrationPage.continueBtn,"continue button");
        //Waiting for data-lcreg attribute to bi visible
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[data-lcreg='"+regulative+"']")));
        //Finding element in Elements
        WebElement attribute = driver.findElement(By.cssSelector("body[data-lcreg='"+regulative+"']"));
        //Taking the value from attribute data-lcreg (FSC, FCA, cysec, Asic, iiroc) and storage as a String type
        String regulativeValue = attribute.getAttribute("data-lcreg");
        //Verifying they are matching
        Assert.assertEquals(regulativeValue,regulative);
        new BasePage(driver).reportScreenshot("Screenshot "+regulative+" regulative");
    }
    @Test(description = "Confirm validation messages")
    @jdk.jfr.Description("Appropriate error messages are triggered if user doesn't insert data")
    public void fieldsValidation() throws IOException {
        AccountRegistrationFullNamePage accountRegistrationFullNamePage = new AccountRegistrationFullNamePage(driver);
        accountRegistrationFullNamePage.clickElement(accountRegistrationFullNamePage.startNowBtn, "send button");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='errorValidationIn'" +
                " and text()='Enter your full name']")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='errorValidationIn'" +
                "and text()='Enter your full name']"))));

        WebElement fullNameValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidationIn'" +
                " and text()='Enter your full name']"));
        WebElement emailValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidationIn' " +
                "and text()='Invalid email format.']"));
        WebElement phoneNumberValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidati" +
                "onIn' and text()='Invalid phone format.']"));

        String fullNameErrorMessage = fullNameValidationMessage.getText();
        String emailErrorMessage = emailValidationMessage.getText();
        String phoneNumberErrorMessage = phoneNumberValidationMessage.getText();

        Assert.assertEquals(fullNameErrorMessage,"Enter your full name");
        System.out.println("The text of the full name field validation message is : "+fullNameErrorMessage);

        Assert.assertEquals(emailErrorMessage,"Invalid email format.");
        System.out.println("The text of the email field validation message is : " + emailErrorMessage);

        Assert.assertEquals(phoneNumberErrorMessage,"Invalid phone format.");
        System.out.println("The text of the phone number field validation message is : "
                + phoneNumberErrorMessage);
        new BasePage(driver).reportScreenshot("- Validation messages are triggered and displayed properly");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}