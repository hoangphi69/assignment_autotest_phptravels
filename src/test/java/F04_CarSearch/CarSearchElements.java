package F04_CarSearch;

import org.openqa.selenium.By;

public class CarSearchElements {
    public static By CAR_TAB = By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[4]/button");

    public static By LOCATION_BEGIN_FIELD = By.xpath(
            "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[1]/div[1]/div[2]/span/span[1]/span/span[1]/div");

    public static By LOCATION_BEGIN_INPUT = By.xpath("/html/body/span/span/span[1]/input");

    public static By LOCATION_END_FIELD = By.xpath(
            "/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[2]/div[1]/div[2]/span/span[1]/span");

    public static By LOCATION_END_INPUT = By.xpath("/html/body/span/span/span[1]/input");

    public static By PICKUP_DATE = By.id("cars_from_date");

    public static By PICKUP_TIME = By.id("cars_from_time");

    public static By DROPOFF_DATE = By.id("cars_to_date");

    public static By DROPOFF_TIME = By.id("cars_to_time");

    public static By TRAVELLERS = By
            .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[5]/div/div/div/a");

    public static By ADULTS = By.id("cars_adults");

    public static By CHILDREN = By.id("cars_child");

    public static By FIND_BUTTON = By
            .xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[6]/button");
}
