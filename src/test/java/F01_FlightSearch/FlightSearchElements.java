package F01_FlightSearch;

import org.openqa.selenium.By;

public class FlightSearchElements {
  public static By FLIGHT_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[1]/button");

  public static By FLIGHT_WAY_SELECT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[1]/div/div/div[1]/select");

  public static By FLIGHT_TYPE_SELECT = By.id("flight_type");

  public static By FLIGHT_FROM_INPUT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[1]/div/input");

  public static By FLIGHT_TO_INPUT = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[2]/div[2]/input");

  public static By DEPARTURE_DATE_INPUT = By.id("departure");

  public static By TRAVELLERS_QUANTITY = By
      .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[1]/form/div[2]/div[4]/div/div/div/a");

  public static By ADULTS_QUANTITY_INPUT = By.id("fadults");

  public static By CHILDREN_QUANTITY_INPUT = By.id("fchilds");

  public static By INFANTS_QUANTITY_INPUT = By.id("finfant");

  public static By SEARCH_BUTTON = By.id("flights-search");

}
