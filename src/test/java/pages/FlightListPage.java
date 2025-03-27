package pages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Helpers;

public class FlightListPage {
  private WebDriver driver;
  private WebDriverWait wait;
  private JavascriptExecutor js;

  // Component danh sách chuyến bay
  public By FLIGHT_LIST = By.id("flight-list");

  public By FLIGHT_LIST_COUNT = By.xpath("/html/body/main/section/div[1]/div[1]/div[2]/h4/span/small/strong");

  public By FLIGHT_ITEM = By.cssSelector("li.flight-item");

  public By FLIGHT_ITEM_SEGMENTS = By.cssSelector("[ng-repeat='segment in flight.segments[0]']");

  public By FLIGHT_ITEM_PRICE = By.cssSelector("span.price.ng-binding");

  public By FLIGHT_ITEM_TIME = By.cssSelector("small.ls--1");

  // Component filter chặng bay
  public By FILTER_SEGMENTS = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[1]");

  // Component filter giá vé
  public By FILTER_PRICE = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[2]");

  public By FILTER_PRICE_SLIDER = By.cssSelector("span.irs-bar");

  public By FILTER_PRICE_MIN_HANDLE = By.cssSelector("span.irs-handle.from");

  public By FILTER_PRICE_MIN = By.cssSelector("span.irs-from");

  public By FILTER_PRICE_MAX_HANDLE = By.cssSelector("span.irs-handle.to");

  public By FILTER_PRICE_MAX = By.cssSelector("span.irs-to");

  // Component filter thời gian
  public By FILTER_TIME = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[4]");

  public By FILTER_TIME_DEPARTURE_TAB = By.id("departure-tab");

  public By FILTER_TIME_ARRIVAL_TAB = By.id("arrival-tab");

  // Component sắp xếp
  public By SORT_ASC_BUTTON = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/nav/ul/li[1]/button");

  public By SORT_DESC_BUTTON = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/nav/ul/li[2]/button");

  // Component phân trang
  public By NEXT_PAGE = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/nav/ul/li[4]/button");

  // Constructor
  public FlightListPage(WebDriver driver) {
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

  // Lấy danh sách các chuyến bay
  public List<WebElement> getFlights() {
    WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(FLIGHT_LIST));
    List<WebElement> flights = list.findElements(FLIGHT_ITEM);
    return flights;
  }

  // Lấy số lượng chuyến bay trong danh sách
  public int getFlightsCount() {
    return Integer.parseInt(driver.findElement(FLIGHT_LIST_COUNT).getText());
  }

  // Lấy chặng bay trong chuyến bay
  public List<WebElement> getFlightSegments(WebElement flight) {
    List<WebElement> segments = flight.findElements(FLIGHT_ITEM_SEGMENTS);
    return segments;
  }

  // Lấy giá vé chuyến bay
  public int getFlightPrice(WebElement flight) {
    WebElement price = flight.findElement(FLIGHT_ITEM_PRICE);
    return (int) Math.ceil(Double.parseDouble(price.getText()));
  }

  // Lấy thời gian cất cánh chuyến bay
  public String getFlightDeparture(WebElement flight) {
    WebElement time = flight.findElements(FLIGHT_ITEM_TIME).get(0);
    return time.getText();
  }

  // Lấy thời gian hạ cánh chuyến bay
  public String getFlightArrival(WebElement flight) {
    WebElement time = flight.findElements(FLIGHT_ITEM_TIME).get(1);
    return time.getText();
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

  // Phân loại chuyến bay theo thời gian
  public String getFlightTypeByTime(String timeString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd-MM-yyyy");
    LocalDateTime datetime = LocalDateTime.parse(timeString.toUpperCase(), formatter);
    LocalTime time = datetime.toLocalTime();

    if (!time.isBefore(LocalTime.of(6, 0))) {
      if (time.isBefore(LocalTime.of(12, 0))) {
        return "Morning";
      } else if (time.isBefore(LocalTime.of(18, 0))) {
        return "Afternoon";
      } else {
        return "Evening";
      }
    } else {
      return "Early Morning";
    }
  }

  // Thực hiện filter danh sách theo chặng bay
  public void performFilterBySegments(String type) {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_SEGMENTS));
    filter.findElement(By.xpath(".//*[contains(text(), '%s')]".formatted(type))).click();
  }

  // Lấy giá vé min từ filter
  public int getFilterPriceMin() {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_PRICE));
    WebElement min = filter.findElement(FILTER_PRICE_MIN);
    int value = Integer.parseInt(min.getText().replaceAll("\\s", ""));
    return value;
  }

  // Lấy giá vé max từ filter
  public int getFilterPriceMax() {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_PRICE));
    WebElement max = filter.findElement(FILTER_PRICE_MAX);
    int value = Integer.parseInt(max.getText().replaceAll("\\s", ""));
    return value;
  }

  // Thực hiện filter danh sách theo giá vé
  public void performFilterByPrice(int minPercent, int maxPercent) {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_PRICE));

    WebElement track = filter.findElement(FILTER_PRICE_SLIDER);
    WebElement minHandle = filter.findElement(FILTER_PRICE_MIN_HANDLE);
    WebElement maxHandle = filter.findElement(FILTER_PRICE_MAX_HANDLE);

    // Lấy kích cỡ các element
    int sliderWidth = track.getSize().getWidth();
    int handleWidth = minHandle.getSize().getWidth() / 2;

    // Lấy vị trí hiện tại các element
    int trackStartX = track.getLocation().getX();
    int currentMinX = minHandle.getLocation().getX() + handleWidth;
    int currentMaxX = maxHandle.getLocation().getX() + handleWidth;

    // Vị trí cấn di chuyến đến
    int targetMinX = trackStartX + (int) (sliderWidth * (minPercent / 100.0));
    int targetMaxX = trackStartX + (int) (sliderWidth * (maxPercent / 100.0));

    // Khoảng cách cần di chuyển
    int minMoveBy = targetMinX - currentMinX;
    int maxMoveBy = targetMaxX - currentMaxX;

    // Di chuyển thanh handle
    Actions actions = new Actions(driver);
    actions.clickAndHold(minHandle).moveByOffset(minMoveBy, 0).release().perform();
    actions.clickAndHold(maxHandle).moveByOffset(maxMoveBy, 0).release().perform();
  }

  // Thực hiện filter danh sách theo thời gian cất cánh
  public void performFilterByDeparture(String type) {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_TIME));
    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", filter);
    delay(2000);
    filter.findElement(FILTER_TIME_DEPARTURE_TAB).click();
    filter.findElement(By.id("departure-%s".formatted(Helpers.toKebabCase(type)))).click();
  }

  // Thực hiện filter danh sách theo thời gian hạ cánh
  public void performFilterByArrival(String type) {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_TIME));
    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", filter);
    delay(2000);
    filter.findElement(FILTER_TIME_ARRIVAL_TAB).click();
    filter.findElement(By.id("arrival-%s".formatted(Helpers.toKebabCase(type)))).click();
  }

  // Thực hiện sắp xếp danh sách chuyến bay
  public void performSortFlights(boolean asc) {
    By button = (asc) ? SORT_ASC_BUTTON : SORT_DESC_BUTTON;
    driver.findElement(button).click();
  }

  // Thực hiện sang trang tiếp theo
  public void performNextPage() {
    WebElement button = driver.findElement(NEXT_PAGE);
    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
    delay(2000);
    button.click();
  }
}
