package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    // 1. private By locators - page locators
    private By emailId = By.id("input-email");
    private By password = By.id("input-passwordddd");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");


    // 2. public Page Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    // 3. public Page actions/methods
    @Step("...Getting login page title...")
    public String getLoginPageTitle() {

        //String title = driver.getTitle();
        String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
        System.out.println("LoginPage title is: " + title);
        return title;

    }

    @Step("...Getting login page url...")
    public String getLoginPageURL() {
        //String url = driver.getCurrentUrl();
        String url = eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_TIME_OUT);
        System.out.println("LoginPage url is: " + url);
        return url;
    }

    @Step("...is forgot password link exists or not...")
    public boolean isForgotPwsLinkExist() {
        return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
        //return driver.findElement(forgotPwdLink).isDisplayed();
    }

    @Step("...login to app with username: {0} and password: {1} ...")
    public AccountsPage doLogin(String username, String pwd) {

        System.out.println("App creds are: " + username + ":" + pwd);
        //driver.findElement(emailId).sendKeys(username);
        eleUtil.waitForElementVisible(emailId, 10).sendKeys(username);
        //driver.findElement(password).sendKeys(pwd);
        eleUtil.doSendKeys(password, pwd);
        //driver.findElement(loginBtn).click();
        eleUtil.doClick(loginBtn);
        // return driver.getTitle();
        // return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
        return new AccountsPage(driver);

    }

    @Step("...Navigating to register page...")
    public RegisterPage navigateToRegisterPage() {
        eleUtil.waitForElementVisible(registerLink, AppConstants.SHORT_TIME_OUT).click();
        return new RegisterPage(driver);
    }
}
