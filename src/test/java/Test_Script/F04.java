package Test_Script;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F04 extends BaseTest {

  // TC01: Nhập chính xác trường thông tin
  @Test
  public void TC01_ValidInput() throws InterruptedException {
    perform_testCar("Tokyo", "Tokyo", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");
  }

  // TC02: Nhập sai địa điểm đón (tham số location lại trả về kết quả trước đó khi
  // nhập sai tên)
  @Test
  public void TC02_IncorrectLocation1() throws InterruptedException {
    perform_testCar("GitGud", "Tokyo", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC03: Bỏ trống địa điểm đón (tham số location lại trả về kết quả trước đó khi
  // không nhập địa điểm)
  @Test
  public void TC03_IncorrectLocation2() throws InterruptedException {
    perform_testCar("", "Tokyo", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "empty";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC04: Nhập sai địa điểm trả khách (tham số location lại trả về kết quả trước
  // đó khi nhập sai tên)
  @Test
  public void TC04_IncorrectLocation3() throws InterruptedException {
    perform_testCar("Tokyo", "GitGud", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC05: Bỏ trống địa điểm trả khách (tham số location lại trả về kết quả trước
  // đó khi không nhập địa điểm)
  @Test
  public void TC05_IncorrectLocation4() throws InterruptedException {
    perform_testCar("Tokyo", "", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");

    // Kiểm tra thông báo alert
    Alert alert = driver.switchTo().alert();
    String expected = "invalid";
    String actual = alert.getText();
    Assert.assertTrue(actual.contains(expected), "Thông báo sai: " + actual);
  }

  // TC06: Bỏ trống cả 2 địa điểm (tham số location lại trả về kết quả trước đó
  // khi không nhập địa điểm)
  // @Test
  // public void TC06_IncorrectLocation5() throws InterruptedException {
  // perform_testCar("", "", "25-04-2025", "03:00", "05-05-2025", "03:00", "1");
  // }

  // TC07: Bỏ trống thời gian đón
  // @Test
  // public void TC07_PickUpTimeBlank() throws InterruptedException {
  // perform_testCar("Tokyo", "Tokyo", "", "03:00");
  // }

  public void perform_testCar(String locationBegin, String locationEnd, String pick_upDate, String pick_upTime,
      String drop_offDate, String drop_offTime, String adultNumber)
      throws InterruptedException {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Chọn tab Cars
    WebElement tours = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[4]/button"));
    tours.click();

    // Chọn địa điểm đón
    WebElement locationBeginField = driver.findElement(By.xpath(
        "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[1]/div[1]/div[2]/span/span[1]/span/span[1]/div"));
    locationBeginField.click();
    delay(500);

    if (!locationBegin.isEmpty()) { // Nhập input
      // Nhập địa điểm đón
      WebElement locationBeginInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
      locationBeginInput.sendKeys(locationBegin);
      delay(500);

      // Nhấn Enter
      locationBeginInput.sendKeys(Keys.ENTER);
      delay(500);
    } else { // Không nhập input
      System.out.println("Thông báo lỗi TC03: Vui lòng chọn địa điểm");
    }

    // Chọn địa điểm trả khách
    WebElement locationEndField = driver.findElement(By.xpath(
        "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[2]/div[1]/div[2]/span/span[1]/span"));
    locationEndField.click();
    delay(500);

    // Nhập địa điểm trả khách
    WebElement locationEndInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
    locationEndInput.sendKeys(locationEnd);
    delay(500);

    // Nhấn Enter
    locationEndInput.sendKeys(Keys.ENTER);
    delay(500);

    // Pickup Date
    WebElement pickUpDate = driver.findElement(By.xpath("//*[@id=\"cars_from_date\"]"));
    js.executeScript("arguments[0].removeAttribute('readonly');", pickUpDate);
    pickUpDate.clear();
    pickUpDate.sendKeys(pick_upDate);
    delay(500);

    // Pickup Time
    WebElement pickupTime = driver.findElement(By.xpath("//*[@id=\"cars_from_time\"]"));
    pickupTime.click();
    Select pickTime = new Select(pickupTime);
    delay(500);
    pickTime.selectByValue(pick_upTime);

    // Drop-off Date
    WebElement dropOffDate = driver.findElement(By.xpath("//*[@id=\"cars_to_date\"]"));
    js.executeScript("arguments[0].removeAttribute('readonly');", dropOffDate);
    dropOffDate.clear();
    dropOffDate.sendKeys(drop_offDate);
    delay(500);

    // Drop-off Time
    WebElement dropOffTime = driver.findElement(By.xpath("//*[@id=\"cars_to_time\"]"));
    dropOffTime.click();
    Select dropTime = new Select(dropOffTime);
    delay(500);
    dropTime.selectByValue(drop_offTime);

    // Chọn số lượng khách
    WebElement travellers = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[5]/div/div/div/a"));
    travellers.click();
    WebElement adults = driver.findElement(By.xpath("//*[@id=\"cars_adults\"]"));
    adults.clear();
    adults.sendKeys(adultNumber);

    // Nhấn tìm kiếm
    WebElement findBtn = driver.findElement(
        By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[6]/button"));
    findBtn.click();
    delay(5000);
  }

  @AfterMethod
  public void navigateBack() {
    driver.get(url);
    delay(5000);
  }
}
