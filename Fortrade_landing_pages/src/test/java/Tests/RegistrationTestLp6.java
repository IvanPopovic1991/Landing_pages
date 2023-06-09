package Tests;

import Pages.AccountRegistrationFullNamePage;
import Pages.AccountRegistrationPage;
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

public class RegistrationTestLp6 extends BaseTest {
    @BeforeMethod
    public void setUp() {
        baseSetUp("CHROME", "113");
        driver.get("https://www.fortrade.com/minilps/en/when-you-jump-into-online-trading-be-smart-about-it/");
    }

    @Test(description = "User register account successfully on - www.fortrade.com/minilps/en/when-you-jump-into-online" +
            "-trading-be-smart-about-it/")
    @Description("User register account successfully under certain regulation")
    @Parameters({"countryCodeNumber", "regulative"})
    public void accountRegistrationFullName(String countryCodeNumber, String regulative) throws IOException {

        AccountRegistrationFullNamePage accountRegistrationFullNamePage = new AccountRegistrationFullNamePage(driver);
        accountRegistrationFullNamePage.accountRegistrationFullNamePageMethod("Testq Testa", "test"
                + System.currentTimeMillis() + "@mailinator.com", "" + countryCodeNumber + "",
                "" + System.currentTimeMillis());
//        driver.getWindowHandles().forEach(tab->driver.switchTo().window(tab));
//        /**
//         * ID of the original window
//         */
//        String originalWindow = driver.getWindowHandle();
//        /**
//         *Checking if there is no other windows open already
//         */
//        assert driver.getWindowHandles().size() == 1;
//
//        /**
//         * Wait for the new tab or the window
//         */
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(numberOfWindowsToBe(2));
//        /**
//         * Loop through until we find a new window handle or tab
//         */
//        for (String windowHandle : driver.getWindowHandles()) {
//            if (!originalWindow.contentEquals(windowHandle)) {
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//        /**
//         * Wait for the new tab to finish loading content
//         */
//        wait.until(titleIs("Access trading platform | Fortrade"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='welcomePopup']//..//div[@id='startTradingButton']")));
        wait.until(ExpectedConditions.visibilityOf(accountRegistrationFullNamePage.continueBtn));
        wait.until(ExpectedConditions.elementToBeClickable(accountRegistrationFullNamePage.continueBtn));
        accountRegistrationFullNamePage.clickElement(accountRegistrationFullNamePage.continueBtn, "Continue button");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[data-lcreg='" + regulative + "']")));
        WebElement attribute = driver.findElement(By.cssSelector("body[data-lcreg='" + regulative + "']"));
        String regulativeValue = attribute.getAttribute("data-lcreg");
        Assert.assertEquals(regulativeValue, regulative);
        new BasePage(driver).reportScreenshot("Screenshot " + regulative + " regulative");
    }
    @Test(description ="Confirm validation messages")
    @Description("Appropriate error messages are triggered if user doesn't insert data")
    public void fieldsValidation() throws IOException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        accountRegistrationPage.clickElement(accountRegistrationPage.startNowBtn, "send button");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='errorValidati" +
                "onIn' and text()='Please enter all your given first name(s)']")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='errorValidati" +
                "onIn' and text()='Please enter all your given first name(s)']"))));

        WebElement firstNameValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidati" +
                "onIn' and text()='Please enter all your given first name(s)']"));
        WebElement lastNameValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidationIn'" +
                " and text()='Please enter your last name in alphabetic characters']"));
        WebElement emailValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidationIn' " +
                "and text()='Invalid email format.']"));
        WebElement phoneNumberValidationMessage = driver.findElement(By.xpath("//div[@class='errorValidati" +
                "onIn' and text()='Invalid phone format.']"));

        String firstNameErrorMessage = firstNameValidationMessage.getText();
        String lastNameErrorMessage = lastNameValidationMessage.getText();
        String emailErrorMessage = emailValidationMessage.getText();
        String phoneNumberErrorMessage = phoneNumberValidationMessage.getText();

        Assert.assertEquals(firstNameErrorMessage,"Please enter all your given first name(s)");
        System.out.println("The text of the first name field validation message is : "+firstNameErrorMessage);

        Assert.assertEquals(lastNameErrorMessage,"Please enter your last name in alphabetic characters");
        System.out.println("The text of the last name field validation message is : "+lastNameErrorMessage);

        Assert.assertEquals(emailErrorMessage,"Invalid email format.");
        System.out.println("The text of the email field validation message is : "+emailErrorMessage);

        Assert.assertEquals(phoneNumberErrorMessage,"Invalid phone format.");
        System.out.println("The text of the phone number field validation message is : "+phoneNumberErrorMessage);
        new BasePage(driver).reportScreenshot("Screenshot - validation messages are triggered and displayed properly");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}