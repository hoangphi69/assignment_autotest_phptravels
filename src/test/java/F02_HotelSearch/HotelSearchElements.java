package F02_HotelSearch;

import org.openqa.selenium.By;

public class HotelSearchElements {
  public static By HOTEL_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[2]/button");

  public static By CITY_SELECT = By.id("select2-hotels_city-container");

  public static By CITY_HIDDEN_SELECT = By.id("hotels_city");

  public static By CITY_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public static By CHECKIN_DATE_INPUT = By.id("checkin");

  public static By CHECKOUT_DATE_INPUT = By.id("checkout");

  public static By GUEST_DROPDOWN = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[4]/div/div/div/a");

  public static By ROOMS_QUANTITY_INPUT = By.id("hotels_rooms");

  public static By ADULTS_QUANTITY_INPUT = By.id("hotels_adults");

  public static By CHILDREN_QUANTITY_INPUT = By.id("hotels_childs");

  public static By SEARCH_BUTTON = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[5]/button");
}
