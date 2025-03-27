package tests;

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

public class F16_SearchHotelName extends BaseTest {
  private HotelListPage page;
  private String[] inputs;

  @Test
  public void TC01_Test() {
    inputs = getTestData("TC01");
    page.performSearchHotel("adu");
  }

  public String[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("search-hotel-name-test-data.json", key);
    return new String[] { data.get("search").asText() };
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
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.navigate().refresh();
  }
}
