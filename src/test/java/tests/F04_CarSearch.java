package tests;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.JsonReader;
import pages.Homepage;

public class F04_CarSearch extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: Nhập chính xác trường thông tin
  @Test
  public void TC01_ValidInput() {
    inputs = getTestData("TC01");
    page.performCarSearch(inputs);
  }

  // TC02: Nhập sai địa điểm đón
  @Test
  public void TC02_IncorrectLocation1() {
    inputs = getTestData("TC02");
    page.performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC03: Bỏ trống địa điểm đón
  @Test
  public void TC03_IncorrectLocation2() {
    inputs = getTestData("TC03");
    page.performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "empty";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập sai địa điểm trả khách
  @Test
  public void TC04_IncorrectLocation3() {
    inputs = getTestData("TC04");
    page.performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Bỏ trống địa điểm trả khách
  @Test
  public void TC05_IncorrectLocation4() {
    inputs = getTestData("TC05");
    page.performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("car-search-test-data.json", key);
    return new String[] {
        data.get("from").asText(),
        data.get("to").asText(),
        data.get("pickupDate").asText(),
        data.get("pickupTime").asText(),
        data.get("dropoffDate").asText(),
        data.get("dropoffTime").asText(),
        data.get("adults").asText(),
        data.get("children").asText()
    };
  }

  @BeforeMethod
  public void construct() {
    page = new Homepage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(2000);
    driver.manage().deleteAllCookies();
    driver.get(BASE_URL);
  }
}
