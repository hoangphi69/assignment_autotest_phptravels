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

public class F01_FlightSearch extends BaseTest {
  private String[] inputs;
  private String output;
  private Homepage page;

  // TC01: Nhập thông tin hợp lệ
  @Test
  public void TC01_ValidSearch() {
    inputs = getInputs("TC01");
    output = getOutput("TC01").get("title").asText();
    page = new Homepage(driver);
    page.performFlightSearch(inputs);

    delay(1000);

    // Kiểm tra tiêu đề trang được điều hướng
    String actual = driver.getTitle();
    String expected = output;
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Bỏ trống điểm xuất phát
  @Test
  public void TC02_SearchWithoutDeparture() {
    inputs = getInputs("TC02");
    output = getOutput("TC02").get("message").asText();
    page = new Homepage(driver);
    page.performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = output;
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC03: Bỏ trống điểm đến
  @Test
  public void TC03_SearchWithoutDestination() {
    inputs = getInputs("TC03");
    output = getOutput("TC03").get("message").asText();
    page = new Homepage(driver);
    page.performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = output;
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC04: Ngày trong quá khứ
  @Test
  public void TC04_SearchWithPastDate() {
    inputs = getInputs("TC04");
    output = getOutput("TC04").get("message").asText();
    page = new Homepage(driver);
    page.performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = output;
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC05: Số lượng hành khách không hợp lệ
  @Test
  public void TC05_SearchWithInvalidPassengerNumber() {
    inputs = getInputs("TC05");
    output = getOutput("TC05").get("message").asText();
    page = new Homepage(driver);
    page.performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = output;
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("flight-search-test-data.json", key).get("input");
    return new String[] {
        data.get("way").asText(),
        data.get("type").asText(),
        data.get("from").asText(),
        data.get("to").asText(),
        data.get("date").asText(),
        data.get("adults").asText(),
        data.get("children").asText(),
        data.get("infants").asText()
    };
  }

  public JsonNode getOutput(String key) {
    return JsonReader.getTestData("flight-search-test-data.json", key).get("output");
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
