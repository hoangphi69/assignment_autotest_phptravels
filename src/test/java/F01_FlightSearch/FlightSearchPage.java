package F01_FlightSearch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.BaseTest;

public class FlightSearchPage extends BaseTest {
  // Chọn tab chuyến bay
  public void clickFlightTab() {
    WebElement flightTabElement = driver.findElement(FlightSearchElements.FLIGHT_TAB);
    flightTabElement.click();
  }

  // Chọn loại chuyến bay
  public void selectFlightWay(String way) {
    WebElement flightWayElement = driver.findElement(FlightSearchElements.FLIGHT_WAY_SELECT);
    Select flightWay = new Select(flightWayElement);
    flightWay.selectByValue(way);
  }

  // Chọn hạng vé
  public void selectFlightType(String type) {
    WebElement flightTypeElement = driver.findElement(FlightSearchElements.FLIGHT_TYPE_SELECT);
    Select flightType = new Select(flightTypeElement);
    flightType.selectByValue(type);
  }

  // Chọn điểm xuất phát
  public void enterFlightFrom(String from) {
    WebElement fromElement = driver.findElement(FlightSearchElements.FLIGHT_FROM_INPUT);
    fromElement.clear();
    fromElement.sendKeys(from);
  }

  // Chọn điểm đến
  public void enterFlightTo(String to) {
    WebElement toElement = driver.findElement(FlightSearchElements.FLIGHT_TO_INPUT);
    toElement.clear();
    toElement.sendKeys(to);
  }

  // Chọn ngày xuất phát
  public void enterFlightDate(String date) {
    // LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);
    // String formattedDate =
    // futureDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    WebElement dateInput = driver.findElement(FlightSearchElements.DEPARTURE_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", dateInput);
    dateInput.clear();
    dateInput.sendKeys(date);
  }

  // Chọn số lượng khách
  public void clickTravellersQuantity() {
    WebElement travellersNum = driver.findElement(FlightSearchElements.TRAVELLERS_QUANTITY);
    travellersNum.click();
  }

  // Nhập số lượng khách người lớn
  public void enterAdultsQuantity(String quantity) {
    WebElement adultsNum = driver.findElement(FlightSearchElements.ADULTS_QUANTITY_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(quantity);
  }

  // Nhập số lượng khách trẻ em
  public void enterChildrenQuantity(String quantity) {
    WebElement childrenNum = driver.findElement(FlightSearchElements.CHILDREN_QUANTITY_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(quantity);
  }

  // Nhập số lượng khách trẻ sơ sinh
  public void enterInfantsQuantity(String quantity) {
    WebElement infantsNum = driver.findElement(FlightSearchElements.INFANTS_QUANTITY_INPUT);
    infantsNum.clear();
    infantsNum.sendKeys(quantity);
  }

  // Bấm nút tìm kiếm chuyến bay
  public void clickSearchButton() {
    WebElement searchButton = driver.findElement(FlightSearchElements.SEARCH_BUTTON);
    searchButton.submit();
  }

  public void performFlightSearch(String... data) {
    if (data.length < 8) {
      throw new IllegalArgumentException("Invalid number of arguments for flight search");
    }
    performFlightSearch(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
  }

  public void performFlightSearch(String way, String type, String from, String to, String date,
      String adults, String children, String infants) {
    clickFlightTab();
    delay(300);
    selectFlightWay(way);
    delay(300);
    selectFlightType(type);
    delay(300);
    enterFlightFrom(from);
    delay(300);
    enterFlightTo(to);
    delay(300);
    enterFlightDate(date);
    delay(300);
    clickTravellersQuantity();
    delay(300);
    enterAdultsQuantity(adults);
    delay(300);
    enterChildrenQuantity(children);
    delay(300);
    enterInfantsQuantity(infants);
    delay(300);
    clickSearchButton();
  }
}
