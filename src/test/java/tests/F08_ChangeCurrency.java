package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.Helpers;
import base.JsonReader;
import pages.Homepage;

public class F08_ChangeCurrency extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: Đổi đơn vị tiền tệ sang EUR
  @Test
  public void TC01_ChangeToEUR() {
    inputs = getTestData("TC01");
    testChangeCurrency(inputs[0]);
  }

  // TC02: Đổi đơn vị tiền tệ sang GBP
  @Test
  public void TC02_ChangeToGBP() {
    inputs = getTestData("TC02");
    testChangeCurrency(inputs[0]);
  }

  // TC03: Đổi đơn vị tiền tệ sang SAR
  @Test
  public void TC03_ChangeToSAR() {
    inputs = getTestData("TC03");
    testChangeCurrency(inputs[0]);
  }

  // TC04: Đổi đơn vị tiền tệ sang USD (không đổi)
  @Test
  public void TC04_ChangeToUSD() {
    inputs = getTestData("TC04");
    testChangeCurrency(inputs[0]);
  }

  // TC05: Đổi đơn vị tiền tệ rồi refresh trang
  @Test
  public void TC05_ChangeAndRefresh() {
    inputs = getTestData("TC05");

    page.performChangeCurrency(inputs[0]);
    page.delay(1000);

    String expected = page.getExampleText();

    driver.navigate().refresh();

    String actual = page.getExampleText();

    Assert.assertEquals(expected, actual);
  }

  public void testChangeCurrency(String currency) {
    // Lấy giá trị đại diện ban đầu
    String exampleText = page.getExampleText();
    String expected = Helpers.convertCurrency(exampleText, currency);

    page.performChangeCurrency(currency);
    page.delay(1000);

    // Lấy giá trị đại diện hiện tại
    String actual = page.getExampleText();

    Assert.assertEquals(expected, actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("change-currency-test-data.json", key);
    return new String[] {
        data.get("currency").asText(),
    };
  }

  @BeforeMethod
  public void construct() {
    page = new Homepage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.get(BASE_URL);
  }
}
