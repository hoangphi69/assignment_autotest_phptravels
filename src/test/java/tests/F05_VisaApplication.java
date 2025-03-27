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

public class F05_VisaApplication extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: Nhập thông tin hợp lệ
  @Test
  public void TC01_ValidInput() {
    inputs = getTestData("TC01");
    page.performVisaApplication(inputs);

    // Kiểm tra điều hướng trang
    delay(1000);
    String actual = driver.getTitle();
    String expected = "Visa";
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Nhập thông tin không hợp lệ (ngày không hợp lệ)
  @Test
  public void TC02_InvalidDate() {
    inputs = getTestData("TC02");
    page.performVisaApplication(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "invalid";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: %s".formatted(actual));
  }

  // TC03: Nhập thông tin thiếu (không nhập ngày)
  @Test
  public void TC03_MissingDate() {
    inputs = getTestData("TC03");
    page.performVisaApplication(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "empty";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: %s".formatted(actual));
  }

  // TC04: Nhập thông tin thiếu (không nhập quốc gia đến)
  @Test
  public void TC04_MissingDestination() {
    inputs = getTestData("TC04");
    page.performVisaApplication(inputs);

    // Kiểm tra nội dung thông báo
    WebElement field = driver.findElement(page.VISA_TO_HIDDEN);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", field);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC05: Nhập thông tin thiếu (không nhập quốc gia đi)
  @Test
  public void TC05_MissingOrigin() {
    inputs = getTestData("TC05");
    page.performVisaApplication(inputs);

    // Kiểm tra nội dung thông báo
    WebElement field = driver.findElement(page.VISA_FROM_HIDDEN);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", field);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC06: Nhập thông tin hợp lệ nhưng ngày đã qua
  @Test
  public void TC06_PastDate() {
    inputs = getTestData("TC06");
    page.performVisaApplication(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "past";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("visa-application-test-data.json", key);
    return new String[] {
        data.get("from").asText(),
        data.get("to").asText(),
        data.get("date").asText(),
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
