package F04_CarSearch;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import utils.JsonReader;

public class CarSearchTest extends CarSearchPage {
  // TC01: Nhập chính xác trường thông tin
  @Test
  public void TC01_ValidInput() {
    String[] inputs = getTestData("TC01");
    performCarSearch(inputs);
  }

  // TC02: Nhập sai địa điểm đón
  @Test
  public void TC02_IncorrectLocation1() {
    String[] inputs = getTestData("TC02");
    performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC03: Bỏ trống địa điểm đón
  @Test
  public void TC03_IncorrectLocation2() {
    String[] inputs = getTestData("TC03");
    performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "empty";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập sai địa điểm trả khách
  @Test
  public void TC04_IncorrectLocation3() {
    String[] inputs = getTestData("TC04");
    performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Bỏ trống địa điểm trả khách
  @Test
  public void TC05_IncorrectLocation4() {
    String[] inputs = getTestData("TC05");
    performCarSearch(inputs);

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("F04_CarSearch/CarSearchData.json", key);
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

  @AfterMethod
  public void navigateBack() {
    delay(5000);
    driver.get(BASE_URL);
  }
}
