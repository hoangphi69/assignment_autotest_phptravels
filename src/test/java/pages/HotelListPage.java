package pages;

import java.time.Duration;
import java.util.List;

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

  // Component danh sách khách sạn
  public By HOTEL_LIST = By.cssSelector("ul.HotelsData.list-unstyled");

  public By HOTEL_ITEM = By.cssSelector("li.card--item");

  public By HOTEL_NAME = By.cssSelector("h5");

  // Component tìm kiếm danh sách theo tên
  public By SEARCH_NAME_INPUT = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[1]/div[2]/div/input");

  // Constructor
  public HotelListPage(WebDriver driver) {
    this.driver = driver;
    js = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Delay page
  public void delay(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  // Lấy danh sách các khách sạn
  public List<WebElement> getHotels() {
    WebElement list = driver.findElement(HOTEL_LIST);
    List<WebElement> items = list.findElements(HOTEL_ITEM);
    return items;
  }

  // Lấy tên khách sạn
  public String getHotelName(WebElement hotel) {
    return hotel.findElement(HOTEL_NAME).getText();
  }

  // Thực hiện tìm kiếm khách sạn theo tên
  public void performSearchHotel(String name) {
    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_NAME_INPUT));
    input.clear();
    input.sendKeys(name);
  }
}
