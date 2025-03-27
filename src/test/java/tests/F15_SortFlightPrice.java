package tests;

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

public class F15_SortFlightPrice extends BaseTest {
  private boolean[] inputs;
  private FlightListPage page;

  // TC01: Sắp xếp chuyến bay giảm dần theo giá
  @Test
  public void TC01_SortDescending() {
    inputs = getTestData("TC01");
    page.performSortFlights(inputs[0]);

    double previousPrice = inputs[0] ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

    // Kiểm tra giá vé có giảm dần không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice <= previousPrice, "Giá vé không giảm");
      previousPrice = currentPrice;
    }
  }

  // TC02: Sắp xếp chuyến bay tăng dần theo giá
  @Test
  public void TC02_SortAscending() {
    inputs = getTestData("TC02");
    page.performSortFlights(inputs[0]);

    double previousPrice = inputs[0] ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

    // Kiểm tra giá vé có tăng dần không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice >= previousPrice, "Giá vé không tăng");
      previousPrice = currentPrice;
    }
  }

  // TC03: Sắp xếp chuyến bay giảm dần theo giá ở trang tiếp theo
  @Test
  public void TC03_SortDescendingOnNextPage() {
    inputs = getTestData("TC03");
    page.performSortFlights(inputs[0]);

    double previousPrice = inputs[0] ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

    // Kiểm tra giá vé có giảm dần ở trang hiện tại không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice <= previousPrice, "Giá vé không giảm");
      previousPrice = currentPrice;
    }

    // Sang trang kế tiếp
    page.performNextPage();

    // Kiểm tra giá vé có giảm dần ở trang kế tiếp không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice <= previousPrice, "Giá vé không giảm");
      previousPrice = currentPrice;
    }
  }

  // TC04: Sắp xếp chuyến bay tăng dần theo giá ở trang tiếp theo
  @Test
  public void TC04_SortAscendingOnNextPage() {
    inputs = getTestData("TC04");
    page.performSortFlights(inputs[0]);

    double previousPrice = inputs[0] ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

    // Kiểm tra giá vé có tăng dần ở trang hiện tại không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice >= previousPrice, "Giá vé không tăng");
      previousPrice = currentPrice;
    }

    // Sang trang kế tiếp
    page.performNextPage();

    // Kiểm tra giá vé có tăng dần ở trang kế tiếp không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice >= previousPrice, "Giá vé không tăng");
      previousPrice = currentPrice;
    }
  }

  // TC05: Sắp xếp chuyến bay giảm dần theo giá rồi refresh trang
  @Test
  public void TC05_SortDescendingAndRefresh() {
    inputs = getTestData("TC05");
    page.performSortFlights(inputs[0]);

    driver.navigate().refresh();
    delay(2000);

    double previousPrice = inputs[0] ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

    // Kiểm tra giá vé có giảm dần không
    for (WebElement flight : page.getFlights()) {
      double currentPrice = page.getFlightPrice(flight);
      Assert.assertTrue(currentPrice <= previousPrice, "Giá vé không giảm");
      previousPrice = currentPrice;
    }
  }

  public boolean[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("sort-flight-price-test-data.json", key);
    return new boolean[] { data.get("asc").asBoolean() };
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
