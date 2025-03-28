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
    inputs = getInputs("TC01");
    String expected = getOutput("TC01").get("title").asText();
    page.performCarSearch(inputs);

    delay(1000);
    String actual = driver.getTitle();
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Nhập sai địa điểm đón
  @Test
  public void TC02_IncorrectLocation1() {
    inputs = getInputs("TC02");
    String expected = getOutput("TC02").get("message").asText();
    page.performCarSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC03: Bỏ trống địa điểm đón
  @Test
  public void TC03_IncorrectLocation2() {
    inputs = getInputs("TC03");
    String expected = getOutput("TC03").get("message").asText();
    page.performCarSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập sai địa điểm trả khách
  @Test
  public void TC04_IncorrectLocation3() {
    inputs = getInputs("TC04");
    String expected = getOutput("TC04").get("message").asText();
    page.performCarSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Bỏ trống địa điểm trả khách
  @Test
  public void TC05_IncorrectLocation4() {
    inputs = getInputs("TC05");
    String expected = getOutput("TC05").get("message").asText();
    page.performCarSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("car-search-test-data.json", key).get("input");
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

  public JsonNode getOutput(String key) {
    return JsonReader.getTestData("car-search-test-data.json", key).get("output");
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
