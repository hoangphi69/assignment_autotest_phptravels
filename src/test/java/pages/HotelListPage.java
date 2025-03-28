package pages;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
  private int svgCount;

  // Component danh sách khách sạn
  public By HOTEL_LIST = By.cssSelector("ul.HotelsData.list-unstyled");

  public By HOTEL_ITEM = By.cssSelector("li.card--item");

  public By HOTEL_NAME = By.cssSelector("h5");

  // Component tìm kiếm danh sách theo tên
  public By SEARCH_NAME_INPUT = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[1]/div[2]/div/input");

  //Component filter rating
  public By RATING = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[1]");
  public By RATING_LIST = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul");
  public By RATING_ALLSTAR = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul/li[1]");
  public By RATING_SELECT = By.cssSelector("li.list-group-item.border-0.rounded-3.p-1.ng-scope");
  public By RATING_START_LABEL = By.className("form-check-label");

  // Component card khách sạn
  public By HOTEL_ITEM_START_LABEL = By.cssSelector(".d-block.d-flex.justify-content-between.align-items-center.gap-2.mt-1.mb-4");

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

  // Lấy số lượng khách sạn trên page
  public int getHotelNumber() {
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

  // Lập danh sách lấy số lượng khách sạn và số lượng sao trong khách sạn
  public Map<WebElement, Integer> getHotelsWithStartsList() {
    WebElement hotelList = driver.findElement(HOTEL_LIST);
    List<WebElement> hotelItems = hotelList.findElements(HOTEL_ITEM);

    Map<WebElement, Integer> hotelMapValue = new LinkedHashMap<>();

    // duyệt từng card hotel trong danh sách card hotel
    for (int i = 0; i < hotelItems.size(); i++) {
      WebElement hotelItem = hotelItems.get(i);
      WebElement hotelDetail = hotelItem.findElement(HOTEL_ITEM_START_LABEL);
      List<WebElement> hotelRating = hotelDetail.findElements(By.tagName("svg"));
      
      hotelMapValue.put(hotelItem, hotelRating.size());
      System.out.println(getHotelName(hotelItem) + (i+1) + ":" + hotelRating.size());
    }
    return hotelMapValue;
  }

  // Chọn hiển thị toàn bộ đánh giá
  public void getAllRating() {
    WebElement ratingCard = driver.findElement(RATING_LIST);
    WebElement ratingList = ratingCard.findElement(RATING_LIST);
    WebElement ratingAllStart = ratingList.findElement(RATING_ALLSTAR);
    ratingAllStart.click();
  }

  // Chọn random 1 đánh giá
  public int selectRandomStarRating() throws InterruptedException {
    // Lấy tất cả các radio button trong nhóm "starRating"
    WebElement ratingCard = driver.findElement(RATING_LIST);
    WebElement ratingList = ratingCard.findElement(RATING_LIST);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", ratingList);
    List<WebElement> listItems = ratingList.findElements(RATING_SELECT);
    Thread.sleep(1000);

    // Kiểm tra danh sách có radio button không
    if (!listItems.isEmpty()) {
      Random rand = new Random();
      int randomIndex = rand.nextInt(listItems.size()); 

      WebElement selectedLi = listItems.get(randomIndex); 
      WebElement radioButton = selectedLi.findElement(By.cssSelector("input[type='radio']")); 
      radioButton.click();

      WebElement startLabel = selectedLi.findElement(RATING_START_LABEL);
      List<WebElement> svgElement = startLabel.findElements(By.tagName("svg"));
      int svgCount = svgElement.size();
      System.out.println("Số sao Rating: " + svgCount);

      return svgCount;
    } else {
      System.out.println("Không tìm thấy thẻ nào.");
      return -1;
    }
  }

  public void scrollToTop() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, 0);");
  }
}
