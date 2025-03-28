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

public class F16_SearchHotelName extends BaseTest {
  private HotelListPage page;
  private String[] inputs;

  // TC01: Tìm kiếm tên khách sạn
  @Test
  public void TC01_ValidSearch() {
    inputs = getTestData("TC01");
    page.performSearchHotel(inputs[0]);

    // Kiểm tra tên khách sạn có chứa chuỗi tìm kiếm không
    for (WebElement hotel : page.getHotels()) {
      String name = page.getHotelName(hotel);
      Assert.assertTrue(name.toLowerCase().contains(inputs[0].toLowerCase()),
          "Tên khách sạn '%s' không chứa '%s'".formatted(name, inputs[0]));
    }
  }

  // TC02: Tìm kiếm tên khách sạn không tồn tại
  @Test
  public void TC02_UnknownSearch() {
    inputs = getTestData("TC02");
    page.performSearchHotel(inputs[0]);

    // Kiểm tra danh sách chứa khách sạn nào không
    int actual = page.getHotels().size();
    int expected = 0;
    Assert.assertEquals(actual, expected, "Danh sách vẫn tồn tại %d khách sạn".formatted(actual));
  }

  // TC03: Tìm kiếm tên khách sạn chứa ký tự đặc biệt
  @Test
  public void TC03_SpecialCharSearch() {
    inputs = getTestData("TC03");
    page.performSearchHotel(inputs[0]);

    // Kiểm tra tên khách sạn có chứa ký tự đặc biệt không
    for (WebElement hotel : page.getHotels()) {
      String name = page.getHotelName(hotel);
      Assert.assertTrue(name.toLowerCase().contains(inputs[0].toLowerCase()),
          "Tên khách sạn '%s' không chứa '%s'".formatted(name, inputs[0]));
    }
  }

  // TC04: Tìm kiếm với khoảng trắng thừa
  @Test
  public void TC04_TrailingSpaceSearch() {
    inputs = getTestData("TC04");
    page.performSearchHotel(inputs[0]);

    // Kiểm tra tên khách sạn có chứa ký tự đặc biệt không
    for (WebElement hotel : page.getHotels()) {
      String name = page.getHotelName(hotel);
      Assert.assertTrue(name.toLowerCase().contains(inputs[0].trim().toLowerCase()),
          "Tên khách sạn '%s' không chứa '%s'".formatted(name, inputs[0]));
    }
  }

  // TC05: Tìm kiếm với toàn bộ khoảng trắng
  @Test
  public void TC05_WhiteSpaceSearch() {
    inputs = getTestData("TC05");

    int expected = page.getHotels().size();

    page.performSearchHotel(inputs[0]);

    int actual = page.getHotels().size();

    // Kiểm tra danh sách có bị thay đổi không
    Assert.assertEquals(actual, expected, "Danh sách bị thay đổi về số lượng");

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
    delay(3000);
  }

  @AfterMethod
  public void navigateBack() {
    driver.navigate().refresh();
    delay(1000);
  }
}
