package tests;

import java.util.List;

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

public class F14_FilterFlightArrival extends BaseTest {
  private String[] inputs;
  private FlightListPage page;

  // TC01: Filter danh sách theo giờ hạ cánh sáng sớm
  @Test
  public void TC01_FilterEarlyMorning() {
    inputs = getTestData("TC01");
    page.performFilterByArrival(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String time = page.getFlightArrival(flight);
      String actual = page.getFlightTypeByTime(time);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC02: Filter danh sách theo giờ hạ cánh buổi sáng
  @Test
  public void TC02_FilterMorning() {
    inputs = getTestData("TC02");
    page.performFilterByArrival(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String time = page.getFlightArrival(flight);
      String actual = page.getFlightTypeByTime(time);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC03: Filter danh sách theo giờ hạ cánh buổi chiều
  @Test
  public void TC03_FilterAfternoon() {
    inputs = getTestData("TC03");
    page.performFilterByArrival(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String time = page.getFlightArrival(flight);
      String actual = page.getFlightTypeByTime(time);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC04: Filter danh sách theo giờ hạ cánh buổi tối
  @Test
  public void TC04_FilterEvening() {
    inputs = getTestData("TC04");
    page.performFilterByArrival(inputs[0]);

    for (WebElement flight : page.getFlights()) {
      String time = page.getFlightArrival(flight);
      String actual = page.getFlightTypeByTime(time);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  // TC05: Filter danh sách theo giờ hạ cánh sáng sớm và kiểm tra sau khi refresh
  @Test
  public void TC05_FilterEarlyMorningWithRefresh() {
    inputs = getTestData("TC05");
    page.performFilterByArrival(inputs[0]);

    driver.navigate().refresh();
    delay(2000);

    for (WebElement flight : page.getFlights()) {
      String time = page.getFlightArrival(flight);
      String actual = page.getFlightTypeByTime(time);
      String expected = inputs[0];
      Assert.assertEquals(actual, expected);
    }
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("filter-flight-time-test-data.json", key);
    return new String[] { data.get("type").asText() };
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
