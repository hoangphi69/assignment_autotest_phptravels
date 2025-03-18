package F03_TourSearch;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import utils.BaseTest;

public class TourSearchPage extends BaseTest {
  // Chọn tab Tours
  public void clickTourTab() {
    WebElement tourTab = driver.findElement(TourSearchElements.TOUR_TAB);
    tourTab.click();
  }

  // Chọn địa điểm Tours
  public void enterLocation(String city) {
    WebElement locationField = driver.findElement(TourSearchElements.CITY_SELECT);
    locationField.click();

    WebElement locationInput = driver.findElement(TourSearchElements.CITY_INPUT);
    locationInput.sendKeys(city);
    delay(300);
    locationInput.sendKeys(Keys.ENTER);
  }

  // Chọn thời gian đi
  public void enterDepartDate(String date) {
    WebElement departDate = driver.findElement(TourSearchElements.DEPART_DATE);
    js.executeScript("arguments[0].removeAttribute('readonly');", departDate);
    departDate.clear();
    departDate.sendKeys(date);
  }

  // Chọn số lượng khách
  public void enterTravellers(String adults, String children) {
    WebElement travellers = driver.findElement(TourSearchElements.TRAVELLERS);
    travellers.click();

    WebElement adultsNum = driver.findElement(TourSearchElements.ADULTS);
    adultsNum.clear();
    adultsNum.sendKeys(adults);

    WebElement childrenNum = driver.findElement(TourSearchElements.CHILDREN);
    childrenNum.clear();
    childrenNum.sendKeys(children);
  }

  // Nhấn tìm kiếm
  public void clickFindButton() {
    WebElement findBtn = driver.findElement(TourSearchElements.FIND_BUTTON);
    findBtn.click();
  }

  public void performTourSearch(String... data) {
    performTourSearch(data[0], data[1], data[2], data[3]);
  }

  public void performTourSearch(String city, String date, String adults, String children) {
    clickTourTab();
    enterLocation(city);
    enterDepartDate(date);
    enterTravellers(adults, children);
    clickFindButton();
  }
}
