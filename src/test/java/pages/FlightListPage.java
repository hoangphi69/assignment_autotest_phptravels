package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightListPage {
  private WebDriver driver;
  private WebDriverWait wait;

  // Component danh sách chuyến bay
  public By FLIGHT_LIST = By.id("flight-list");

  public By FLIGHT_LIST_COUNT = By.xpath("/html/body/main/section/div[1]/div[1]/div[2]/h4/span/small/strong");

  public By FLIGHT_ITEM = By.cssSelector("li.flight-item");

  // Component filter chặng bay
  public By FILTER_SEGMENTS = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[1]");

  // Constructor
  public FlightListPage(WebDriver driver) {
    this.driver = driver;

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  // Lấy danh sách các chuyến bay
  public List<WebElement> getFlights() {
    WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(FLIGHT_LIST));
    List<WebElement> flights = list.findElements(FLIGHT_ITEM);
    return flights;
  }

  public int getFlightsCount() {
    return Integer.parseInt(driver.findElement(FLIGHT_LIST_COUNT).getText());
  }

  // Số chặng bay trong một chuyến bay
  public List<WebElement> getFlightSegments(WebElement flight) {
    List<WebElement> legs = flight.findElements(By.cssSelector("[ng-repeat='segment in flight.segments[0]']"));
    return legs;
  }

  // Phân loại chuyến bay theo chặng bay
  public String getFlightTypeBySegments(WebElement flight) {
    int segmentCount = getFlightSegments(flight).size();

    if (segmentCount == 1) {
      return "Direct";
    } else if (segmentCount == 2) {
      return "1 Stops";
    } else if (segmentCount > 2) {
      return "2 Stops";
    } else {
      return "Invalid segment count";
    }
  }

  // Thực hiện filter danh sách theo chặng bay
  public void performFilterBySegments(String type) {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_SEGMENTS));
    filter.findElement(By.xpath(".//*[contains(text(), '%s')]".formatted(type))).click();
  }
}
