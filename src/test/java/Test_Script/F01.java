package Test_Script;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F01 extends BaseTest{
  
  // TC01: Nhập thông tin hợp lệ
  @Test
  public void TC01_ValidSearch() throws InterruptedException {
    performFlightSearch(
      "oneway",
      "economy_premium",
      "HAN - Noi Bai International Airport",
      "NYC - All Airports",
      5,
      "2",
      false,
      "sucess test"
    );
  }
  
  // TC02:  Bỏ trống địa điểm đón nhận
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

  // TC03: Bỏ trống điểm đến
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

  // TC04: Ngày không hợp lệ
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

  // TC05: Số lượng khách sạn không hợp lệ
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

  public void performFlightSearch(String flightType, String ticketClass, String from, String to, int daysFromToday, String passengers, boolean expectSuccess, String alertMessage) throws InterruptedException {
    // Chọn tab chuyến bay
    WebElement flightTabElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[1]/button"));
    flightTabElement.click();

    delay(1000);

    // Chọn loại chuyến bay
    WebElement flightWayElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[1]/div/div/div[1]/select"));
    Select flightWay = new Select(flightWayElement);
    flightWay.selectByValue(flightType);

    // Chọn hạng vé
    delay(1000);
    WebElement flightClassElement = driver.findElement(By.xpath("//*[@id=\"flight_type\"]"));
    Select flightClass = new Select(flightClassElement);
    flightClass.selectByValue(ticketClass);

    // Chọn điểm xuất phát
    delay(1000);
    WebElement fromElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[1]/div/input"));
    fromElement.sendKeys(from);

    // Chọn điểm đến
    delay(1000);
    WebElement toElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[2]/div[2]/input"));
    toElement.sendKeys(to);

    // Chọn ngày xuất phát
    delay(1000);
    LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);
    String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    WebElement dateInput = driver.findElement(By.xpath("//*[@id=\"departure\"]"));
    dateInput.clear();
    dateInput.sendKeys(formattedDate);

    // Chọn số lượng hành khách
    delay(1000);
    WebElement travellersElement = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[4]/div/div/div/a"));
    travellersElement.click();

    WebElement fAdultsElement = driver.findElement(By.xpath("//*[@id=\"fadults\"]"));
    fAdultsElement.clear();
    fAdultsElement.sendKeys(passengers);

    // Bấm nút tìm kiếm chuyến bay
    delay(1000);
    WebElement flightSearchElement = driver.findElement(By.xpath("//*[@id=\"flights-search\"]"));
    flightSearchElement.submit();

    delay(2000);
    driver.navigate().back();
  }
}