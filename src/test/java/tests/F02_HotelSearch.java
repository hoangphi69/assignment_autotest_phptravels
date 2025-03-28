package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.JsonReader;
import pages.Homepage;

public class F02_HotelSearch extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: Tham số truyền vào hợp lệ
  @Test
  public void TC01_ValidHotel() {
    inputs = getInputs("TC01");
    String expected = getOutput("TC01").get("title").asText();
    page.performHotelSearch(inputs);

    delay(1000);
    String actual = driver.getTitle();
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Không nhập địa điểm
  @Test
  public void TC02_SearchWithoutLocation() {
    inputs = getInputs("TC02");
    String expected = getOutput("TC02").get("message").asText();
    page.performHotelSearch(inputs);

    WebElement location = driver.findElement(page.HOTEL_CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Nhập thời gian trả phòng sớm hơn thời gian nhận phòng
  @Test
  public void TC03_CheckinBeforeCheckout() {
    inputs = getInputs("TC03");
    String expected = getOutput("TC03").get("message").asText();
    page.performHotelSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập số lượng khách tối đa
  @Test
  public void TC04_ExceedGuestLimit() {
    inputs = getInputs("TC04");
    String expected = getOutput("TC04").get("message").asText();
    page.performHotelSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Nhập thời gian check in trước hiện tại
  @Test
  public void TC05_PastCheckinDate() {
    inputs = getInputs("TC05");
    String expected = getOutput("TC05").get("message").asText();
    page.performHotelSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("hotel-search-test-data.json", key).get("input");
    return new String[] {
        data.get("city").asText(),
        data.get("checkin").asText(),
        data.get("checkout").asText(),
        data.get("rooms").asText(),
        data.get("adults").asText(),
        data.get("children").asText()
    };
  }

  public JsonNode getOutput(String key) {
    return JsonReader.getTestData("hotel-search-test-data.json", key).get("output");
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
