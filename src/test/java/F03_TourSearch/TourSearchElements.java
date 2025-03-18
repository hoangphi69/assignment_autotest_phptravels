package F03_TourSearch;

import org.openqa.selenium.By;

public class TourSearchElements {
  public static By TOUR_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[3]/button");

  public static By CITY_SELECT = By.id("select2-tours_city-container");

  public static By CITY_HIDDEN_SELECT = By.id("tours_city");

  public static By CITY_INPUT = By.xpath("/html/body/span/span/span[1]/input");

  public static By DEPART_DATE = By.id("date");

  public static By TRAVELLERS = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[3]/div/div/div/a");

  public static By ADULTS = By.id("tours_adults");

  public static By CHILDREN = By.id("tours_child");

  public static By FIND_BUTTON = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[4]/button");
}
