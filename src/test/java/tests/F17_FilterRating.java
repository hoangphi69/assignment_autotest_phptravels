package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HotelListPage;

public class F17_FilterRating extends BaseTest{
    private HotelListPage page;

    @BeforeMethod
    public void construct() {
      page = new HotelListPage(driver);
    }

    // @BeforeClass
    // public void navigateToPage() {
    //   Homepage homepage = new Homepage(driver);
    //   driver.findElement(homepage.FEATURED_FLIGHT_1).click();
    //   delay(5000);
    // }

    @Test
    public void test() throws InterruptedException {
        page.selectRandomStarRating();
        page.getHotelRating();
    }

    @Test 
    public void TC_CompareRating() throws InterruptedException {
      int startPoint = page.selectRandomStarRating();
      int startRating = page.getHotelRating();

      Assert.assertEquals(startPoint, startRating, "Đánh giá không khớp với filter");
    }
}
