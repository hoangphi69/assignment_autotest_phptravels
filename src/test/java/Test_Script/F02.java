package Test_Script;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F02 extends BaseTest {

    // TC01: Tham số truyền vào hợp lệ
    @Test
    public void testValidHotel() throws InterruptedException {
        searchHotel("Dubai", "2025-03-01", "2025-03-07", "1", "1", "1");
    }

    // TC02: Không nhập địa điểm
    @Test
    public void testSearchWithoutLocation() throws InterruptedException {
        searchHotel("", "2025-03-01", "2025-03-07", "2", "2", "2");
    }

    // TC03: 
    @Test
    public void testCheckinBeforeCheckout() throws InterruptedException {
        searchHotel("Dubai", "2025-03-07", "2025-03-01", "3", "3", "3");
    }

    @Test
    public void testExceedGuestLimit() throws InterruptedException{
        searchHotel("Dubai", "2025-03-01", "2025-03-07", "1", "999", "999");
    }

    @Test
    public void testPastCheckinDate() throws InterruptedException {
        searchHotel("Dubai", "2024-02-22", "2025-03-07", "4", "4", "4");
    }

    public void searchHotel(String location, String checkinDate, String checkoutDate, String rooms, String adults, String childs) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Chọn Hotel
        WebElement hotels = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[2]/button"));
        hotels.click();

        // Đặt hotel
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"select2-hotels_city-container\"]"));
        searchBox.click();
        delay(1000);

        if (!location.isEmpty()) { // Nhập input 
            
            // Nhập địa điểm Hotels
            WebElement locationInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
            locationInput.sendKeys(location);
            delay(1000);

            // Nhấn Enter
            locationInput.sendKeys(Keys.ENTER);
            delay(1000);
        } 

        // Chọn thời gian đi
        WebElement checkInDate = driver.findElement(By.xpath("//*[@id=\"checkin\"]"));
        js.executeScript("arguments[0].removeAttribute('readonly');", checkInDate);
        checkInDate.clear();
        checkInDate.sendKeys(checkinDate);
        delay(1000);

        // Chọn thời gian đi
        WebElement checkOutDate = driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        js.executeScript("arguments[0].removeAttribute('readonly');", checkInDate);
        checkOutDate.clear();
        checkOutDate.sendKeys(checkoutDate);
        delay(1000);

        // Chọn số lượng khách
       WebElement guestBox = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[4]/div/div/div/a"));
       guestBox.click();
       // Số phòng
       WebElement roomNum = driver.findElement(By.xpath("//*[@id=\"hotels_rooms\"]"));
       roomNum.clear();
       delay(1000);
       roomNum.sendKeys(rooms);
       // Khách người lón
       WebElement adultsNum = driver.findElement(By.xpath("//*[@id=\"hotels_adults\"]"));
       adultsNum.clear();
       delay(1000);
       adultsNum.sendKeys(adults);
       // Khách trẻ em
       WebElement childsNum = driver.findElement(By.xpath("//*[@id=\"hotels_childs\"]"));
       childsNum.clear();
       delay(1000);
       childsNum.sendKeys(childs);

       // Nhấn tìm kiếm
       WebElement findBtn = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[2]/form/div/div[5]/button"));
       findBtn.click();
       delay(1000);

       delay(1000);
       driver.navigate().back();
    }

    
}