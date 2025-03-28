package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("EPIC-100: Design of the login page for open cart")
@Story("US-200: Implement login page futures for open cart app")
public class LoginPageTest extends BaseTest {

    @Description("Login page title test...")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1)
    public void loginPageTitleTest() {
        String actTitle = loginpage.getLoginPageTitle();
        Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Description("Login page url test...")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginPageURLTest() {
        String actURL = loginpage.getLoginPageURL();
        Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
    }

    @Description("Check forgot password link exists on login page...")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void isForgotPwdLinkExistTest() {
        Assert.assertTrue(loginpage.isForgotPwsLinkExist());
    }

    @Description("Check user is able to login to open cart with valid credentials...")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 4)
    public void loginTest() {
        accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertEquals(accPage.isLogoutLinkExist(), true);
    }

    @Description("...checking fail test...")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 5)
    public void failAssertTest() {
      Assert.assertEquals(true,false);
    }


}
