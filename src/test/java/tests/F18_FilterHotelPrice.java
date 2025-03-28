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
import pages.Homepage;
import pages.HotelListPage;

public class F18_FilterHotelPrice extends BaseTest {
  private Integer[] inputs;
  private HotelListPage page;

  @Test
  public void TC01_FilterPrice() {
    inputs = getTestData("TC01");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement hotel : page.getHotels()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      double actual = page.getHotelPrice(hotel);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %f khách sạn không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  @Test
  public void TC02_FilterPriceMax() {
    inputs = getTestData("TC02");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement hotel : page.getHotels()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      double actual = page.getHotelPrice(hotel);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %f khách sạn không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  @Test
  public void TC03_FilterPriceMin() {
    inputs = getTestData("TC03");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement hotel : page.getHotels()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      double actual = page.getHotelPrice(hotel);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %f khách sạn không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  @Test
  public void TC04_FilterPriceMiddle() {
    inputs = getTestData("TC04");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement hotel : page.getHotels()) {
      int min = page.getFilterPriceMin();
      int max = page.getFilterPriceMax();
      double actual = page.getHotelPrice(hotel);
      Assert.assertTrue(min <= actual && actual <= max,
          "Giá %f khách sạn không thuộc khoảng [%d, %d]".formatted(actual, min, max));
    }
  }

  @Test
  public void TC05_FilterPricePrecise() {
    inputs = getTestData("TC05");
    page.performFilterByPrice(inputs[0], inputs[1]);

    for (WebElement hotel : page.getHotels()) {
      int max = page.getFilterPriceMax();
      double actual = page.getHotelPrice(hotel);
      Assert.assertTrue(actual == max,
          "Giá %f khách sạn không thuộc khoảng [%d, %d]".formatted(actual, max, max));
    }
  }

  public Integer[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("filter-hotel-price-test-data.json", key);
    return new Integer[] {
        data.get("min").asInt(),
        data.get("max").asInt(),
    };
  }

  @BeforeClass
  public void navigateToPage() {
    Homepage homepage = new Homepage(driver);
    homepage.performHotelSearch("Dubai", "01-04-2025", "05-04-2025", "1", "1", "0");
    delay(5000);
  }

  @BeforeMethod
  public void construct() {
    page = new HotelListPage(driver);
    delay(3000);
  }

  @AfterMethod
  public void navigateBack() {
    driver.navigate().refresh();
    delay(1000);
  }
}
