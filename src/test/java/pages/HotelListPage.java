package pages;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelListPage {
  private WebDriver driver;
  private JavascriptExecutor js;
  private WebDriverWait wait;

  // Component danh sách khách sạn
  public By HOTEL_LIST = By.cssSelector("ul.HotelsData.list-unstyled");

  public By HOTEL_ITEM = By.cssSelector("li.card--item");

  public By HOTEL_ITEM_NAME = By.cssSelector("h5");

  public By HOTEL_ITEM_PRICE = By.cssSelector("[data-price]");

  public By HOTEL_ITEM_START_LABEL = By
      .cssSelector(".d-block.d-flex.justify-content-between.align-items-center.gap-2.mt-1.mb-4");

  // Component tìm kiếm danh sách theo tên
  public By SEARCH_NAME_INPUT = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[1]/div[2]/div/input");

  // Component filter rating
  public By RATING = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[1]");

  public By RATING_LIST = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul");

  public By RATING_ALLSTAR = By
      .xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul/li[1]");

  public By RATING_SELECT = By.cssSelector("li.list-group-item.border-0.rounded-3.p-1.ng-scope");
  public By RATING_STAR_LABEL = By.className("form-check-label");

  // Component card khách sạn
  public By HOTEL_ITEM_STAR_LABEL = By
      .cssSelector(".d-block.d-flex.justify-content-between.align-items-center.gap-2.mt-1.mb-4");

  public By RATING_START_LABEL = By.className("form-check-label");

  // Component filter giá thuê khách sạn
  public By FILTER_PRICE = By.id("priceRange");

  public By FILTER_PRICE_SLIDER = By.cssSelector("span.irs-bar");

  public By FILTER_PRICE_MIN_HANDLE = By.cssSelector("span.irs-handle.from");

  public By FILTER_PRICE_MIN = By.cssSelector("span.irs-from");

  public By FILTER_PRICE_MAX_HANDLE = By.cssSelector("span.irs-handle.to");

  public By FILTER_PRICE_MAX = By.cssSelector("span.irs-to");

  // Component thanh header page (thanh xanh)
  public By HEADER = By.xpath("/html/body/main/section/div[1]/div[1]/div[2]");

  public By HEADER_HOTEL_NUMBER = By.xpath("/html/body/main/section/div[1]/div[1]/div[2]/h4/span/small/strong");

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
    WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(HOTEL_LIST));
    List<WebElement> hotels = list.findElements(HOTEL_ITEM);
    return hotels;
  }

  // Lấy số lượng khách sạn trên page
  public int getHotelCount() {
    WebElement header = driver.findElement(HEADER);
    WebElement hotelNumber = header.findElement(HEADER_HOTEL_NUMBER);
    String hotelNumberText = hotelNumber.getText().trim();
    if (hotelNumberText.isEmpty()) {
      throw new IllegalStateException("Không tìm thấy số lượng khách sạn trong header!");
    }

    try {
      return Integer.parseInt(hotelNumberText);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Dữ liệu số khách sạn không hợp lệ: " + hotelNumberText);
    }
  }

  // Lấy tên khách sạn
  public String getHotelName(WebElement hotel) {
    return hotel.findElement(HOTEL_ITEM_NAME).getText();
  }

  // Lấy giá thuê khách sạn
  public double getHotelPrice(WebElement hotel) {
    return Double.parseDouble(hotel.findElement(HOTEL_ITEM_PRICE).getText().replaceAll("[^\\d.]", ""));
  }

  // Thực hiện tìm kiếm khách sạn theo tên
  public void performSearchHotel(String name) {
    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_NAME_INPUT));
    input.clear();
    input.sendKeys(name);
  }

  // Lập danh sách lấy số lượng khách sạn và số lượng sao trong khách sạn
  public Map<WebElement, Integer> getHotelsWithStarsList() {
    WebElement hotelList = driver.findElement(HOTEL_LIST);
    List<WebElement> hotelItems = hotelList.findElements(HOTEL_ITEM);

    Map<WebElement, Integer> hotelMapValue = new LinkedHashMap<>();

    // duyệt từng card hotel trong danh sách card hotel
    for (int i = 0; i < hotelItems.size(); i++) {
      WebElement hotelItem = hotelItems.get(i);
      WebElement hotelDetail = hotelItem.findElement(HOTEL_ITEM_STAR_LABEL);
      List<WebElement> hotelRating = hotelDetail.findElements(By.tagName("svg"));

      hotelMapValue.put(hotelItem, hotelRating.size());
    }
    return hotelMapValue;
  }

  // Chọn hiển thị toàn bộ đánh giá
  public void selectAllStar() {
    WebElement ratingCard = driver.findElement(RATING_LIST);
    WebElement ratingList = ratingCard.findElement(RATING_LIST);
    WebElement ratingAllStar = ratingList.findElement(RATING_ALLSTAR);
    ratingAllStar.click();
  }

  // Chọn random 1 đánh giá
  public int selectStarRatingInFilter(int desiredStars) {
    // Lấy tất cả các radio button trong nhóm "starRating"
    WebElement ratingCard = driver.findElement(RATING_LIST);
    WebElement ratingList = ratingCard.findElement(RATING_LIST);
    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", ratingList);
    List<WebElement> listItems = ratingList.findElements(RATING_SELECT);

    // Kiểm tra danh sách có radio button không
    if (!listItems.isEmpty()) {
      for (WebElement item : listItems) {
          WebElement StarLabel = item.findElement(RATING_STAR_LABEL);
          List<WebElement> svgElement = StarLabel.findElements(By.tagName("svg"));
          int svgCount = svgElement.size(); // Đếm số lượng sao thực tế

          if (svgCount == desiredStars) { // Nếu số sao trùng khớp với mong muốn
              WebElement radioButton = item.findElement(By.cssSelector("input[type='radio']"));
              radioButton.click();
              System.out.println("Đã chọn mức sao: " + svgCount);
              return svgCount;
          }
      }
      System.out.println("Không tìm thấy mức sao phù hợp.");
  } else {
      System.out.println("Không tìm thấy thẻ nào.");
  }
  return -1; 
  }

  // Lấy giá vé min từ filter
  public int getFilterPriceMin() {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_PRICE));
    WebElement min = filter.findElement(FILTER_PRICE_MIN);
    int value = Integer.parseInt(min.getText().replaceAll("[^\\d.]", ""));
    return value;
  }

  // Lấy giá vé max từ filter
  public int getFilterPriceMax() {
    WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_PRICE));
    WebElement max = filter.findElement(FILTER_PRICE_MAX);
    int value = Integer.parseInt(max.getText().replaceAll("[^\\d.]", ""));
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

  // Cuộn trang lên trên đầu
  public void scrollToTop() {
    js.executeScript("window.scrollTo(0, 0);");
  }
}
