package pages;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
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

  // Component tìm kiếm danh sách theo tên
  public By SEARCH_NAME_INPUT = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[1]/div[2]/div/input");

  //Component đánh giá sao
  public By RATING = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[1]");
  public By RATING_LIST = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul");
  public By RATING_ALLSTAR = By.xpath("/html/body/main/section/div[2]/div/div[1]/div/div[2]/div[1]/div[2]/div/ul/li[1]");
  public By RATING_SELECT = By.cssSelector("li.list-group-item.border-0.rounded-3.p-1.ng-scope");
  public By RATING_START_LABEL = By.className("form-check-label");

  // Component card khách sạn
  public By HOTEL_CARD = By.xpath("/html/body/main/section/div[2]/div/div[2]/div/ul/li[1]/div");
  public By HOTEL_CARD_DETAIL = By.xpath("/html/body/main/section/div[2]/div/div[2]/div/ul/li[1]/div/div/div/div[2]");
  public By HOTEL_CARD_START_LABEL = By.xpath("/html/body/main/section/div[2]/div/div[2]/div/ul/li[1]/div/div/div/div[2]/div/div[1]/div");

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

  // Lấy thông số rating khách sạn
  public int getHotelRating() {
    WebElement hotelCard = driver.findElement(HOTEL_CARD);
    WebElement startLabel = hotelCard.findElement(HOTEL_CARD_START_LABEL);
    List<WebElement> svgElement = startLabel.findElements(By.tagName("svg"));
    int ratingHotel = svgElement.size();
    System.out.println("Rating khách sạn: " + ratingHotel);
    return ratingHotel;
  } 

  // các chuyến bay
  public List<WebElement> getHotelList() {
    if (driver.findElements(HOTEL_CARD).isEmpty()) {
      System.out.println("Không tìm thấy FLIGHT_CARD.");
      return Collections.emptyList();
    }
    WebElement hotel_card = driver.findElement(HOTEL_CARD);

    if (hotel_card.findElements(HOTEL_CARD_DETAIL).isEmpty()) {
      System.out.println("Không tìm thấy FLIGHT_CARD_DETAIL.");
      return Collections.emptyList();
    }
    WebElement hotel_detail = hotel_card.findElement(HOTEL_CARD_DETAIL);

    if (hotel_detail.findElements(HOTEL_CARD_START_LABEL).isEmpty()) {
      System.out.println("Không tìm thấy PLANE_CARD.");
      return Collections.emptyList();
    }
    WebElement start_label = hotel_detail.findElement(HOTEL_CARD_START_LABEL);

    List<WebElement> starts = start_label.findElements(By.tagName("svg"));
    if (starts.isEmpty()) {
      System.out.println("Không tìm thấy chuyến bay nào.");
      return Collections.emptyList(); 
    }

    return starts;
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
      System.out.println("Số lượng thẻ SVG: " + svgCount);
    } else {
      System.out.println("Không tìm thấy thẻ nào.");
    }
    return svgCount;
  }

  public void scrollToTop() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, 0);");
  }
}
