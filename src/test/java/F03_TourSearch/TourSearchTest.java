package F03_TourSearch;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import utils.JsonReader;

public class TourSearchTest extends TourSearchPage {
  // TC01: nhập đúng trường thông tin
  @Test
  public void TC01_Correct() {
    String[] inputs = getTestData("TC01");
    performTourSearch(inputs);
  }

  // TC02: Nhập sai địa điểm
  @Test
  public void TC02_IncorrectLocation() {
    String[] inputs = getTestData("TC02");
    performTourSearch(inputs);

    // Kiểm tra thông báo từ input
    WebElement location = driver.findElement(TourSearchElements.CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Không nhập địa điểm
  @Test
  public void TC03_LocationBlank() {
    String[] inputs = getTestData("TC03");
    performTourSearch(inputs);

    // Kiểm tra thông báo từ input
    WebElement location = driver.findElement(TourSearchElements.CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC04: Nhập thời gian quá khứ
  @Test
  public void TC04_DateInThePast() {
    String[] inputs = getTestData("TC04");
    performTourSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "past";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Nhập số lượng hành khách là '0'
  @Test
  public void TC05_ZeroTraveller() {
    String[] inputs = getTestData("TC05");
    performTourSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "travellers";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("F03_TourSearch/TourSearchData.json", key);
    return new String[] {
        data.get("city").asText(),
        data.get("date").asText(),
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
