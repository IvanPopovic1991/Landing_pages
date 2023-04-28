package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountRegistrationFullNamePage extends BasePage{

    public AccountRegistrationFullNamePage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(css = "[name='FullName']")
    WebElement fullName;
    @FindBy(css="[name='Email']")
    WebElement email;
    @FindBy (css = "[name='PhoneCountryCode']")
    WebElement countryCode;
    @FindBy (css = "[name='Phone']")
    WebElement phoneNumber;
    @FindBy (css = "[name='Send']")
    WebElement startNowBtn;
    @FindBy (xpath = "//div[@class='welcomePopup']//..//div[@id='startTradingButton']")
    public WebElement continueBtn;

    public void enterFullName(String fullNameValue){
        typeText(fullName,fullNameValue,"Full Name field");
    }

    public void enterEmail(String emailValue){
        typeText(email,emailValue,"Email field");
    }
    public void enterCountryCode(String countryCodeValue){
        typeText(countryCode,countryCodeValue,"Country code field");
    }
    public void enterPhoneNumber(String phoneNumberValue){
        typeText(phoneNumber,phoneNumberValue,"Phone number field");
    }
    public void accountRegistrationFullNamePageMethod(String fullNameValue,String emailValue,String countryCodeValue,String phoneNumberValue){
        enterFullName(fullNameValue);
        enterEmail(emailValue);
        enterCountryCode(countryCodeValue);
        enterPhoneNumber(phoneNumberValue);
        clickElement(startNowBtn,"Join now button");
    }
}
