package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import base.Helpers;
import pages.FlightListPage;
import pages.Homepage;

public class F12_FilterFlightAirlines extends BaseTest {
  private FlightListPage page;
  private String airlineName;

  @Test
  public void TC01_Select1RandomAirlines() {
    List<WebElement> list = page.getFilterAirlines();
    page.performFilterDeselectAirlines(list);

    List<WebElement> airlines = Helpers.selectRandomElements(list, 1);
    List<String> expected = page.performFilterSelectAirlines(airlines);

    for (WebElement flight : page.getFlights()) {
      List<String> actuals = page.getFlightAirlines(flight);
      boolean found = actuals.stream().anyMatch(expected::contains);
      Assert.assertTrue(found, "Chuyến bay không hề chứa hãng bay [%s]".formatted(String.join(", ", expected)));
    }
  }

  @BeforeClass
  public void navigateToPage() {
    Homepage homepage = new Homepage(driver);
    driver.findElement(homepage.FEATURED_FLIGHT_1).click();
    delay(5000);
  }

  @BeforeMethod
  public void construct() {
    page = new FlightListPage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.navigate().refresh();
  }
}