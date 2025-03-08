package utils;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
  protected WebDriver driver;
  protected String url = "https://phptravels.net/";

  protected void delay(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restore interrupted status
    }
  }

  public String getPopupErrorMessage() {
    try {
        WebElement popUpError = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[2]/div"));
        WebElement popUpMessage = popUpError.findElement(By.xpath("/html/body/div[4]/div[2]/div[2]"));
        return popUpMessage.getText(); 
    } catch (Exception e) {
        return "No output context";
    }
}

  @BeforeClass
  public void setUp() {
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    WebDriverManager.firefoxdriver().setup();
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(url);
    driver.manage().window().maximize();
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      delay(2000);
      driver.quit();
    }
  }
}