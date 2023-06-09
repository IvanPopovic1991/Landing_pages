package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[name='FirstName']")
    WebElement firstName;
    @FindBy(css = "[name='LastName']")
    WebElement lastName;
    @FindBy(css = "[name='Email']")
    WebElement email;
    @FindBy(css = "[name='PhoneCountryCode']")
    WebElement countryCode;
    @FindBy(css = "[name='Phone']")
    WebElement phoneNumber;
    @FindBy(css = "[name='Send']")
    public WebElement startNowBtn;
    @FindBy(xpath = "//div[@id='startTradingButton']")
    public WebElement continueBtn;

    public void enterFirstName(String firstNameValue) {
        typeText(firstName, firstNameValue, "First name field");
    }

    public void enterLastName(String lastNameValue) {
        typeText(lastName, lastNameValue, "Last name field");
    }

    public void enterEmail(String emailValue) {
        typeText(email, emailValue, "Email field");
    }

    public void enterCountryCode(String countryCodeValue) {
        typeText(countryCode, countryCodeValue, "Country code field");
    }

    public void enterPhoneNumber(String phoneNumberValue) {
        typeText(phoneNumber, phoneNumberValue, "Phone number field");
    }

    public void accountRegistrationMethod(String firstNameValue, String lastNameValue, String emailValue, String countryCodeValue
            , String phoneNumberValue) {
        enterFirstName(firstNameValue);
        enterLastName(lastNameValue);
        enterEmail(emailValue);
        enterCountryCode(countryCodeValue);
        enterPhoneNumber(phoneNumberValue);
        clickElement(startNowBtn, "Start now button");
    }
}
