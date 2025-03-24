package base;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected JavascriptExecutor js;
  protected final String BASE_URL = "https://phptravels.net/";

  public void delay(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public WebDriver getDriver() {
    return driver;
  }

  @BeforeClass
  public void setUp() {
    // Config UTF-8 cho console
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

    // Khởi tạo trình duyệt
    WebDriverManager.firefoxdriver().setup();
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(BASE_URL);
    driver.manage().window().maximize();

    // Khởi tạo wait
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Khởi tạo JSexe
    js = (JavascriptExecutor) driver;
  }

  @AfterClass
  public void tearDown() {
    // Đóng trình duyệt
    if (driver != null) {
      delay(2000);
      driver.quit();
    }
  }
}