package tests;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.JsonReader;
import pages.FlightListPage;
import pages.Homepage;

public class F10_FilterFlightSegments extends BaseTest {
  private String[] inputs;
  private FlightListPage page;

  // TC01: Filter danh sách theo chuyến bay một chặng
  @Test
  public void TC01_ChangeToDirect() {
    inputs = getTestData("TC01");
    page.performFilterBySegments(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String actual = page.getFlightTypeBySegments(flight);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC02: Filter danh sách theo chuyến bay 2 chặng
  @Test
  public void TC02_ChangeTo1Stops() {
    inputs = getTestData("TC02");
    page.performFilterBySegments(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String actual = page.getFlightTypeBySegments(flight);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC03: Filter danh sách theo chuyến bay 3 chặng
  @Test
  public void TC03_ChangeTo2Stops() {
    inputs = getTestData("TC03");
    page.performFilterBySegments(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String actual = page.getFlightTypeBySegments(flight);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC04: Filter danh sách về ban đầu (chọn ngẫu nhiên -> chọn tất cả)
  @Test
  public void TC04_ChangeToAll() {
    inputs = getTestData("TC04");
    delay(3000);
    int expected = page.getFlightsCount();

    // Filter chuyến bay theo chặng ngẫu nhiễn (trừ tất cả)
    String[] options = { "Direct", "1 Stops", "2 Stops" };
    String option = options[ThreadLocalRandom.current().nextInt(options.length)];
    page.performFilterBySegments(option);

    // Filter chuyến bay về ban đầu (tất cả)
    page.performFilterBySegments(inputs[0]);

    int actual = page.getFlightsCount();

    // Kiểm tra số lượng trong danh sách có thay đổi không
    Assert.assertEquals(actual, expected);
  }

  // TC05: Filter danh sách và refresh lại trang
  @Test
  public void TC05_ChangeAndRefresh() {
    inputs = getTestData("TC05");
    page.performFilterBySegments(inputs[0]);

    driver.navigate().refresh();

    for (WebElement flight : page.getFlights()) {
      String actual = page.getFlightTypeBySegments(flight);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("filter-flight-segments-test-data.json", key);
    return new String[] {
        data.get("type").asText(),
    };
  }

  @BeforeClass
  public void navigateToPage() {
    Homepage homepage = new Homepage(driver);
    driver.findElement(homepage.FEATURED_FLIGHT_1).click();
    delay(5000);
  }

  @BeforeMethod
  public void construct() {
    page = new FlightListPage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.navigate().refresh();
  }
}
