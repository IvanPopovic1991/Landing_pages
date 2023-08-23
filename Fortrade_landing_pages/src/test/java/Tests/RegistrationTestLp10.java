package Tests;
import Pages.AccountRegistrationPage;
import Pages.BasePage;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class RegistrationTestLp10 extends BaseTest {
    @BeforeMethod
    public void setUp() {
        baseSetUp("OPERA", "114");
        driver.get("https://www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-gushing-at-fortrade/");
    }

    @Test(description = "User register account successfully on-www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-" +
            "gushing-at-fortrade/")
    @Description("User register account successfully under certain regulation")
    @Parameters({"countryCodeNumber", "regulative"})
    public void accountRegistration(String countryCodeNumber, String regulative) throws IOException {

        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        //Entering test data in sign in form
        accountRegistrationPage.accountRegistrationMethod("Testq","Testa","test"
        +System.currentTimeMillis()+"@mailinator.com",countryCodeNumber,""+System.currentTimeMillis());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        //Waiting for continue button to be visible and clickable
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='welcomePopup']//..//div" +
                "[@id='startTradingButton']")));
        wait.until(ExpectedConditions.visibilityOf(accountRegistrationPage.continueBtn));
        wait.until(ExpectedConditions.elementToBeClickable(accountRegistrationPage.continueBtn));
        accountRegistrationPage.clickElement(accountRegistrationPage.continueBtn, "Continue button");
        //Waiting for data-lcreg attribute to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[data-lcreg='" + regulative + "']")));
        //Finding data-lcreg attribute in Console
        WebElement attribute = driver.findElement(By.cssSelector("body[data-lcreg='" + regulative + "']"));
        //Taking the value from attribute data-lcreg (FSC, FCA, cysec, Asic, iiroc) and storage as a String type
        String regulativeValue = attribute.getAttribute("data-lcreg");
        //Verifying they are matching
        Assert.assertEquals(regulativeValue, regulative);
        new BasePage(driver).reportScreenshot("Screenshot " + regulative + " regulative");
    }
    @Test(description = "Check if user is already registered")
    @Description("User try to register already registered account")
    @Parameters({"countryCodeNumber"})
    public void userExist(String countryCodeNumber) throws IOException {
        /**
         navigate to the landing page - driver.get() is triggered from setUp method
         */
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        /**
         *register new account
         */
        accountRegistrationPage.accountRegistrationMethod("Testq","Testa",
                "test"+System.currentTimeMillis()+"@mailinator.com",""+countryCodeNumber,
                ""+System.currentTimeMillis());
        WebDriverWait wdWait = new WebDriverWait(driver,10);
        /**
         *save the email address used during account registration
         */
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='credetialsBox']//span" +
                "[@id='loginUserEmail']")));
        WebElement email = driver.findElement(By.xpath("//div[@class='credetialsBox']//span[@id='loginUserEmail']"));
        String emailAddress = email.getText();
        driver.close();
        /**
         * navigate to the same landing page in the new window
         */
        baseSetUp("OPERA","114");
        driver.get("https://www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-gushing-at-fortrade/");
        /**
         * try to register account by using email address from previous registration (already registered email address)
         */
        AccountRegistrationPage accountRegistrationPage1 = new AccountRegistrationPage(driver);
        accountRegistrationPage1.accountRegistrationMethod("Testq","Testa",emailAddress,countryCodeNumber,
                ""+System.currentTimeMillis());
        /**
         * check if user exist pop up appears and verify the same
         */
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@name='UserExistLabel']")));
        WebElement userExistWindow = driver.findElement(By.xpath("//label[@name='UserExistLabel']"));
        userExistWindow.isDisplayed();
        if(userExistWindow.isDisplayed()==true){
            System.out.println("'It looks like you've already got an account associated with this email/phone. Log in instead or " +
                    " reset your password if you've forgotten it.' pop up window is displayed ");
        }
        //taking screenshot
        new BasePage(driver).reportScreenshot("User exist window is displayed");
        tearDown();
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
    @Test(description = "Check Terms and Conditions link redirection")
    @Description("Click on the Terms and Conditions link should redirect user to the Client Agreement page - each regulative " +
            "separately")
    @Parameters({"regulation", "tag"})
    public void checkTermsAndConditionsLinkRedirection(String regulation, String tag) throws IOException, InterruptedException {

        driver.get("https://www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-gushing-at-fortrade/" + tag + "");
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,300)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='" + regulation + "Class" +
                "']//a[text()=' Terms and Conditions']")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='" + regulation + "Class']//" +
                "a[text()=' Terms and Conditions']")));
        WebElement termsAndConditionsLink = driver.findElement(By.xpath("//div[@class='" + regulation + "Class']" +
                "//a[text()=' Terms and Conditions']"));
        accountRegistrationPage.clickElement(termsAndConditionsLink, "terms and conditions link");
        newWindowIsOpen();
        String termsAndConditionUrl = driver.getCurrentUrl();
        // if regulation is cysec
        if (termsAndConditionUrl.contains("CySEC/Client_Agreement.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - client agreement page");
            Assert.assertEquals(termsAndConditionUrl, "https://www.fortrade.com/wp-content/uploads/legal/CySEC/Client_" +
                    "Agreement.pdf");
            System.out.println("The user is redirected to " + termsAndConditionUrl + " page");
        }
        //if regulation is FCA
        else if (termsAndConditionUrl.contains("Fortrade_Terms_and_Conditions.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - client agreement page");
            Assert.assertEquals(termsAndConditionUrl, "https://www.fortrade.com/wp-content/uploads/legal/Fortrade_Terms" +
                    "_and_Conditions.pdf");
            System.out.println("The user is redirected to " + termsAndConditionUrl + " page");
        }
        //if regulation is iiroc
        else if (termsAndConditionUrl.contains("IIROC/Client_Agreement.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - client agreement page");
            Assert.assertEquals(termsAndConditionUrl, "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Client" +
                    "_Agreement.pdf");
            System.out.println("The user is redirected to " + termsAndConditionUrl + " page");
        }
        //if regulation is FSC
        else {
            new BasePage(driver).reportScreenshot("Screenshot - client agreement page");
            Assert.assertEquals(termsAndConditionUrl, "https://www.fortrade.com/fortrade-mauritius-client-agreement/");
            System.out.println("The user is redirected to " + termsAndConditionUrl + " page");
        }
    }

    @Test(description = "Check privacy policy link redirection")
    @Description("Click on the privacy link should redirect user to the privacy policy page - for each regulation separately")
    @Parameters({"regulation", "tag"})
    public void checkPrivacyPolicyLinkRedirection(String regulation, String tag) throws IOException, InterruptedException {

        driver.get("https://www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-gushing-at-fortrade/" + tag + "");
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,300)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='" + regulation + "Class'" +
                "]//a[text()='Privacy Policy']")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='" + regulation + "Class']//a" +
                "[text()='Privacy Policy']")));
        WebElement privacyPolicyLink = driver.findElement(By.xpath("//div[@class='" + regulation + "Class']//a[te" +
                "xt()='Privacy Policy']"));
        accountRegistrationPage.clickElement(privacyPolicyLink, "privacy policy link");
        newWindowIsOpen();
        String currentUrl = driver.getCurrentUrl();
        // if regulation is cysec
        if (currentUrl.contains("CySEC/Privacy_Policy.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/CySEC/Privacy_Policy.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //if regulation is FCA
        else if (currentUrl.contains("Fortrade_Privacy_Policy.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/Fortrade_Privacy_Policy.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //if regulation is iiroc
        else if (currentUrl.contains("IIROC/Privacy_Policy.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Privacy_Policy.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //if regulation is FSC
        else {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/fortrade-ma-privacy-policy/");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
    }

    @Test(description = "Check how to unsubscribe link redirection")
    @Description("Click on click here link should redirect user to the how to unsubscribe page")
    public void checkHowToUnsubscribeLinkRedirection() throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,300)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@class='checkboxItem " +
                "MarketingMaterials']//a[text()='click here']")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class='checkboxItem " +
                "MarketingMaterials']//a[text()='click here']")));
        accountRegistrationPage.clickElement(accountRegistrationPage.unsubscribeLink,"click here link");
        newWindowIsOpen();
        new BasePage(driver).reportScreenshot("Screenshot - how to unsubscribe page");
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.fortrade.com/wp-content/uploads/legal/How_to_guides/How_to_unsubscribe.pdf");
        System.out.println("User is redirected to " + URL + " page");
    }

    @Test(description = "Check risk warning link redirection")
    @Description("Click on the risk warning link should redirect user to risk warning page - for each regulation separately")
    @Parameters({"regulation", "tag"})
    public void checkRiskWarningLinkRedirection(String regulation, String tag) throws IOException {

        driver.get("https://www.fortrade.com/minilps/en/opportunities-to-trade-on-oil-are-gushing-at-fortrade/" + tag + "");

        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='" + regulation +
                "Class']//a[contains(text(),'Risk warning')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='" + regulation +
                "Class']//a[contains(text(),'Risk warning')]")));
        WebElement riskWarningLink = driver.findElement(By.xpath("//div[@class='" + regulation + "Class']" +
                "//a[contains(text(),'Risk warning')]"));
        accountRegistrationPage.clickElement(riskWarningLink, "Risk Warning link");
        newWindowIsOpen();
        String currentUrl = driver.getCurrentUrl();
        // if regulation is cysec
        if (currentUrl.contains("CySEC")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/CySEC/Risk_Disclosure.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //regulation is FCA
        else if (currentUrl.contains("Fortrade_Risk_Disclosure.pdf")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/Fortrade_Risk_Disclosure.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //regulation is iiroc
        else if (currentUrl.contains("IIROC")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Relationship_" +
                    "Disclosure.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //regulation is Asic
        else if (currentUrl.contains("ASIC")) {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/ASIC/Fort_Securities_" +
                    "AU_Product_Disclosure_Statement-ASIC.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
        //regulation is FSC
        else {
            new BasePage(driver).reportScreenshot("Screenshot - privacy policy page");
            Assert.assertEquals(currentUrl, "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Risk_" +
                    "Disclosure.pdf");
            System.out.println("The user is redirected to " + currentUrl + " page");
        }
    }

    @Test(description = "Check FRN: 609970 link redirection")
    @Description("Click on FRN: 609970 link should redirect user to Financial Conduct Authority (FCA) page")
    @Parameters({"regulation"})
    public void checkFCALinkRedirection(String regulation) throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='frcItem " + regulation + "Notes']" +
                "//..//a[contains(text(),'FRN: 609970')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='frcItem " + regulation + "Notes']" +
                "//..//a[contains(text(),'FRN: 609970')]")));
        WebElement FRN609970 = driver.findElement(By.xpath("//div[@class='frcItem " + regulation + "Notes']//..//a" +
                "[contains(text(),'FRN: 609970')]"));
        accountRegistrationPage.clickElement(FRN609970, " FRN: 609970 link");
        newWindowIsOpen();
        wdWait.until(titleIs("Fortrade Limited"));
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://register.fca.org.uk/s/firm?id=001b000000NMdUwAAL");
        System.out.println("The user is redirected to " + URL + " page");
        new BasePage(driver).reportScreenshot("Screenshot - Financial Conduct Authority FCA page ");
    }

    @Test(description = "Check CIF 385/20. link redirection")
    @Description("Click on CIF 385/20 link should redirect user to Cyprus Securities and Exchange Commission | " +
            "Fortrade Cyprus Ltd page")
    @Parameters({"regulation"})
    public void checkCysecLinkRedirection(String regulation) throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='frcItem "
                + regulation + "Notes']//..//a[contains(text(),'CIF license number 385/20')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='frcItem "
                + regulation + "Notes']//..//a[contains(text(),'CIF license number 385/20')]")));
        WebElement CIF38520 = driver.findElement(By.xpath("//div[@class='frcItem " + regulation
                + "Notes']//..//a[contains(text(),'CIF license number 385/20')]"));
        accountRegistrationPage.clickElement(CIF38520, "CIF license number 385/20link");
        newWindowIsOpen();
        wdWait.until(titleIs("Cyprus Securities and Exchange Commission | Fortrade Cyprus Ltd"));
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.cysec.gov.cy/en-GB/entities/investment-firms/cypriot/86639/");
        System.out.println("The user is redirected to " + URL + " page");
        new BasePage(driver).reportScreenshot("Screenshot - Cyprus Securities and Exchange Commission page ");
    }

    @Test(description = "Check GB21026472 link redirection")
    @Description("Click on GB21026472 link should redirect user to page Financial Services Commission, Mauritius (FSC) page")
    @Parameters({"regulation"})
    public void checkFSCLinkRedirection(String regulation) throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        WebElement GB21026472 = driver.findElement(By.xpath("//div[@class='frcItem " + regulation +
                "Notes']//..//a[contains(text(),'GB21026472')]"));
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='frcItem " +
                regulation + "Notes']//..//a[contains(text(),'GB21026472')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='frcItem " +
                regulation + "Notes']//..//a[contains(text(),'GB21026472')]")));
        accountRegistrationPage.clickElement(GB21026472, " GB21026472 link");
        newWindowIsOpen();
        wdWait.until(titleIs("Register of Licensees - Details - Financial Services Commission - Mauritius"));
        new BasePage(driver).reportScreenshot("Screenshot - Financial Services Commission - Mauritius page");
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.fscmauritius.org/en/supervision/register-of-licensees/register-" +
                "of-licensees-details?licence_no=GB21026472&key=&cat=_GB&code=");
        System.out.println("The user is redirected to " + URL + " page");
    }

    @Test(description = "Check CRN: BC1148613 link redirection")
    @Description("Click on CRN: BC1148613 link should redirect user to Investment Industry Regulatory Organization " +
            "of Canada (IIROC) page")
    @Parameters({"regulation"})
    public void checkIirocLinkRedirection(String regulation) throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='frcItem " +
                regulation + "Notes']//..//a[contains(text(),'CRN: BC1148613')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='frcItem " + regulation +
                "Notes']//..//a[contains(text(),'CRN: BC1148613')]")));
        WebElement FRN609970 = driver.findElement(By.xpath("//div[@class='frcItem " + regulation + "Notes']//.." +
                "//a[contains(text(),'CRN: BC1148613')]"));
        accountRegistrationPage.clickElement(FRN609970, " CRN: BC1148613 link");
        newWindowIsOpen();
        wdWait.until(titleIs("Fortrade Canada Limited | IIROC"));
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.iiroc.ca/investors/choosing-investment-advisor/dealers-we-regulate" +
                "/fortrade-canada-limited");
        System.out.println("The user is redirected to " + URL + " page");
        new BasePage(driver).reportScreenshot("Screenshot - Investment Industry Regulatory Organization of Canada " +
                "(IIROC) page ");
    }
    @Test(description = "Check ABN: 33 614 683 831 | AFSL: 493520 link redirection")
    @Description("Click on ABN: 33 614 683 831 | AFSL: 493520 link should redirect user to  Australian Securities and Investmen" +
            "ts Commission (ASIC) page")
    public void checkAsicLinkRedirection() throws IOException, InterruptedException {
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        WebDriverWait wdWait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
        wdWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='frcItem asicNotes']//" +
                "..//a[contains(text(),'ABN: 33 614 683 831 | AFSL: 493520')]")));
        wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='frcItem asicNotes']//..//" +
                "a[contains(text(),'ABN: 33 614 683 831 | AFSL: 493520')]")));
        WebElement FRN609970 = driver.findElement(By.xpath("//div[@class='frcItem asicNotes']" +
                "//..//a[contains(text(),'ABN: 33 614 683 831 | AFSL: 493520')]"));
        accountRegistrationPage.clickElement(FRN609970, "ABN: 33 614 683 831 | AFSL: 493520 link");
        newWindowIsOpen();
        wdWait.until(titleIs("View Details - Organisations and Business Names"));
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, URL);
        System.out.println("The user is redirected to " + URL + " page");
        new BasePage(driver).reportScreenshot("Screenshot - Australian Securities and Investments Commission " +
                "(ASIC) page ");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}