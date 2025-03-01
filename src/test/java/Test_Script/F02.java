package Test_Script;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F02 extends BaseTest {

  // TC01: Tham số truyền vào hợp lệ
  @Test
  public void testValidHotel() throws InterruptedException {
    searchHotel("Dubai", "15-03-2025", "20-03-2025", "1", "1", "1");
  }

  // TC02: Không nhập địa điểm
  @Test
  public void testSearchWithoutLocation() throws InterruptedException {
    searchHotel("", "15-03-2025", "20-03-2025", "1", "1", "1");

    // Kiểm tra thông báo từ input
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement location = driver.findElement(By.xpath("//*[@id=\"hotels_city\"]"));
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Nhập thời gian trả phòng sớm hơn thời gian nhận phòng
  @Test
  public void testCheckinBeforeCheckout() throws InterruptedException {
    searchHotel("Dubai", "20-03-2025", "15-03-2025", "1", "1", "1");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập số lượng khách tối đa
  @Test
  public void testExceedGuestLimit() throws InterruptedException {
    searchHotel("Dubai", "15-03-2025", "20-03-2025", "1", "-999", "999");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "travellers";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Nhập thời gian check in trước hiện tại
  @Test
  public void testPastCheckinDate() throws InterruptedException {
    searchHotel("Dubai", "15-03-2024", "20-03-2025", "1", "1", "1");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  public void searchHotel(String location, String checkinDate, String checkoutDate, String rooms, String adults,
      String childs) throws InterruptedException {

    JavascriptExecutor js = (JavascriptExecutor) driver;
    // Chọn Hotel
    WebElement hotels = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[2]/button"));
    hotels.click();

    // Đặt hotel
    WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"select2-hotels_city-container\"]"));
    searchBox.click();
    delay(1000);

    if (!location.isEmpty()) { // Nhập input

      // Nhập địa điểm Hotels
      WebElement locationInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
      locationInput.sendKeys(location);
      delay(1000);

      // Nhấn Enter
      locationInput.sendKeys(Keys.ENTER);
      delay(1000);
    }

    // Chọn thời gian đi
    WebElement checkInDate = driver.findElement(By.xpath("//*[@id=\"checkin\"]"));
    js.executeScript("arguments[0].removeAttribute('readonly');", checkInDate);
    checkInDate.clear();
    checkInDate.sendKeys(checkinDate);
    delay(1000);

    // Chọn thời gian đi
    WebElement checkOutDate = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
    js.executeScript("arguments[0].removeAttribute('readonly');", checkInDate);
    checkOutDate.clear();
    checkOutDate.sendKeys(checkoutDate);
    delay(1000);

    // Chọn số lượng khách
    WebElement guestBox = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[4]/div/div/div/a"));
    guestBox.click();
    // Số phòng
    WebElement roomNum = driver.findElement(By.xpath("//*[@id=\"hotels_rooms\"]"));
    roomNum.clear();
    delay(1000);
    roomNum.sendKeys(rooms);
    // Khách người lón
    WebElement adultsNum = driver.findElement(By.xpath("//*[@id=\"hotels_adults\"]"));
    adultsNum.clear();
    delay(1000);
    adultsNum.sendKeys(adults);
    // Khách trẻ em
    WebElement childsNum = driver.findElement(By.xpath("//*[@id=\"hotels_childs\"]"));
    childsNum.clear();
    delay(1000);
    childsNum.sendKeys(childs);

    // Nhấn tìm kiếm
    WebElement findBtn = driver
        .findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[5]/button"));
    findBtn.click();
    delay(1000);

    delay(5000);
    driver.navigate().back();
  }

}