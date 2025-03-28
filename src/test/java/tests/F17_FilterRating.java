package tests;

import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.Homepage;
import pages.HotelListPage;

public class F17_FilterRating extends BaseTest {
  private HotelListPage page;

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

  // TC01: So sánh số sao từ filter với số sao rating khách sạn
  @Test
  public void TC_CompareRating() throws InterruptedException {
    int expected = page.selectRandomStarRating();
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau");
    }
  }

  // TC02: So sánh số lượng khách sạn hiển thị trên page với thực tế
  @Test
  public void TC_CompareHotelNumber() throws InterruptedException {
    page.selectRandomStarRating();
    int expected = page.getHotelCount();

    Map<WebElement, Integer> hotelMap = page.getHotelsWithStarsList(); // Lấy danh sách khách sạn thực tế
    int actualHotelCount = hotelMap.size(); // Đếm số khách sạn lấy từ danh sách

    System.out.println("Số khách sạn từ header (expected): " + expected);
    System.out.println("Số khách sạn từ danh sách thực tế (actual): " + actualHotelCount);

    // So sánh
    Assert.assertEquals(actualHotelCount, expected, "Số lượng khách sạn không khớp từ page không khớp với thực tế!");
  }
}
