package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;


@Epic("EPIC-103: Design of the productInfo page for open cart app")
@Story("US-203: Implement product page futures for open cart app")
public class ProductInfoTest extends BaseTest {

    @BeforeClass
    public void productInfoSetUp() {
        accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

    }

    @DataProvider
    public Object[][] productTestData() {
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"macbook", "MacBook Air"},
                {"imac", "iMac"},
                {"samsung", "Samsung SyncMaster 941BW"},
                {"samsung", "Samsung Galaxy Tab 10.1"}

        };

    }

    @Test(dataProvider = "productTestData")
    public void productHeaderTest(String searchKey, String productName) {
        searchResPage = accPage.doSearch(searchKey);
        productInfoPage = searchResPage.SelectProduct(productName);
        String actualProductHeader = productInfoPage.getProductHeaderValue();
        Assert.assertEquals(actualProductHeader, productName);
    }


    @DataProvider
    public Object[][] productData() {
        return new Object[][]{
                {"macbook", "MacBook Pro", 4},
                {"macbook", "MacBook Air", 4},
                {"imac", "iMac", 3},
                {"samsung", "Samsung SyncMaster 941BW", 1},
                {"samsung", "Samsung Galaxy Tab 10.1", 7}

        };

    }


    @Test(dataProvider = "productData")
    public void productImageCountTest(String searchKey, String productName, int expectedProductImagesCount) {
        searchResPage = accPage.doSearch(searchKey);
        productInfoPage = searchResPage.SelectProduct(productName);
        int actImagesCount = productInfoPage.getProductImageCount();
        Assert.assertEquals(actImagesCount, expectedProductImagesCount);
    }

    @Test
    public void productInfoTest() {
        searchResPage = accPage.doSearch("macbook");
        productInfoPage = searchResPage.SelectProduct("MacBook Pro");
        Map<String, String> productActualData = productInfoPage.getProductData();
        System.out.println(productActualData);
        softAssert.assertEquals(productActualData.get("Brand"), "Apple");
        softAssert.assertEquals(productActualData.get("Availability"), "In Stock");
        softAssert.assertEquals(productActualData.get("productheader"), "MacBook Pro");
        softAssert.assertEquals(productActualData.get("price"), "$2,000.00");
        softAssert.assertEquals(productActualData.get("Reward Points"), "800");
        softAssert.assertAll();
    }
}
