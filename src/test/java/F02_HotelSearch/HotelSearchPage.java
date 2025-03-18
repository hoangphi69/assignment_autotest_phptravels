package F02_HotelSearch;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import utils.BaseTest;

public class HotelSearchPage extends BaseTest {
  // Chọn tab Hotel
  public void clickHotelTab() {
    WebElement hotels = driver.findElement(HotelSearchElements.HOTEL_TAB);
    hotels.click();
  }

  // Nhập thành phố
  public void enterCity(String city) {
    WebElement searchBox = driver.findElement(HotelSearchElements.CITY_SELECT);
    searchBox.click();

    WebElement locationInput = driver.findElement(HotelSearchElements.CITY_INPUT);
    locationInput.clear();
    locationInput.sendKeys(city);
    delay(300);
    locationInput.sendKeys(Keys.ENTER);
  }

  // Chọn thời gian checkin
  public void enterCheckinDate(String date) {
    WebElement checkinInput = driver.findElement(HotelSearchElements.CHECKIN_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", checkinInput);
    checkinInput.clear();
    checkinInput.sendKeys(date);
  }

  // Chọn thời gian checkout
  public void enterCheckoutDate(String date) {
    WebElement checkoutInput = driver.findElement(HotelSearchElements.CHECKOUT_DATE_INPUT);
    js.executeScript("arguments[0].removeAttribute('readonly');", checkoutInput);
    checkoutInput.clear();
    checkoutInput.sendKeys(date);
  }

  // Bấm dropdown số lượng
  public void clickGuestDropdown() {
    WebElement guestDropdown = driver.findElement(HotelSearchElements.GUEST_DROPDOWN);
    guestDropdown.click();
  }

  // Nhập số lượng phòng
  public void enterRoomsQuantity(String quantity) {
    WebElement roomsNum = driver.findElement(HotelSearchElements.ROOMS_QUANTITY_INPUT);
    roomsNum.clear();
    roomsNum.sendKeys(quantity);
  }

  // Nhập số lượng người lớn
  public void enterAdultsQuantity(String quantity) {
    WebElement adultsNum = driver.findElement(HotelSearchElements.ADULTS_QUANTITY_INPUT);
    adultsNum.clear();
    adultsNum.sendKeys(quantity);
  }

  // Nhập số lượng trẻ em
  public void enterChildrenQuantity(String quantity) {
    WebElement childrenNum = driver.findElement(HotelSearchElements.CHILDREN_QUANTITY_INPUT);
    childrenNum.clear();
    childrenNum.sendKeys(quantity);
  }

  // Bấm nút tìm kiếm
  public void clickSearchButton() {
    WebElement searchBtn = driver.findElement(HotelSearchElements.SEARCH_BUTTON);
    searchBtn.click();
  }

  public void searchHotel(String... data) {
    searchHotel(data[0], data[1], data[2], data[3], data[4], data[5]);
  }

  // Thực hiện chức năng tìm kiếm khách sạn
  public void searchHotel(String city, String checkin, String checkout, String rooms, String adults,
      String children) {
    clickHotelTab();
    enterCity(city);
    enterCheckinDate(checkin);
    enterCheckoutDate(checkout);
    clickGuestDropdown();
    enterRoomsQuantity(rooms);
    enterAdultsQuantity(adults);
    enterChildrenQuantity(children);
    clickSearchButton();
  }
}