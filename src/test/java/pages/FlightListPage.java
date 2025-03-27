package pages;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightListPage {
  private WebDriver driver;
  private WebDriverWait wait;
  private String airlineName;


  // Component danh sách chuyến bay
  public By FLIGHT_LIST = By.id("flight-list");
  public By FLIGHT_LIST_COUNT = By.xpath("/html/body/main/section/div[1]/div[1]/div[2]/h4/span/small/strong");
  public By FLIGHT_ITEM = By.cssSelector("li.flight-item");
  public By FLIGHT_CARD = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div");
  // Ticket detail
  public By FLIGHT_CARD_DETAIL = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]");
  // Departure information
  public By FLIGHT_CARD_DEPARTURE_PORT = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[1]/div[1]/div[1]");
  public By FLIGHT_CARD_DEPARTURE_AIRPORT = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[1]/div[1]/div[2]");
  public By FLIGHT_CARD_DEPARTIME_TIME = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[2]/form/div/div/div/div[1]/div/div[1]/div[1]/div[3]/small");
  // Arrival information
  public By FLIGHT_CARD_ARRIVAL_PORT = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[1]/div[3]/div/div[1]/span[1]");
  public By FLIGHT_CARD_ARRIVAL_AIRPORT = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[1]/div[3]/div/div[2]");
  public By FLIGHT_CARD_ARRIVAL_TIME = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[1]/div[3]/div/div[3]/small");
  // Plane Airplane
  public By PLANE_CARD = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[1]/form/div/div/div/div[1]/div/div[2]/div[1]");
  public By PLANE_AIRLANE = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/ul/li[2]/form/div/div/div/div[1]/div/div[2]/div[1]/div/div[1]/div/span");
  public By FLIGHT_LIST_NULL = By.xpath("/html/body/main/section/div[2]/div[2]/div[3]/div/div");

  // Component filter chặng bay
  public By FILTER_SEGMENTS = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[1]");

  // Component filter hãng bay
  public By AIRLINES_LIST = By.xpath("/html/body/main/section/div[2]/div[2]/div[2]/div/div[3]/div[2]");
  public By AIRLINE_ITEMS = By.cssSelector("html body#fadein main section.ng-scope div.container.mb-5.mt-2 div.row.g-3 div.col-md-3.order-md-1.order-2 div.flights_filter div.card.mt-3.border.rounded-4 div.card-body.p-4 ul.list.remove_duplication.checkbox-group.oneway--checkbox-filter ul.list.remove_duplication.checkbox-group.oneway--checkbox-filter li.ng-scope");
  public By AIRLINES_ITEM(String airlineName) {
    return By.xpath(String.format("//*[@id='%s']", airlineName));
}

  // Constructor
  public FlightListPage(WebDriver driver) {
    this.driver = driver;

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

  // Lấy element thành dạng text
  public String getElementToText(By element) {
    WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    return field.getText();
}

  // các chuyến bay
  public List<WebElement> getFlightAirline() {
    if (driver.findElements(FLIGHT_CARD).isEmpty()) {
      System.out.println("Không tìm thấy FLIGHT_CARD.");
      return Collections.emptyList();
  }
    WebElement flight_ticket = driver.findElement(FLIGHT_CARD);
    if (flight_ticket.findElements(FLIGHT_CARD_DETAIL).isEmpty()) {
      System.out.println("Không tìm thấy FLIGHT_CARD_DETAIL.");
      return Collections.emptyList();
  }
    WebElement flight_detail = flight_ticket.findElement(FLIGHT_CARD_DETAIL);
    if (flight_detail.findElements(PLANE_CARD).isEmpty()) {
      System.out.println("Không tìm thấy PLANE_CARD.");
      return Collections.emptyList();
  }
    WebElement plane_card = flight_detail.findElement(PLANE_CARD);
    List<WebElement> flights = plane_card.findElements(PLANE_AIRLANE);

    if (flights.isEmpty()) {
      System.out.println("Không tìm thấy chuyến bay nào.");
      return Collections.emptyList(); // Trả về danh sách rỗng để tránh lỗi
        }
        
    return flights;
  }

// Chọn hãng bay
public String selectAirline() throws InterruptedException {
  System.out.println(">>-----select");
  WebElement airline_List = driver.findElement(AIRLINES_LIST);
  List<WebElement> checkbox = airline_List.findElements(By.cssSelector("input.airline-checkbox"));
  List<String> checkboxID = checkbox.stream()
      .map(e -> e.getAttribute("id"))
      .collect(Collectors.toList());

  Random random = new Random();
  String randomID;
  WebElement randomCheckbox;

  do {
      randomID = checkboxID.get(random.nextInt(checkboxID.size()));
      randomCheckbox = driver.findElement(By.id(randomID));
  } while (randomCheckbox.isSelected()); // Chỉ chọn checkbox chưa được chọn

  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", randomCheckbox);
  Thread.sleep(2000);
  randomCheckbox.click();

  WebElement label = driver.findElement(By.xpath("//label[@for='" + randomID + "']"));
  airlineName = label.getText().replaceAll("\\(\\d+\\)", "").trim(); // Gán vào biến toàn cục

  System.out.println("Hãng bay đã chọn: " + airlineName);

  return airlineName;
}


// // So sánh airline từ filter Airline và ticket
// public boolean compareAirlineSelection() {
//   System.out.println(">>-----compare");
//   // Chạy performAirline để chọn hãng bay
//   try {
//       // Chạy performAirlineTicket để lấy airline đã chọn
//       WebElement flight_ticket = driver.findElement(FLIGHT_CARD);
//       WebElement flight_detail = flight_ticket.findElement(FLIGHT_CARD_DETAIL);
//       WebElement plane_card = flight_detail.findElement(PLANE_CARD);
//       String selectedAirline = plane_card.findElement(PLANE_AIRLANE).getText().split("\n")[0].trim();
//       System.out.println(" Airline from Ticket: " + selectedAirline);
//       System.out.println(" Airline Selected: " + airlineName);
//       // So sánh kết quả
//       if (airlineName.equals(selectedAirline)) {
//         System.out.println("Hãng bay hợp lệ");
//         return true;
//       } else {
//         System.out.println("Hãng bay không hợp lệ");
//         return false;
//       }
//   } catch (Exception e) {
//       System.out.println("Error in airline selection: " + e.getMessage());
//       return false;
//   }
// }

  // Bỏ chọn tất cã hãng bay
  public void deselectAllAirline() {
    System.out.println(">>-----deselect");
    WebElement airline_List = driver.findElement(AIRLINES_LIST);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'end'});", airline_List);
    List<WebElement> checkboxes = airline_List.findElements(By.cssSelector("input.airline-checkbox"));

    // Duyệt qua từng checkbox và click nếu nó chưa được chọn
    for (WebElement checkbox : checkboxes) {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
      checkbox.click();
    }
  }

  public void getNullTicket() {
    WebElement nullTicket = driver.findElement(FLIGHT_LIST_NULL);
    nullTicket.getText();
  }

  // các chuyến bay
  public List<WebElement> getFlights() {
    WebElement flight_ticket = driver.findElement(FLIGHT_CARD);
    WebElement flight_detail = flight_ticket.findElement(FLIGHT_CARD_DETAIL);
    WebElement plane_card = flight_detail.findElement(PLANE_CARD);
    List<WebElement> flights = plane_card.findElements(FLIGHT_ITEM);
    return flights;
  }

  public void scrollToTop() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, 0);");
}

  // Chọn vé bay
  public String performAirlineTicket() {
    WebElement flight_ticket = driver.findElement(FLIGHT_CARD);
    WebElement flight_detail = flight_ticket.findElement(FLIGHT_CARD_DETAIL);

    String departureFrom = flight_detail.findElement(FLIGHT_CARD_DEPARTURE_PORT).getText();
    System.out.println("Departure From: " + departureFrom);

    String departureTime = flight_detail.findElement(FLIGHT_CARD_DEPARTIME_TIME).getText();
    System.out.println("Departure Time: " + departureTime);

    String arrivalFrom = flight_detail.findElement(FLIGHT_CARD_ARRIVAL_PORT).getText();
    System.out.println("Arrival From: " + arrivalFrom);

    String arrivalTime = flight_detail.findElement(FLIGHT_CARD_ARRIVAL_TIME).getText();
    System.out.println("Arrival Time: " + arrivalTime);

    WebElement plane_card = flight_detail.findElement(PLANE_CARD);
    String airline = plane_card.findElement(PLANE_AIRLANE).getText().split("\n")[0].trim();
    System.out.println("Airline: " + airline);

    return airline;
  }
}
