package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties prop;
    protected LoginPage loginpage;
    protected AccountsPage accPage;
    protected SearchResultsPage searchResPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;
    DriverFactory df;
    protected SoftAssert softAssert;

   @BeforeTest
    public void setUp() {
       /* driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");*/

        df= new DriverFactory();
        prop=df.initProp();
       // driver=df.initDriver("Chrome");
        driver=df.initDriver(prop);
        loginpage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
