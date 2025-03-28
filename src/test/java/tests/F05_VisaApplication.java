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
    inputs = getInputs("TC01");
    String expected = getOutput("TC01").get("title").asText();
    page.performVisaApplication(inputs);

    delay(1000);
    String actual = driver.getTitle();
    Assert.assertEquals(actual, expected, "Tiêu đề trang sai");
  }

  // TC02: Nhập thông tin không hợp lệ (ngày không hợp lệ)
  @Test
  public void TC02_InvalidDate() {
    inputs = getInputs("TC02");
    String expected = getOutput("TC02").get("message").asText();
    page.performVisaApplication(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: %s".formatted(actual));
  }

  // TC03: Nhập thông tin thiếu (không nhập ngày)
  @Test
  public void TC03_MissingDate() {
    inputs = getInputs("TC03");
    String expected = getOutput("TC03").get("message").asText();
    page.performVisaApplication(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: %s".formatted(actual));
  }

  // TC04: Nhập thông tin thiếu (không nhập quốc gia đến)
  @Test
  public void TC04_MissingDestination() {
    inputs = getInputs("TC04");
    String expected = getOutput("TC04").get("message").asText();
    page.performVisaApplication(inputs);

    WebElement field = driver.findElement(page.VISA_TO_HIDDEN);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", field);
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC05: Nhập thông tin thiếu (không nhập quốc gia đi)
  @Test
  public void TC05_MissingOrigin() {
    inputs = getInputs("TC05");
    String expected = getOutput("TC05").get("message").asText();
    page.performVisaApplication(inputs);

    WebElement field = driver.findElement(page.VISA_FROM_HIDDEN);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", field);
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC06: Nhập thông tin hợp lệ nhưng ngày đã qua
  @Test
  public void TC06_PastDate() {
    inputs = getInputs("TC06");
    String expected = getOutput("TC06").get("message").asText();
    page.performVisaApplication(inputs);

    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("visa-application-test-data.json", key).get("input");
    return new String[] {
        data.get("from").asText(),
        data.get("to").asText(),
        data.get("date").asText()
    };
  }

  public JsonNode getOutput(String key) {
    return JsonReader.getTestData("visa-application-test-data.json", key).get("output");
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
