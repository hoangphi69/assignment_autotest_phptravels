package Test_Script;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F02 extends BaseTest{
    
  @Test
  public void TC01_ValidSearch() throws InterruptedException {
    performFlightSearch(
      "oneway",
      "economy_premium",
      "HAN - Noi Bai International Airport",
      "NYC - All Airports",
      5,
      "2",
      true
    );
  }
  
  @Test
  public void TC02_SearchWithoutDeparture() throws InterruptedException {
    performFlightSearch(
      "oneway",
      "economy",
      "",
      "",
      0,
      "0",
      false,
      "Departure City cannot be empty"
    );
  }

  @Test
  public void TC03_SearchWithoutDestination() throws InterruptedException {
    performFlightSearch(
      "oneway", 
      "economy", 
      "HAN - Noi Bai International Airport", 
      "", 
      5, 
      "1", 
      false, 
      "Destination City cannot be empty"
    );
  }

  @Test
  public void TC04_SearchWithPastDate() throws InterruptedException {
    performFlightSearch(
      "oneway", 
      "business", 
      "SGN - Tan Son Nhat International Airport", 
      "LAX - Los Angeles International Airport", 
      -2, 
      "1", 
      false, 
      "Invalid departure date"
    );
  }

  @Test
  public void TC05_SearchWithInvalidPassengerNumber() throws InterruptedException {
    performFlightSearch(
      "oneway", 
      "first", 
      "DAD - Da Nang International Airport", 
      "CDG - Charles de Gaulle International Airport", 
      10,
      "-1", 
      false, 
      "Invalid passenger number"
    );
  }
  
  public void performFlightSearch(String flightType, String ticketClass, String from, String to, int daysFromToday,
      String passengers, boolean expectSuccess) throws InterruptedException {
    performFlightSearch(flightType, ticketClass, from, to, daysFromToday, passengers, expectSuccess, "");
  }

  public void performFlightSearch(String flightType, String ticketClass, String from, String to, int daysFromToday, String passengers, boolean expectSuccess, String alertMessage) throws InterruptedException {
    // Chọn tab chuyến bay
    WebElement flightTabElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[1]/button"));
    flightTabElement.click();

    Thread.sleep(500);

    // Chọn loại chuyến bay
    WebElement flightWayElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[1]/div/div/div[1]/select"));
    Select flightWay = new Select(flightWayElement);
    flightWay.selectByValue(flightType);

    // Chọn hạng vé
    Thread.sleep(500);
    WebElement flightClassElement = driver.findElement(By.xpath("//*[@id=\"flight_type\"]"));
    Select flightClass = new Select(flightClassElement);
    flightClass.selectByValue(ticketClass);

    // Chọn điểm xuất phát
    Thread.sleep(500);
    WebElement fromElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[1]/div/input"));
    fromElement.sendKeys(from);

    // Chọn điểm đến
    Thread.sleep(500);
    WebElement toElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[2]/div[2]/input"));
    toElement.sendKeys(to);

    // Chọn ngày xuất phát
    Thread.sleep(500);
    LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);
    String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    WebElement dateInput = driver.findElement(By.xpath("//*[@id=\"departure\"]"));
    dateInput.clear();
    dateInput.sendKeys(formattedDate);

    // Chọn số lượng hành khách
    Thread.sleep(500);
    WebElement travellersElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[4]/div/div/div/a"));
    travellersElement.click();

    WebElement fAdultsElement = driver.findElement(By.xpath("//*[@id=\"fadults\"]"));
    fAdultsElement.clear();
    fAdultsElement.sendKeys(passengers);

    // Bấm nút tìm kiếm chuyến bay
    Thread.sleep(500);
    WebElement flightSearchElement = driver.findElement(By.xpath("//*[@id=\"flights-search\"]"));
    flightSearchElement.submit();

    
    // Kiểm tra tiêu đề trang kết quả hoặc thông báo lỗi
    if (expectSuccess) {
      Thread.sleep(1000);
      String actualTitle = driver.getTitle();
      String expectedTitle = "Flights Result";
      Assert.assertEquals(actualTitle, expectedTitle, "Trang dẫn có tiêu đề không đúng");
    } else {
      Thread.sleep(500);
      Alert alert = driver.switchTo().alert();
      String actualMessage = alert.getText();
      String expectedMessage = alertMessage;
      Assert.assertEquals(actualMessage, expectedMessage, "Nội dung alert sai");
      alert.accept();
    }

    Thread.sleep(3000);
  }
}