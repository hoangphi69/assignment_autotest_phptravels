package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Homepage {
  private WebDriver driver;
  private JavascriptExecutor js;

  // Component tìm kiếm chuyến bay
  public By FLIGHT_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[1]/button");

  public By FLIGHT_WAY_SELECT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[1]/div/div/div[1]/select");

  public By FLIGHT_TYPE_SELECT = By.id("flight_type");

  public By FLIGHT_FROM_INPUT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[1]/div/input");

  public By FLIGHT_TO_INPUT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[2]/div[2]/input");

  public By FLIGHT_DEPARTURE_DATE_INPUT = By.id("departure");

  public By FLIGHT_TRAVELLERS = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[4]/div/div/div/a");

  public By FLIGHT_ADULTS_INPUT = By.id("fadults");

  public By FLIGHT_CHILDREN_INPUT = By.id("fchilds");

  public By FLIGHT_INFANTS_INPUT = By.id("finfant");

  public By FLIGHT_SEARCH_BUTTON = By.id("flights-search");

  // Component tìm kiếm chuyến bay
  public By HOTEL_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[2]/button");

  public By HOTEL_CITY_SELECT = By.id("select2-hotels_city-container");

  public By HOTEL_CITY_HIDDEN_SELECT = By.id("hotels_city");

  public By HOTEL_CITY_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public By HOTEL_CHECKIN_DATE_INPUT = By.id("checkin");

  public By HOTEL_CHECKOUT_DATE_INPUT = By.id("checkout");

  public By HOTEL_GUEST_DROPDOWN = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[4]/div/div/div/a");

  public By HOTEL_ROOMS_INPUT = By.id("hotels_rooms");

  public By HOTEL_ADULTS_INPUT = By.id("hotels_adults");

  public By HOTEL_CHILDREN_INPUT = By.id("hotels_childs");

  public By HOTEL_SEARCH_BUTTON = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[5]/button");

  // Component tìm kiếm tour
  public By TOUR_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[3]/button");

  public By TOUR_CITY_SELECT = By.id("select2-tours_city-container");

  public By TOUR_CITY_HIDDEN_SELECT = By.id("tours_city");

  public By TOUR_CITY_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public By TOUR_DEPART_DATE = By.id("date");

  public By TOUR_TRAVELLERS = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[3]/div/div/div/a");

  public By TOUR_ADULTS_INPUT = By.id("tours_adults");

  public By TOUR_CHILDREN_INPUT = By.id("tours_child");

  public By TOUR_SEARCH_BUTTON = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[4]/button");

  // Component tìm kiếm thuê xe
  public By CAR_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[4]/button");

  public By CAR_FROM_FIELD = By.xpath(
      "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[1]/div[1]/div[2]/span/span[1]/span/span[1]/div");

  public By CAR_FROM_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public By CAR_TO_FIELD = By.xpath(
      "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[2]/div[1]/div[2]/span/span[1]/span");

  public By CAR_TO_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public By CAR_PICKUP_DATE_INPUT = By.id("cars_from_date");

  public By CAR_PICKUP_TIME_INPUT = By.id("cars_from_time");

  public By CAR_DROPOFF_DATE_INPUT = By.id("cars_to_date");

  public By CAR_DROPOFF_TIME_INPUT = By.id("cars_to_time");

  public By CAR_TRAVELLERS_DROPDOWN = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[5]/div/div/div/a");

  public By CAR_ADULTS_INPUT = By.id("cars_adults");

  public By CAR_CHILDREN_INPUT = By.id("cars_child");

  public By CAR_SEARCH_BUTTON = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[6]/button");

  // Component đổi tiền tệ
  public By CURRENCY_DROPDOWN = By.xpath("/html/body/header/div/div[2]/div[2]/ul/li[2]/a");

  public By CURRENCY_OPTIONS = By.xpath("html/body/header/div/div[2]/div[2]/ul/li[2]/ul");

  public By EXAMPLE_TEXT = By
      .xpath("/html/body/main/section/div/div/div[2]/div[1]/div/div/div[1]/div/div[2]/div[1]/h5/span/strong");

  // Component đổi ngôn ngữ
  public By LANGUAGE_DROPDOWN = By.xpath("/html/body/header/div/div[2]/div[2]/ul/li[1]/a");

  public By LANGUAGE_OPTIONS = By.xpath("/html/body/header/div/div[2]/div[2]/ul/li[1]/ul");

  // Constructor
  public Homepage(WebDriver driver) {
    this.driver = driver;

    js = (JavascriptExecutor) driver;
  }

  // Delay page
  public void delay(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  // Chọn tab chuyến bay
  public void clickFlightTab() {
    WebElement flightTabElement = driver.findElement(FLIGHT_TAB);
    flightTabElement.click();
  }

  // Chọn loại chuyến bay
  public void selectFlightWay(String way) {
    WebElement flightWayElement = driver.findElement(FLIGHT_WAY_SELECT);
    Select flightWay = new Select(flightWayElement);
    flightWay.selectByValue(way);
  }

  // Chọn hạng vé
  public void selectFlightType(String type) {
    WebElement flightTypeElement = driver.findElement(FLIGHT_TYPE_SELECT);
    Select flightType = new Select(flightTypeElement);
    flightType.selectByValue(type);
  }

  // Chọn điểm xuất phát
  public void enterFlightFrom(String from) {
    WebElement fromElement = driver.findElement(FLIGHT_FROM_INPUT);
    fromElement.clear();
    fromElement.sendKeys(from);
  }

  // Chọn điểm đến
  public void enterFlightTo(String to) {
    WebElement toElement = driver.findElement(FLIGHT_TO_INPUT);
    toElement.clear();
    toElement.sendKeys(to);
  }

  // Chọn ngày xuất phát
  public void enterFlightDate(String date) {
    WebElement dateInput = driver.findElement(FLIGHT_DEPARTURE_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", dateInput);
    dateInput.clear();
    dateInput.sendKeys(date);
  }

  // Nhập số lượng khách
  public void enterFlightTravellers(String adults, String children, String infants) {
    WebElement travellersNum = driver.findElement(FLIGHT_TRAVELLERS);
    travellersNum.click();

    WebElement adultsNum = driver.findElement(FLIGHT_ADULTS_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(FLIGHT_CHILDREN_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(children);

    WebElement infantsNum = driver.findElement(FLIGHT_INFANTS_INPUT);
    infantsNum.clear();
    infantsNum.sendKeys(infants);
  }

  // Bấm nút tìm kiếm chuyến bay
  public void clickFlightSearchButton() {
    WebElement searchButton = driver.findElement(FLIGHT_SEARCH_BUTTON);
    searchButton.submit();
  }

  // Thực hiện tìm kiếm chuyến bay
  public void performFlightSearch(String... data) {
    if (data.length < 8) {
      throw new IllegalArgumentException("Invalid number of arguments for flight search");
    }
    performFlightSearch(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
  }

  public void performFlightSearch(String way, String type, String from, String to, String date,
      String adults, String children, String infants) {
    clickFlightTab();
    selectFlightWay(way);
    selectFlightType(type);
    enterFlightFrom(from);
    enterFlightTo(to);
    enterFlightDate(date);
    enterFlightTravellers(adults, children, infants);
    clickFlightSearchButton();
  }

  // Chọn tab Hotel
  public void clickHotelTab() {
    WebElement hotels = driver.findElement(HOTEL_TAB);
    hotels.click();
  }

  // Nhập thành phố
  public void enterHotelLocation(String city) {
    WebElement searchBox = driver.findElement(HOTEL_CITY_SELECT);
    searchBox.click();

    WebElement locationInput = driver.findElement(HOTEL_CITY_INPUT);
    locationInput.clear();
    locationInput.sendKeys(city);
    delay(300);
    locationInput.sendKeys(Keys.ENTER);
  }

  // Chọn thời gian checkin
  public void enterHotelCheckinDate(String date) {
    WebElement checkinInput = driver.findElement(HOTEL_CHECKIN_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", checkinInput);
    checkinInput.clear();
    checkinInput.sendKeys(date);
  }

  // Chọn thời gian checkout
  public void enterHotelCheckoutDate(String date) {
    WebElement checkoutInput = driver.findElement(HOTEL_CHECKOUT_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", checkoutInput);
    checkoutInput.clear();
    checkoutInput.sendKeys(date);
  }

  public void enterHotelTravellers(String rooms, String adults, String children) {
    WebElement guestDropdown = driver.findElement(HOTEL_GUEST_DROPDOWN);
    guestDropdown.click();

    WebElement roomsNum = driver.findElement(HOTEL_ROOMS_INPUT);
    roomsNum.clear();
    roomsNum.sendKeys(rooms);

    WebElement adultsNum = driver.findElement(HOTEL_ADULTS_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(HOTEL_CHILDREN_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(children);
  }

  // Bấm nút tìm kiếm
  public void clickHotelSearchButton() {
    WebElement searchBtn = driver.findElement(HOTEL_SEARCH_BUTTON);
    searchBtn.click();
  }

  // Thực hiện chức năng tìm kiếm khách sạn
  public void performHotelSearch(String... data) {
    performHotelSearch(data[0], data[1], data[2], data[3], data[4], data[5]);
  }

  public void performHotelSearch(String city, String checkin, String checkout, String rooms, String adults,
      String children) {
    clickHotelTab();
    enterHotelLocation(city);
    enterHotelCheckinDate(checkin);
    enterHotelCheckoutDate(checkout);
    enterHotelTravellers(rooms, adults, children);
    clickHotelSearchButton();
  }

  // Chọn tab tour
  public void clickTourTab() {
    WebElement tourTab = driver.findElement(TOUR_TAB);
    tourTab.click();
  }

  // Chọn địa điểm tour
  public void enterTourLocation(String city) {
    WebElement locationField = driver.findElement(TOUR_CITY_SELECT);
    locationField.click();

    WebElement locationInput = driver.findElement(TOUR_CITY_INPUT);
    locationInput.sendKeys(city);
    delay(300);
    locationInput.sendKeys(Keys.ENTER);
  }

  // Chọn thời gian đi
  public void enterTourDepartDate(String date) {
    WebElement departDate = driver.findElement(TOUR_DEPART_DATE);
    js.executeScript("arguments[0].removeAttribute('readonly');", departDate);
    departDate.clear();
    departDate.sendKeys(date);
  }

  // Chọn số lượng khách
  public void enterTourTravellers(String adults, String children) {
    WebElement travellers = driver.findElement(TOUR_TRAVELLERS);
    travellers.click();

    WebElement adultsNum = driver.findElement(TOUR_ADULTS_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(TOUR_CHILDREN_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(children);
  }

  // Nhấn tìm kiếm
  public void clickTourSearchButton() {
    WebElement findBtn = driver.findElement(TOUR_SEARCH_BUTTON);
    findBtn.click();
  }

  // Thực hiện tìm kiếm tour
  public void performTourSearch(String... data) {
    performTourSearch(data[0], data[1], data[2], data[3]);
  }

  public void performTourSearch(String city, String date, String adults, String children) {
    clickTourTab();
    enterTourLocation(city);
    enterTourDepartDate(date);
    enterTourTravellers(adults, children);
    clickTourSearchButton();
  }

  // Chọn tab Cars
  public void clickCarTab() {
    WebElement carTab = driver.findElement(CAR_TAB);
    carTab.click();
  }

  // Chọn địa điểm đón
  public void enterCarFrom(String city) {
    WebElement locationBeginField = driver.findElement(CAR_FROM_FIELD);
    locationBeginField.click();

    WebElement locationBeginInput = driver.findElement(CAR_FROM_INPUT);
    locationBeginInput.sendKeys(city);
    delay(300);
    locationBeginInput.sendKeys(Keys.ENTER);
  }

  // Chọn địa điểm trả khách
  public void enterCarTo(String city) {
    WebElement locationEndField = driver.findElement(CAR_TO_FIELD);
    locationEndField.click();

    WebElement locationEndInput = driver.findElement(CAR_TO_INPUT);
    locationEndInput.sendKeys(city);
    delay(300);
    locationEndInput.sendKeys(Keys.ENTER);
  }

  // Chọn ngày và giờ đón
  public void enterCarPickupDateTime(String date, String time) {
    WebElement pickUpDate = driver.findElement(CAR_PICKUP_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", pickUpDate);
    pickUpDate.clear();
    pickUpDate.sendKeys(date);

    WebElement pickupTime = driver.findElement(CAR_PICKUP_TIME_INPUT);
    pickupTime.click();
    Select pickTime = new Select(pickupTime);
    pickTime.selectByValue(time);
  }

  // Chọn ngày và giờ trả
  public void enterCarDropoffDateTime(String date, String time) {
    WebElement dropOffDate = driver.findElement(CAR_DROPOFF_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", dropOffDate);
    dropOffDate.clear();
    dropOffDate.sendKeys(date);

    WebElement dropOffTime = driver.findElement(CAR_DROPOFF_TIME_INPUT);
    dropOffTime.click();
    Select dropTime = new Select(dropOffTime);
    dropTime.selectByValue(time);
  }

  // Chọn số lượng khách
  public void enterCarTravellers(String adults, String children) {
    WebElement travellers = driver.findElement(CAR_TRAVELLERS_DROPDOWN);
    travellers.click();

    WebElement adultsNum = driver.findElement(CAR_ADULTS_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(CAR_CHILDREN_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(children);
  }

  // Nhấn tìm kiếm
  public void clickCarSearchButton() {
    WebElement findBtn = driver.findElement(CAR_SEARCH_BUTTON);
    findBtn.click();
  }

  // Thực hiện tìm kiếm thuê xe
  public void performCarSearch(String... data) {
    performCarSearch(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
  }

  public void performCarSearch(String from, String to, String pickupDate, String pickupTime,
      String dropoffDate, String dropoffTime, String adults, String children) {
    clickCarTab();
    enterCarFrom(from);
    enterCarTo(to);
    enterCarPickupDateTime(pickupDate, pickupTime);
    enterCarDropoffDateTime(dropoffDate, dropoffTime);
    enterCarTravellers(adults, children);
    clickCarSearchButton();
  }

  // Lấy text element kiểm tra đại diện
  public String getExampleText() {
    WebElement fromTextElement = driver.findElement(EXAMPLE_TEXT);
    return fromTextElement.getText();
  }

  // Bâm dropdown đơn vị tiền tệ
  public void clickDropdownCurrency() {
    WebElement dropdownCurrency = driver.findElement(CURRENCY_DROPDOWN);
    dropdownCurrency.click();
  }

  // Chọn đơn vị tiền tệ
  public void selectCurrencyOption(String toCurrency) {
    WebElement currencyOption = driver.findElement(CURRENCY_OPTIONS)
        .findElement(By.xpath(".//*[contains(text(), '%s')]".formatted(toCurrency)));
    currencyOption.click();
  }

  // Thực hiện đổi đơn vị tiền tệ
  public void performChangeCurrency(String currency) {
    clickDropdownCurrency();
    selectCurrencyOption(currency);
  }

  // Bấm dropdown ngôn ngữ
  public void clickDropdownLanguage() {
    WebElement dropdownLanguage = driver.findElement(LANGUAGE_DROPDOWN);
    dropdownLanguage.click();
  }

  // Chọn ngôn ngữ
  public void selectLanguageOption(String language) {
    WebElement languageOption = driver.findElement(LANGUAGE_OPTIONS)
        .findElement(By.xpath(".//*[contains(text(), '%s')]".formatted(language)));
    languageOption.click();
  }

  // Thực hiện đổi ngôn ngữ
  public void performChangeLanguage(String language) {
    clickDropdownLanguage();
    selectLanguageOption(language);
  }
}
