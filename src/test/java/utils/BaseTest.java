package utils;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected  WebDriver driver;
    protected String url = "https://phptravels.net/";

    protected void delay(long milliseconds) {
        try {
          Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt(); // Restore interrupted status
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