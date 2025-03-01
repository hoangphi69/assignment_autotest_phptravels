package Test_Script;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F03 extends BaseTest {
  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

  // TC01: nhập đúng trường thông tin
  @Test
  public void TC01_Correct() {
    perform_testTours("Tokyo", "25-04-2025", "1");
  }

  // TC02: Nhập sai địa điểm (tham số location lại trả về kết quả trước đó khi
  // nhập sai tên)
  @Test
  public void TC02_IncorrectLocation() {
    perform_testTours("GitGud", "25-04-2025", "1");

    // Kiểm tra thông báo từ input
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement location = driver.findElement(By.xpath("//*[@id=\"tours_city\"]"));
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC03: Không nhập địa điểm (tham số location lại trả về kết quả trước đó khi
  // không nhập địa điểm)
  @Test
  public void TC03_LocationBlank() {
    perform_testTours("", "25-04-2025", "1");

    // Kiểm tra thông báo từ input
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement location = driver.findElement(By.xpath("//*[@id=\"tours_city\"]"));
    String actual = (String) js.executeScript("return arguments[0].validationMessage;", location);
    String expected = "Please select an item in the list.";
    Assert.assertEquals(actual, expected, "Thông báo sai");
  }

  // TC04: Nhập thời gian quá khứ
  @Test
  public void TC04_DateInThePast() {
    perform_testTours("Tokyo", "25-04-2024", "1");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "past";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Nhập số lượng hành khách là '0'
  @Test
  public void TC05_ZeroTraveller() {
    perform_testTours("Tokyo", "25-04-2025", "0");

    // Kiểm tra thông báo từ alert
    Alert alert = driver.switchTo().alert();
    String actual = alert.getText();
    String expected = "past";
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  public void perform_testTours(String location, String depart_date, String adults_number) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Chọn Tours
    WebElement tours = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[3]/button"));
    tours.click();

    // Chọn địa điểm Tours
    WebElement locationField = driver.findElement(By.xpath("//*[@id=\"select2-tours_city-container\"]"));
    locationField.click();
    delay(500);

    if (!location.isEmpty()) { // Nhập input
      // Nhập địa điểm Tours
      WebElement locationInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
      locationInput.sendKeys(location);
      delay(500);

      // Nhấn Enter
      locationInput.sendKeys(Keys.ENTER);
      delay(500);
    } else { // Không nhập input
      System.out.println("Thông báo lỗi TC03: Vui lòng chọn địa điểm");
    }

    // // Chọn thời gian đi
    WebElement departDate = driver.findElement(By.xpath("//*[@id=\"date\"]"));
    js.executeScript("arguments[0].removeAttribute('readonly');", departDate);
    departDate.clear();
    departDate.sendKeys(depart_date);
    delay(500);

    // Chọn số lượng khách
    WebElement travellers = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[3]/div/div/div/a"));
    travellers.click();

    WebElement adults = driver.findElement(By.xpath("//*[@id=\"tours_adults\"]"));
    adults.clear();
    adults.sendKeys(adults_number);
    delay(500);

    // Nhấn tìm kiếm
    WebElement findBtn = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[4]/button"));
    findBtn.click();
    delay(5000);
  }

  @AfterMethod
  public void navigateBack() {
    driver.get(url);
    delay(500);
  }
}
