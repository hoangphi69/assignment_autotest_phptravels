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
  public void testValidHotel() {
    inputs = getTestData("TC01");
    page.performHotelSearch(inputs);
  }

  // TC02: Không nhập địa điểm
  @Test
  public void testSearchWithoutLocation() {
    inputs = getTestData("TC02");
    page.performHotelSearch(inputs);

    // Kiểm tra thông báo từ input
    WebElement location = driver.findElement(page.HOTEL_CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Nhập thời gian trả phòng sớm hơn thời gian nhận phòng
  @Test
  public void testCheckinBeforeCheckout() {
    inputs = getTestData("TC03");
    page.performHotelSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC04: Nhập số lượng khách tối đa
  @Test
  public void testExceedGuestLimit() {
    inputs = getTestData("TC04");
    page.performHotelSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "travellers";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC05: Nhập thời gian check in trước hiện tại
  @Test
  public void testPastCheckinDate() {
    inputs = getTestData("TC05");
    page.performHotelSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("hotel-search-test-data.json", key);
    return new String[] {
        data.get("city").asText(),
        data.get("checkin").asText(),
        data.get("checkout").asText(),
        data.get("rooms").asText(),
        data.get("adults").asText(),
        data.get("children").asText(),
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
