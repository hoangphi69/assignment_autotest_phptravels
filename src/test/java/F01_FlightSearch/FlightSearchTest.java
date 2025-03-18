package F01_FlightSearch;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import utils.JsonReader;

public class FlightSearchTest extends FlightSearchPage {
  // TC01: Nhập thông tin hợp lệ
  @Test
  public void TC01_ValidSearch() {
    String[] inputs = getTestData("TC01");
    performFlightSearch(inputs);
  }

  // TC02: Bỏ trống điểm xuất phát
  @Test
  public void TC02_SearchWithoutDeparture() {
    String[] inputs = getTestData("TC02");
    performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "flying from";
    System.out.println("TC02 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC03: Bỏ trống điểm đến
  @Test
  public void TC03_SearchWithoutDestination() {
    String[] inputs = getTestData("TC03");
    performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "destination to";
    System.out.println("TC03 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC04: Ngày trong quá khứ
  @Test
  public void TC04_SearchWithPastDate() {
    String[] inputs = getTestData("TC04");
    performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    System.out.println("TC04 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  // TC05: Số lượng hành khách không hợp lệ
  @Test
  public void TC05_SearchWithInvalidPassengerNumber() {
    String[] inputs = getTestData("TC05");
    performFlightSearch(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "travellers";
    System.out.println("TC05 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("F01_FlightSearch/FlightSearchData.json", key);
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

  @AfterMethod
  public void navigateBack() {
    delay(5000);
    driver.get(BASE_URL);
  }
}