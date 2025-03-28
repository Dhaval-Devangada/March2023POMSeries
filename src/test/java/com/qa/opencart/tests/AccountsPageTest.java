package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Epic("EPIC-101: Design of the Account page for open cart")
@Story("US-201: Implement Account page futures for open cart app")
public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accPageSetup() {
        accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

    }

    @Test
    public void accPageTitleTest() {

        String actAccPageTitle = accPage.getAccPageTitle();
        Assert.assertEquals(actAccPageTitle, AppConstants.ACCOUNTS_PAGE_TITLE);

    }

    @Test
    public void logoutLinkExistsTest() {
        Assert.assertTrue(accPage.isLogoutLinkExist());

    }

    @Test
    public void accPageHeadersTest() {
        int actAccPageHeadersCount = accPage.getAccountsPageHeaderCount();
        System.out.println("Actual Acc Page Headers count= " + actAccPageHeadersCount);
        Assert.assertEquals(actAccPageHeadersCount, AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
    }

    @Test
    public void accPageHeaderTest() {
        List<String> actAccPAgeHeadersList = accPage.getAccountsPageHeader();
        Assert.assertEquals(actAccPAgeHeadersList, AppConstants.EXPECTED_ACC_HEADERS_LIST);
    }

    @DataProvider
    public Object[][] getSearchKey() {
        return new Object[][]{

                {"macbook", 3},
                {"imac", 1},
                {"samsung", 2}

        };

    }

    @Test(dataProvider = "getSearchKey")
    public void searchTest(String searchKey, int productCount) {
        searchResPage = accPage.doSearch(searchKey);
        int actResultsCount = searchResPage.getSearchResultsCount();
        Assert.assertEquals(actResultsCount, productCount);

    }
}


