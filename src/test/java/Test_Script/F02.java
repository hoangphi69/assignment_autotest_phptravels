package Test_Script;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F02 extends BaseTest {
    private WebDriverWait wait;

    @Test
    public void testValidHotel() throws InterruptedException {
        searchHotel("Dubai", "2025-03-01", "2025-03-07", 2);
        WebElement resultTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Hotels in')]")));
        Assert.assertTrue(resultTitle.getText().contains("Hotels in"), "Kết quả tìm kiếm không đúng!");
    }

    @Test
    public void testSearchWithoutLocation() throws InterruptedException {
        searchHotel("", "2025-03-01", "2025-03-07", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'error-message')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi thiếu địa điểm!");
    }

    @Test
    public void testCheckinBeforeCheckout() throws InterruptedException {
        searchHotel("Dubai", "2025-03-07", "2025-03-01", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Check-out date must be after Check-in date')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi ngày checkout trước checkin!");
    }

    @Test
    public void testExceedGuestLimit() throws InterruptedException{
        searchHotel("Dubai", "2025-03-01", "2025-03-07", 100);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Maximum guest limit exceeded')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi số lượng khách vượt quá giới hạn!");
    }

    @Test
    public void testPastCheckinDate() throws InterruptedException {
        searchHotel("Dubai", "2024-02-22", "2025-03-07", 2);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Check-in date cannot be in the past')]")));
        Assert.assertTrue(error.isDisplayed(), "Không có thông báo lỗi khi nhập ngày nhận phòng là ngày quá khứ!");
    }

    public void searchHotel(String location, String checkinDate, String checkoutDate, int guests) throws InterruptedException {

        // Chọn Hotel
        WebElement hotels = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[2]/button"));
        hotels.click();
        
        // Đặt hotel
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"select2-hotels_city-container\"]")));
        searchBox.click();
        
        // if (!location.isEmpty()) { // Nếu location không rỗng, nhập dữ liệu vào ô tìm kiếm
        //     WebElement searchInput = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
        //     searchInput.sendKeys(location);
        //     WebElement resall = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'select2-results__option')]")));
        //     resall.click();
        // }

        if (!location.isEmpty()) { // Nhập input 

            // Nhập địa điểm Hotels
            WebElement locationInput = driver.findElement(By.xpath("//input[@class='select2-search__field"));
            locationInput.sendKeys(location);
            delay(500);

            // Nhấn Enter
            locationInput.sendKeys(Keys.ENTER);
            delay(500);
        } else { // Không nhập input
            System.out.println("Thông báo lỗi TC03: Vui lòng chọn địa điểm");
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

    
}