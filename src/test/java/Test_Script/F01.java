package Test_Script;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class F01 {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
      // System.setProperty("webdriver.chrome.driver", "C:\\Tester\\chromedriver-win64\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://phptravels.net/#");
    }

    public void searchHotel(String location, String checkinDate, String checkoutDate, int guests) {
        if (!location.isEmpty()) { // Nếu location không rỗng, nhập dữ liệu vào ô tìm kiếm
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-hotels_city-container")));
            searchBox.click();
            WebElement searchInput = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
            searchInput.sendKeys(location);
            WebElement resall = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'select2-results__option')]")));
            resall.click();
        }

        // Nhập ngày nhận/trả phòng
        driver.findElement(By.id("checkin")).clear();
        driver.findElement(By.id("checkin")).sendKeys(checkinDate);
        driver.findElement(By.id("checkout")).clear();
        driver.findElement(By.id("checkout")).sendKeys(checkoutDate);

        // Nhập số khách
        WebElement guestBox = driver.findElement(By.id("travellersInput"));
        guestBox.click();
        WebElement adult = driver.findElement(By.id("adultInput"));
        adult.clear();
        adult.sendKeys(String.valueOf(guests));

        driver.findElement(By.id("hotels-search")).click();
    }

    @Test
    public void testValidHotel() {
        searchHotel("Dubai", "2025-03-01", "2025-03-07", 2);
        WebElement resultTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Hotels in')]")));
        Assert.assertTrue(resultTitle.getText().contains("Hotels in"), "Kết quả tìm kiếm không đúng!");
    }

    @Test
    public void testSearchWithoutLocation() {
        searchHotel("", "2025-03-01", "2025-03-07", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'error-message')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi thiếu địa điểm!");
    }

    @Test
    public void testCheckinBeforeCheckout() {
        searchHotel("Dubai", "2025-03-07", "2025-03-01", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Check-out date must be after Check-in date')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi ngày checkout trước checkin!");
    }

    @Test
    public void testExceedGuestLimit() {
        searchHotel("Dubai", "2025-03-01", "2025-03-07", 100);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Maximum guest limit exceeded')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi số lượng khách vượt quá giới hạn!");
    }

    @Test
    public void testPastCheckinDate() {
        searchHotel("Dubai", "2024-02-22", "2025-03-07", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Check-in date cannot be in the past')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi nhập ngày nhận phòng là ngày quá khứ!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}