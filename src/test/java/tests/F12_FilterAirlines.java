package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.FlightListPage;
import pages.Homepage;

public class F12_FilterAirlines extends BaseTest{ 
    private FlightListPage page;
    private String airlineName;

    public F12_FilterAirlines() {
      this.page = new FlightListPage(driver);
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

    //TC01: Huỷ chọn tất cả hãng bay và refresh kiểm tra lưu thông tin
    @Test
    public void TC01_DeselectAirline() {
      page.deselectAllAirline();
      driver.navigate().refresh();
    }

    //TC02: Kiểm tra không chọn hãng bay có trả về danh sách vé không
    @Test
    public void TC02_CheckEmptyTicket() {
      page.deselectAllAirline();
      List<WebElement> tickets = page.getFlightAirline();

      if (tickets.equals(null)) {
        page.scrollToTop();
        page.getNullTicket();
        Assert.assertTrue(tickets.isEmpty(), "Không có vé nào nhưng test không đúng!");
      } else {
        for (WebElement ticket : tickets) {
          String ticketAirline = ticket.getText().split("\n")[0].trim(); 
          System.out.println("Tên hãng bay trong ticket list: " + ticketAirline);
          Assert.assertEquals(ticketAirline, null, "Danh sách hãng bay không hợp lệ");
        }
      }
    }

    // TC03: So sánh tên hãng bay với các chuyến bay
    @Test
    public void TC3_CompareAirPlane() throws InterruptedException {
      page.deselectAllAirline();
      airlineName = page.selectAirline();

      for (WebElement ticket : page.getFlightAirline()) {
        String ticketAirline = ticket.getText().split("\n")[0].trim(); 
        System.out.println("Tên hãng bay trong ticket list: " + ticketAirline);
        Assert.assertEquals(ticketAirline, airlineName, "Hãng bay không khớp");
      }
    }

    //TC04: So sánh hãng bay từ filter và vé máy bay
    @Test
    public void TC04_CompareAirPlane() throws InterruptedException {
      page.deselectAllAirline();
      airlineName = page.selectAirline();
      String selectedTicket = page.performAirlineTicket();
      System.out.println("Hãng bay đã chọn từ filter: " + airlineName);
      System.out.println("Hãng bay từ ticket: " + selectedTicket);

      // So sánh kết quả
      Assert.assertEquals(airlineName, selectedTicket);
  }
}