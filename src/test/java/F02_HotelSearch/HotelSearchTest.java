package F02_HotelSearch;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import utils.JsonReader;

public class HotelSearchTest extends HotelSearchPage {
  // TC01: Tham số truyền vào hợp lệ
  @Test
  public void testValidHotel() {
    String[] inputs = getTestData("TC01");
    searchHotel(inputs);
  }

  // TC02: Không nhập địa điểm
  @Test
  public void testSearchWithoutLocation() {
    String[] inputs = getTestData("TC02");
    searchHotel(inputs);

    // Kiểm tra thông báo từ input
    WebElement location = driver.findElement(HotelSearchElements.CITY_HIDDEN_SELECT);
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Nhập thời gian trả phòng sớm hơn thời gian nhận phòng
  @Test
  public void testCheckinBeforeCheckout() {
    String[] inputs = getTestData("TC03");
    searchHotel(inputs);

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
    String[] inputs = getTestData("TC04");
    searchHotel(inputs);

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
    String[] inputs = getTestData("TC05");
    searchHotel(inputs);

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: "
        + actual);
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("F02_HotelSearch/HotelSearchData.json", key);
    return new String[] {
        data.get("city").asText(),
        data.get("checkin").asText(),
        data.get("checkout").asText(),
        data.get("rooms").asText(),
        data.get("adults").asText(),
        data.get("children").asText(),
    };
  }

  @AfterMethod
  public void navigateBack() {
    delay(5000);
    driver.get(BASE_URL);
  }
}
