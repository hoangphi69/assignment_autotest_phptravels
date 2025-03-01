package Test_Script;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F01 extends BaseTest {

  // TC01: Nhập thông tin hợp lệ
  @Test
  public void TC01_ValidSearch() throws InterruptedException {
    performFlightSearch("oneway",
        "economy",
        "HAN - Noi Bai International Airport",
        "NYC - All Airports",
        5,
        "1", "0", "0");
  }

  // TC02: Bỏ trống điểm xuất phát
  @Test
  public void TC02_SearchWithoutDeparture() throws InterruptedException {
    performFlightSearch("oneway",
        "economy",
        "",
        "NYC - All Airports",
        5,
        "1", "0", "0");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "flying from";
    System.out.println("TC02 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC03: Bỏ trống điểm đến
  @Test
  public void TC03_SearchWithoutDestination() throws InterruptedException {
    performFlightSearch("oneway",
        "economy",
        "HAN - Noi Bai International Airport",
        "",
        5,
        "1", "0", "0");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "destination to";
    System.out.println("TC03 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Ngày trong quá khứ
  @Test
  public void TC04_SearchWithPastDate() throws InterruptedException {
    performFlightSearch("oneway",
        "economy",
        "HAN - Noi Bai International Airport",
        "NYC - All Airports",
        -2,
        "1", "0", "0");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "date";
    System.out.println("TC04 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Số lượng hành khách không hợp lệ
  @Test
  public void TC05_SearchWithInvalidPassengerNumber() throws InterruptedException {
    performFlightSearch(
        "oneway",
        "economy",
        "HAN - Noi Bai International Airport",
        "NYC - All Airports",
        5,
        "999",
        "-111",
        "-999");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "travellers";
    System.out.println("TC05 thông báo" + actual);
    Assert.assertTrue(actual.toLowerCase().contains(expected), "Thông báo sai: " + actual);
  }

  public void performFlightSearch(String flightType, String ticketClass, String from, String to, int daysFromToday,
      String adults, String childs, String infants) throws InterruptedException {
    // Chọn tab chuyến bay
    WebElement flightTabElement = driver
        .findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[1]/button"));
    flightTabElement.click();

    delay(1000);

    // Chọn loại chuyến bay
    WebElement flightWayElement = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[1]/div/div/div[1]/select"));
    Select flightWay = new Select(flightWayElement);
    flightWay.selectByValue(flightType);

    // Chọn hạng vé
    delay(1000);
    WebElement flightClassElement = driver.findElement(By.xpath("//*[@id=\"flight_type\"]"));
    Select flightClass = new Select(flightClassElement);
    flightClass.selectByValue(ticketClass);

    // Chọn điểm xuất phát
    delay(1000);
    WebElement fromElement = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[1]/div/input"));
    fromElement.sendKeys(from);

    // Chọn điểm đến
    delay(1000);
    WebElement toElement = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[2]/div[2]/input"));
    toElement.sendKeys(to);

    // Chọn ngày xuất phát
    delay(1000);
    LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);
    String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    WebElement dateInput = driver.findElement(By.xpath("//*[@id=\"departure\"]"));
    dateInput.clear();
    dateInput.sendKeys(formattedDate);

    // Chọn số lượng khách
    WebElement guestBox = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[4]/div/div/div/a"));
    guestBox.click();
    // Khách người lón
    WebElement adultsNum = driver.findElement(By.xpath("//*[@id=\"fadults\"]"));
    adultsNum.clear();
    delay(1000);
    adultsNum.sendKeys(adults);
    // Khách trẻ em
    WebElement childsNum = driver.findElement(By.xpath("//*[@id=\"fchilds\"]"));
    childsNum.clear();
    delay(1000);
    childsNum.sendKeys(childs);
    // Số khách sơ sinh
    WebElement roomNum = driver.findElement(By.xpath("//*[@id=\"finfant\"]"));
    roomNum.clear();
    delay(1000);
    roomNum.sendKeys(infants);

    // Bấm nút tìm kiếm chuyến bay
    delay(1000);
    WebElement flightSearchElement = driver.findElement(By.xpath("//*[@id=\"flights-search\"]"));
    flightSearchElement.submit();
    delay(5000);

  }

  @AfterMethod
  public void navigateBack() {
    driver.navigate().back();
    delay(5000);
  }
}