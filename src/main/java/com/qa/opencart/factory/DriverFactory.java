package com.qa.opencart.factory;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 */
public class DriverFactory {

    public static String highlight;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    WebDriver driver;
    Properties prop;
    OptionsManager optionsManager;
    //set - to initialize thread local for specific resources
    //get - to fatch the local copy of the thread

    public static WebDriver getDriver() {
        return tlDriver.get();

    }

    /**
     * This is used to initiliaze the driver
     *
     * @param browserName
     * @return
     */
    public WebDriver initDriver(Properties prop) {
        String browserName = prop.getProperty("browser");
        System.out.println("browser name is : " + browserName);

        optionsManager = new OptionsManager(prop);
        highlight = prop.getProperty("highlight");

        switch (browserName.toLowerCase()) {
            case "chrome":
                // driver = new ChromeDriver(optionsManager.getChromeOptions());
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                break;
            case "firefox":
                // driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
                tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
                break;
            case "edge":
                // driver = new EdgeDriver(optionsManager.getEdgeOptions());
                tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                break;

            default:
                System.out.println("Plz pass the right browser..." + browserName);
                break;
        }

        //driver.manage().window().maximize();
        getDriver().manage().window().maximize();
        //driver.manage().deleteAllCookies();
        getDriver().manage().deleteAllCookies();
        //driver.get(prop.getProperty("url"));
        getDriver().get(prop.getProperty("url"));
        return getDriver();
    }

    /**
     * This method is used to init the properties
     *
     * @return
     */
    public Properties initProp() {

        //mvn clean install -Denv='qa'

        FileInputStream ip = null;
        prop = new Properties();

        String envName = System.getProperty("env");
        System.out.println("env name is : " + envName);
        try {
            if (envName == null) {
                System.out.println("no env is given...hence running on QA env..by default");
                ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");

            } else {
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream(".\\src\\test\\resources\\config\\dev.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream(".\\src\\test\\resources\\config\\stage.config.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream(".\\src\\test\\resources\\config\\uat.config.properties");
                        break;
                    case "prod":
                        ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");
                        break;
                    default:
                        System.out.println("Please pass the right env..." + envName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        try {
            prop.load(ip);

        } catch (IOException e) {
            e.printStackTrace();

        }
       /* try {
            FileInputStream ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");//make the connection with the config file
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }*/

        return prop;
    }

    /**
     * Take Screenshot
     */
    public static String getScreenshot(String methodName) {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("use.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(path); //Destination file is pointing to path
        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

}
