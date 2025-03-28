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

public class F03_TourSearch extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: nhập đúng trường thông tin
  @Test
  public void TC01_Correct() {
    inputs = getInputs("TC01");
    String expected = getOutput("TC01").get("title").asText();
    page.performTourSearch(inputs);

    delay(1000);
    String actual = driver.getTitle();
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Nhập sai địa điểm
  @Test
  public void TC02_IncorrectLocation() {
    inputs = getInputs("TC02");
    String expected = getOutput("TC02").get("message").asText();
    page.performTourSearch(inputs);

    WebElement location = driver.findElement(page.TOUR_CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Không nhập địa điểm
  @Test
  public void TC03_LocationBlank() {
    inputs = getInputs("TC03");
    String expected = getOutput("TC03").get("message").asText();
    page.performTourSearch(inputs);

    WebElement location = driver.findElement(page.TOUR_CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC04: Nhập thời gian quá khứ
  @Test
  public void TC04_DateInThePast() {
    inputs = getInputs("TC04");
    String expected = getOutput("TC04").get("message").asText();
    page.performTourSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Nhập số lượng hành khách là '0'
  @Test
  public void TC05_ZeroTraveller() {
    inputs = getInputs("TC05");
    String expected = getOutput("TC05").get("message").asText();
    page.performTourSearch(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("tour-search-test-data.json", key).get("input");
    return new String[] {
        data.get("city").asText(),
        data.get("date").asText(),
        data.get("adults").asText(),
        data.get("children").asText()
    };
  }

  public JsonNode getOutput(String key) {
    return JsonReader.getTestData("tour-search-test-data.json", key).get("output");
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
