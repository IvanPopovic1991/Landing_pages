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
    public WebElement startNowBtn;
    @FindBy (xpath = "//div[@class='welcomePopup']//..//div[@id='startTradingButton']")
    public WebElement continueBtn;
    @FindBy (xpath = "//label[@class='checkboxItem MarketingMaterials']//a[text()='click here']")
    public WebElement unsubscribeLink;
    @FindBy (xpath = "//label[@class='checkboxItem MarketingMaterials']//a[text()='haga clic aquí']")
    public WebElement hagaClicAquíLink;
    @FindBy (xpath = "//label[@class='checkboxItem MarketingMaterials']//a[text()='kliknite ovde']")
    public WebElement klikniteOvdelink;
    @FindBy(xpath = "//p//a[contains(text(),'Already have an account? Login')]")
    public WebElement loginLink;
    @FindBy(xpath = "//p//a[contains(text(),'Već imate nalog? Prijavite se')]")
    public WebElement prijaviteSeLink;
    @FindBy (xpath = "//div[@id='formWrapper' and @class='col-md-5 formCover formCoverFixed']//button[@onclick='" +
            "myCloseFormFunction()']")
    public WebElement closeBtn;


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
    public void clickCloseBtn(){
        clickElement(closeBtn,"close button in footer");
    }
}
