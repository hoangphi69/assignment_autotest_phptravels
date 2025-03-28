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

public class F11_FilterFlightPrice extends BaseTest {
  private Integer[] inputs;
  private FlightListPage page;

  // TC01: Filter danh sách theo giá vé
  @Test
  public void TC01_FilterPrice() {
    inputs = getTestData("TC01");
    page.performFilterByPrice(inputs[0], inputs[1]);

    // Kiểm tra các giá vé các chuyến bay có nằm trong khoảng giá không
    for (WebElement flight : page.getFlights()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      int actual = page.getFlightPrice(flight);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %d vé không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  // TC02: Filter danh sách với giá vé max
  @Test
  public void TC02_FilterPriceMax() {
    inputs = getTestData("TC02");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement flight : page.getFlights()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      int actual = page.getFlightPrice(flight);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %d vé không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  // TC03: Filter danh sách theo giá vé min
  @Test
  public void TC03_FilterPriceMin() {
    inputs = getTestData("TC03");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement flight : page.getFlights()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      int actual = page.getFlightPrice(flight);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %d vé không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  // TC04: Filter danh sách theo khoảng giá vé không tồn tại
  @Test
  public void TC04_FilterPriceMiddle() {
    inputs = getTestData("TC04");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement flight : page.getFlights()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      int actual = page.getFlightPrice(flight);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %d vé không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  // TC05: Filter danh sách theo giá vé chính xác
  @Test
  public void TC05_FilterPricePrecise() {
    inputs = getTestData("TC05");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement flight : page.getFlights()) {
      int max = page.getFilterPriceMax();
      int actual = page.getFlightPrice(flight);
      Assert.assertTrue(actual == max,
          "Giá %d vé không thuộc khoảng [%d, %d]".formatted(actual, max, max));
    }
  }

  public Integer[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("filter-flight-price-test-data.json", key);
    return new Integer[] {
        data.get("min").asInt(),
        data.get("max").asInt(),
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
