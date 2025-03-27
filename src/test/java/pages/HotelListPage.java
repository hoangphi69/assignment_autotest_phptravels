package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelListPage {
  private WebDriver driver;
  private JavascriptExecutor js;
  private WebDriverWait wait;

  // Component tìm kiếm danh sách theo tên
  public By SEARCH_NAME_INPUT = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[1]/div[2]/div/input");

  // Constructor
  public HotelListPage(WebDriver driver) {
    this.driver = driver;
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Thực hiện tìm kiếm khách sạn theo tên
  public void performSearchHotel(String name) {
    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_NAME_INPUT));
    input.clear();
    input.sendKeys(name);
  }
}
