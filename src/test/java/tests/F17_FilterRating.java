package tests;

import java.util.Map;

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

public class F17_FilterRating extends BaseTest{
    private HotelListPage page;
    private int[] inputs;

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

  public int[] getTestData(String key) {
    JsonNode data = JsonReader.getTestData("hotel-filter-star-test-data.json", key);
    return new int[] { data.get("rating").asInt() };
  }


  // TC01: So sánh 5 sao từ filter với danh sách sao rating trong khách sạn 
  @Test 
  public void TC01_CompareRating5Star() {
    inputs = getTestData("TC01");
    int expected = page.selectStarRatingInFilter(inputs[0]);
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau" );
    }
  }

  // TC02: So sánh 4 sao từ filter với danh sách sao rating trong khách sạn 
  @Test 
  public void TC02_CompareRating4Star() {
    inputs = getTestData("TC02");
    int expected = page.selectStarRatingInFilter(inputs[0]);
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau" );
    }
  }

  // TC03: So sánh 3 sao từ filter với danh sách sao rating trong khách sạn 
  @Test 
  public void TC03_CompareRating3Star() {
    inputs = getTestData("TC03");
    int expected = page.selectStarRatingInFilter(inputs[0]);
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau" );
    }
  }

  // TC04: So sánh 2 sao từ filter với danh sách sao rating trong khách sạn 
  @Test 
  public void TC04_CompareRating2Star() {
    inputs = getTestData("TC04");
    int expected = page.selectStarRatingInFilter(inputs[0]);
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau" );
    }
  }

  // TC05: So sánh 2 sao từ filter với danh sách sao rating trong khách sạn 
  @Test 
  public void TC05_CompareRating1Star() {
    inputs = getTestData("TC05");
    int expected = page.selectStarRatingInFilter(inputs[0]);
    Map<WebElement, Integer> hotelValue = page.getHotelsWithStarsList();
    for (Map.Entry<WebElement, Integer> entry : hotelValue.entrySet()) {
      int actual = entry.getValue();
      Assert.assertEquals(actual, expected, "Số sao giữa filter và số sao của khách sạn không bằng nhau" );
    }
  }

  // TC06: So sánh số lượng khách sạn hiển thị trên page với thực tế
  @Test 
  public void TC06_CompareHotelAllStar() {
    page.selectAllStar();
    int expected = page.getHotelNumber();
    
    Map<WebElement, Integer> hotelMap = page.getHotelsWithStarsList(); // Lấy danh sách khách sạn thực tế
    int actualHotelCount = hotelMap.size(); // Đếm số khách sạn lấy từ danh sách
    
    System.out.println("Số khách sạn từ header (expected): " + expected);
    System.out.println("Số khách sạn từ danh sách thực tế (actual): " + actualHotelCount);
    
    // So sánh
    Assert.assertEquals(actualHotelCount, expected, "Số lượng khách sạn không khớp từ page không khớp với thực tế!"); 
  }

  // TC07: So sánh số lượng khách sạn hiển thị sau khi đổi lọc đánh giá sao của AllStar
  @Test
  public void TC07_CompareAfterChangedAllStar(){
    Map<WebElement, Integer> hotelMap1 = page.getHotelsWithStarsList(); 
    int actualHotelCount1 = hotelMap1.size();

    page.selectStarRatingInFilter(inputs[0]);
    page.selectAllStar();

    Map<WebElement, Integer> hotelMap2 = page.getHotelsWithStarsList(); 
    int actualHotelCount2 = hotelMap2.size();

    Assert.assertEquals(actualHotelCount1, actualHotelCount2, "Sau khi đổi button dữ liệu 2 trang khác nhau");
  }

}

