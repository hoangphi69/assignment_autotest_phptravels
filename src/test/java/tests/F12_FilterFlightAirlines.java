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
import base.Helpers;
import base.JsonReader;
import pages.FlightListPage;
import pages.Homepage;

public class F12_FilterFlightAirlines extends BaseTest {
  private FlightListPage page;
  private Integer[] inputs;

  // TC01: Lọc ngẫu nhiên một hãng bay
  @Test
  public void TC01_Select1RandomAirlines() {
    inputs = getTestData("TC01");

    // Huỷ chọn tất cả hãng bay
    List<WebElement> list = page.getFilterAllAirlines();
    page.performFilterDeselectAirlines(list);

    // Chọn ngẫu nhiên 1 hãng bay
    List<WebElement> airlines = Helpers.selectRandomElements(list, inputs[0]);
    List<String> expected = page.performFilterSelectAirlines(airlines);

    // Kiểm tra các chuyến bay có chứa hãng bay được filter không
    for (WebElement flight : page.getFlights()) {
      List<String> actuals = page.getFlightAirlines(flight);
      boolean found = actuals.stream().anyMatch(expected::contains);
      Assert.assertTrue(found, "Tồn tại chuyến bay không hề chứa hãng bay [%s]".formatted(String.join(", ", expected)));
    }
  }

  // TC02: Lọc ngẫu nhiên hai hãng bay
  @Test
  public void TC02_Select2RandomAirlines() {
    inputs = getTestData("TC02");

    List<WebElement> list = page.getFilterAllAirlines();
    page.performFilterDeselectAirlines(list);

    List<WebElement> airlines = Helpers.selectRandomElements(list, inputs[0]);
    List<String> expected = page.performFilterSelectAirlines(airlines);

    for (WebElement flight : page.getFlights()) {
      List<String> actuals = page.getFlightAirlines(flight);
      boolean found = actuals.stream().anyMatch(expected::contains);
      Assert.assertTrue(found, "Tồn tại chuyến bay không hề chứa hãng bay [%s]".formatted(String.join(", ", expected)));
    }
  }

  // TC03: Lọc ngẫu nhiên ba hãng bay
  @Test
  public void TC03_Select3RandomAirlines() {
    inputs = getTestData("TC03");

    List<WebElement> list = page.getFilterAllAirlines();
    page.performFilterDeselectAirlines(list);

    List<WebElement> airlines = Helpers.selectRandomElements(list, inputs[0]);
    List<String> expected = page.performFilterSelectAirlines(airlines);

    for (WebElement flight : page.getFlights()) {
      List<String> actuals = page.getFlightAirlines(flight);
      boolean found = actuals.stream().anyMatch(expected::contains);
      Assert.assertTrue(found, "Tồn tại chuyến bay không hề chứa hãng bay [%s]".formatted(String.join(", ", expected)));
    }
  }

  // TC04: Không chọn hãng bay nào
  @Test
  public void TC04_SelectNoAirlines() {
    List<WebElement> list = page.getFilterAllAirlines();
    page.performFilterDeselectAirlines(list);

    Assert.assertTrue(page.getFlights().isEmpty(), "Không có chuyến bay nào được hiển thị khi không chọn hãng bay.");
  }

  // TC05: Chọn một hãng bay rồi refresh
  @Test
  public void TC05_Select1AirlineAfterDeselectAll() {
    inputs = getTestData("TC05");

    List<WebElement> list = page.getFilterAllAirlines();
    page.performFilterDeselectAirlines(list);

    List<WebElement> airlines = Helpers.selectRandomElements(list, inputs[0]);
    List<String> expected = page.performFilterSelectAirlines(airlines);

    driver.navigate().refresh();
    delay(2000);

    for (WebElement flight : page.getFlights()) {
      List<String> actuals = page.getFlightAirlines(flight);
      boolean found = actuals.stream().anyMatch(expected::contains);
      Assert.assertTrue(found, "Tồn tại chuyến bay không hề chứa hãng bay [%s]".formatted(String.join(", ", expected)));
    }
  }

  public Integer[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("filter-flight-airlines-test-data.json", key);
    return new Integer[] {
        data.get("select-count").asInt(),
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
    delay(2000);
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.navigate().refresh();
  }
}