package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
//import seleniumsessions.customexception.FrameworkException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtil {

    private WebDriver driver;
    private Actions act;
    private JavaScriptUtil jsUtil;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        act = new Actions(driver);
        jsUtil = new JavaScriptUtil(driver);
    }

    //We are not keeping below method static  reason
    //If we do then these methods will be stored in Common memory allocation and diver will be stored in CMS as well
    //So in that case we won't be able to do parallel execution
    @Step("...Getting Element for locator: {0}")
    public WebElement getElement(By locator) {
        WebElement element = driver.findElement(locator);
        if (Boolean.parseBoolean(DriverFactory.highlight)){
            jsUtil.flash(element);

        }

        return element;
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    @Step("...Entering value: {1} in locator: {0}")
    public void doSendKeys(By locator, String value) {
        if (value == null) {
            System.out.println("value can not be null while using sendKeys method");
            // throw new FrameworkException("VALUECANNOTBENULL");
        }
        getElement(locator).sendKeys(value);
    }

    public String doElementGetText(By locator) {
        String eleTxt = getElement(locator).getText();
        System.out.println("Element text is=====>" + eleTxt);
        return eleTxt;
    }

    public WebElement getElementByLinkText(String linkText) {
        return driver.findElement(By.linkText(linkText));
    }

    public boolean checkElementIsDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }

    public String getElementAttribute(By locator, String attrName) {
        return getElement(locator).getAttribute(attrName);
    }

    public boolean checkElementIsDisabled(By locator) {
        String disableValue = getElement(locator).getAttribute("disabled");
        if (disableValue.equals("true")) {
            return true;
        }
        return false;
    }

    public int getElementsCount(By locator) {
        return getElements(locator).size();
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public List<String> geElementsTextList(By locator) {

        List<WebElement> eleList = getElements(locator);
        List<String> eleTextList = new ArrayList<String>();

        for (WebElement e : eleList) {
            String text = e.getText();
            if (!text.isEmpty()) {
                eleTextList.add(text);

            }
        }
        return eleTextList;
    }

    /**
     * Generic method to click on any link from many links based on the text of the given links
     *
     * @param locator Accepts locator of type By
     * @param text    Accepts the text of type String
     */
    public void clickOnElement(By locator, String text) {

        List<WebElement> linksList = getElements(locator);

        System.out.println("Total language links" + linksList.size());

        for (WebElement e : linksList) {

            String textOfLanguage = e.getText();
            System.out.println(textOfLanguage);

            if (textOfLanguage.equals(text)) {
                e.click();
                break;
            }
        }
    }

    /**
     * Generic method for doing the search and performing click operation on any link/text/Suggestion from given suggestion
     *
     * @param searchLocator           the locator of text field where we need to search and need to enter the text for searching
     * @param searchSuggestionLocator the locator of the search suggestion(es) which are appearing and which we need to store
     * @param searchKey               Key/Text which we want to search for.
     * @param suggestionName          Suggestion/key/text on which want to click and open
     * @throws InterruptedException
     */
    public void doSearch(By searchLocator, By searchSuggestionLocator, String searchKey, String suggestionName) throws InterruptedException {

        doSendKeys(searchLocator, searchKey);

        //We need to wait for some-time as suggestion will take sometime to appear on the screen from the database. Always keep wait after entering the keyword in any search box

        Thread.sleep(5000);

        List<WebElement> suggestionsList = getElements(searchSuggestionLocator);

        System.out.println(suggestionsList.size());

        for (WebElement e : suggestionsList) {

            String text = e.getText();
            System.out.println(text);

            if (text.contains(suggestionName)) {
                e.click();
                break;
            }

        }
    }

    //***********************  DropDown Utils  ***********************//

    public void doActionsClick(By locator) {


        act.click(getElement(locator)).build().perform();

    }

    public void doActionsSendKeys(By locator, String text) {


        act.sendKeys(getElement(locator), text).build().perform();
    }

    public void doSelectDropDownByIndex(By locator, int index) {

        if (index < 0) {
            System.out.println("Please pass (+ve) index");
            return;
        }
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);

    }

    public void doSelectDropDownByVisibleText(By locator, String visibleText) {

        if (visibleText == null) {
            System.out.println("Please pass the right visible text and it can not be null");
            return;
        }

        Select select = new Select(getElement(locator));
        select.selectByVisibleText(visibleText);

    }

    public void doSelectDropDownByValue(By locator, String value) {

        if (value == null) {
            System.out.println("Please pass the right value text and it can not be null");
            return;
        }

        Select select = new Select(getElement(locator));
        select.selectByVisibleText(value);

    }


    //Give the By locator of the dropdown and get the all the dropdown values

    public List<String> getDropDownTextList(By locator) {

        Select select = new Select(getElement(locator));

        List<WebElement> optionsList = select.getOptions();

        List<String> optionsTextList = new ArrayList<>();

        for (WebElement e : optionsList) {

            String optionText = e.getText();
            optionsTextList.add(optionText);
        }

        return optionsTextList;

    }

    public int getDropDownOptionsCount(By locator) {
        Select select = new Select(getElement(locator));
        return select.getOptions().size();
    }

    public void doSelectDropDownValue(By locator, String dropDownValue) {

        // Don't use 3 methods: value ,index, visibleText

        Select select = new Select(getElement(locator));

        List<WebElement> optionsList = select.getOptions();

        for (WebElement e : optionsList) {
            String optionText = e.getText();
            if (optionText.equals(dropDownValue)) {
                e.click();
                break;

            }
        }

    }

    public void doSelectDropDownValueUsingLocator(By locator, String dropDownValue) {

        List<WebElement> optinsList = getElements(locator);


        for (WebElement e : optinsList) {

            String optionText = e.getText();
            if (optionText.equals(dropDownValue)) {
                e.click();
                break;
            }
        }


    }

    //***********************  Action Utils  ***********************//

    public void selectRightClickOption(By contextMenuLocator, String optionValue) {

        Actions action = new Actions(driver);

        action.contextClick(getElement(contextMenuLocator)).perform();

        By optionLocator = By.xpath("//*[text()='" + optionValue + "']");

        doClick(optionLocator);

    }

    /**
     * This method will handle the menu up to 2 level
     *
     * @param levelOneLocator
     * @param levelTwoLocator
     */
    public void multiLevelMenuHandling(By levelOneLocator, By levelTwoLocator) {

        act.moveToElement(getElement(levelOneLocator)).build().perform();
        doClick(levelTwoLocator);

    }

    /**
     * This method will handle the menu up to 3 level
     *
     * @param level1Locator
     * @param level2
     * @param level3
     * @throws InterruptedException
     */
    public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {

        act.moveToElement(getElement(level1Locator)).build().perform();
        Thread.sleep(1500);

        act.moveToElement(getElementByLinkText(level2)).build().perform();
        Thread.sleep(1500);

        getElementByLinkText(level3).click();
    }

    /**
     * This method will handle the menu up to 4 level
     *
     * @param level1Locator
     * @param level2
     * @param level3
     * @param level4
     * @throws InterruptedException
     */
    public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4) throws InterruptedException {

        act.moveToElement(getElement(level1Locator)).build().perform();
        Thread.sleep(1500);

        act.moveToElement(getElementByLinkText(level2)).build().perform();
        Thread.sleep(1500);

        act.moveToElement(getElementByLinkText(level3)).build().perform();
        Thread.sleep(1500);

        getElementByLinkText(level4).click();
    }
//**************WaitUtils*************************//

    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementPresence(By locator, int timeOut, int pollingTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed
     * but also has a height and width that is greater than 0.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement waitForElementVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("...Waiting for element is visible for locator: {0} with timeout: {1}")
    public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public String waitForTitleContains(String titleFraction, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
                return driver.getTitle();
            } else {

                System.out.println(titleFraction + "title value is not present");
                return null;

            }
        } catch (Exception e) {
            System.out.println(titleFraction + "title value is not present");
            return null;
        }

    }

    @Step("...Waiting for the title and capture the title...")
    public String waitForTitleIs(String titleValue, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            if (wait.until(ExpectedConditions.titleIs(titleValue))) {
                return driver.getTitle();
            } else {

                System.out.println(titleValue + "title value is not present");
                return null;

            }
        } catch (Exception e) {
            System.out.println(titleValue + "title value is not present");
            return null;
        }

    }

    @Step("...Waiting for the PageURL and fetching it...Url Fraction: {0}")
    public String waitForUrlContains(String UrlFraction, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            if (wait.until(ExpectedConditions.urlContains(UrlFraction))) {
                return driver.getCurrentUrl();
            } else {

                System.out.println(UrlFraction + "url value is not present");
                return null;

            }
        } catch (Exception e) {
            System.out.println(UrlFraction + "url value is not present");
            return null;
        }

    }

    public String waitForUrlTobe(String UrlValue, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            if (wait.until(ExpectedConditions.urlToBe(UrlValue))) {
                return driver.getCurrentUrl();
            } else {

                System.out.println(UrlValue + "url value is not present");
                return null;

            }
        } catch (Exception e) {
            System.out.println(UrlValue + "url value is not present");
            return null;
        }

    }

    public Alert waitForJSAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public boolean waitForNumberOfBrowserWindows(int timeOut, int numberOfWindowsToBe) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
    }


    public void waitForFrameByLocator(By frameLocator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameByIndex(By frameIndex, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameByNameOrId(By frameNameOrId, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
    }

    public void waitForFrameElement(WebElement frameElement, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public void clickElementWhenReady(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

//******************* Fluent Utils  **********************//


    public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("----time out is done...element is not found..." + locator);

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

    public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("----time out is done...element is not found..." + locator);

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoAlertPresentException.class)
                .withMessage("----time out is done...Js alert is not found...");
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForFrameWithFluentWait(String frameNameOrID, int timeOut, int pollingTime) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchFrameException.class)
                .withMessage("----time out is done...Frame is not found...with name or id: " + frameNameOrID);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
    }

    public void waitForElementAndEnterValue(By locator, int timeOut, int pollingTime, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .withMessage("----time out is done...element is not found..." + locator)
                .until(ExpectedConditions.presenceOfElementLocated(locator))
                .sendKeys(value);
    }

    public void waitForElementAndClick(By locator, int timeOut, int pollingTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .withMessage("----time out is done...element is not found..." + locator)
                .until(ExpectedConditions.presenceOfElementLocated(locator))
                .click();
    }

    //**************Custom Waits**************//

    public WebElement retryingElement(By locator, int timeOut) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {//10
            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt " + attempts);
                break;

            } catch (NoSuchElementException e) {
                System.out.println("element is not found...." + locator + " in attempt " + attempts);
                try {
                    Thread.sleep(500);//default polling time = 500 ms
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            attempts++;

        }

        if (element == null) {
            System.out.println("element is not found....tried for " + timeOut + " secs " + " with the interval of " + 500 + " milli secs");
        }

        return element;

    }


    public WebElement retryingElement(By locator, int timeOut, long pollingTime) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {//10
            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt " + attempts);
                break;

            } catch (NoSuchElementException e) {
                System.out.println("element is not found...." + locator + " in attempt " + attempts);
                try {
                    Thread.sleep(pollingTime);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            attempts++;

        }

        if (element == null) {
            System.out.println("element is not found....tried for " + timeOut + " secs " + " with the interval of " + pollingTime + " mill secs");
        }

        return element;

    }


    public boolean isPageLoaded(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
        return Boolean.parseBoolean(flag);
    }

}
