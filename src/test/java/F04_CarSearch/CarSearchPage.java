package F04_CarSearch;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.BaseTest;

public class CarSearchPage extends BaseTest {
  // Chọn tab Cars
  public void clickCarTab() {
    WebElement carTab = driver.findElement(CarSearchElements.CAR_TAB);
    carTab.click();
  }

  // Chọn địa điểm đón
  public void enterFrom(String city) {
    WebElement locationBeginField = driver.findElement(CarSearchElements.LOCATION_BEGIN_FIELD);
    locationBeginField.click();

    WebElement locationBeginInput = driver.findElement(CarSearchElements.LOCATION_BEGIN_INPUT);
    locationBeginInput.sendKeys(city);
    delay(300);
    locationBeginInput.sendKeys(Keys.ENTER);
  }

  // Chọn địa điểm trả khách
  public void enterTo(String city) {
    WebElement locationEndField = driver.findElement(CarSearchElements.LOCATION_END_FIELD);
    locationEndField.click();

    WebElement locationEndInput = driver.findElement(CarSearchElements.LOCATION_END_INPUT);
    locationEndInput.sendKeys(city);
    delay(300);
    locationEndInput.sendKeys(Keys.ENTER);
  }

  // Chọn ngày và giờ đón
  public void enterPickupDateTime(String date, String time) {
    WebElement pickUpDate = driver.findElement(CarSearchElements.PICKUP_DATE);
    js.executeScript("arguments[0].removeAttribute('readonly');", pickUpDate);
    pickUpDate.clear();
    pickUpDate.sendKeys(date);

    WebElement pickupTime = driver.findElement(CarSearchElements.PICKUP_TIME);
    pickupTime.click();
    Select pickTime = new Select(pickupTime);
    pickTime.selectByValue(time);
  }

  // Chọn ngày và giờ trả
  public void enterDropoffDateTime(String date, String time) {
    WebElement dropOffDate = driver.findElement(CarSearchElements.DROPOFF_DATE);
    js.executeScript("arguments[0].removeAttribute('readonly');", dropOffDate);
    dropOffDate.clear();
    dropOffDate.sendKeys(date);

    WebElement dropOffTime = driver.findElement(CarSearchElements.DROPOFF_TIME);
    dropOffTime.click();
    Select dropTime = new Select(dropOffTime);
    dropTime.selectByValue(time);
  }

  // Chọn số lượng khách
  public void enterTravellers(String adults, String children) {
    WebElement travellers = driver.findElement(CarSearchElements.TRAVELLERS);
    travellers.click();

    WebElement adultsNum = driver.findElement(CarSearchElements.ADULTS);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(CarSearchElements.CHILDREN);
    childrenNum.clear();
    childrenNum.sendKeys(children);
  }

  // Nhấn tìm kiếm
  public void clickFindButton() {
    WebElement findBtn = driver.findElement(CarSearchElements.FIND_BUTTON);
    findBtn.click();
  }

  // Thực hiện tìm kiếm thuê xe
  public void performCarSearch(String... data) {
    performCarSearch(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
  }

  public void performCarSearch(String from, String to, String pickupDate, String pickupTime,
      String dropoffDate, String dropoffTime, String adults, String children) {
    clickCarTab();
    enterFrom(from);
    enterTo(to);
    enterPickupDateTime(pickupDate, pickupTime);
    enterDropoffDateTime(dropoffDate, dropoffTime);
    enterTravellers(adults, children);
    clickFindButton();
  }
}
