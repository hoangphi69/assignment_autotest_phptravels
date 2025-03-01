package Test_Script;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F03 extends BaseTest{
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    // TC01: nhập đúng trường thông tin
    @Test
    public void TC01_Correct() throws InterruptedException {
        perform_testTours("Tokyo", "4", "0");
        
    }

    // TC02: Nhập sai địa điểm (tham số location lại trả về kết quả trước đó khi nhập sai tên)
    @Test
    public void TC02_IncorrectLocation() throws InterruptedException {
        perform_testTours("GitGud", "2", "5");
        WebElement noResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/span/span/span[2]/ul/li")));
        String noResultMessage = noResult.getText();
        System.out.println("Thông báo lỗi TC02:" + noResultMessage);
    }

    // TC03: Không nhập địa điểm (tham số location lại trả về kết quả trước đó khi không nhập địa điểm)
    @Test
    public void TC03_LocationBlank() throws InterruptedException {
        perform_testTours("", "1","4");
    }

    // TC04: Nhâp số lượng khách người lớn là số âm
    @Test
    public void TC04_AdultsNumBelow() throws InterruptedException {
        perform_testTours("Tokyo", "-5", "0");
        
    }

    // TC05: Bỏ trống lượng khách người lớn 
    @Test
    public void TC05_AdultsNumBlank() throws InterruptedException {
        perform_testTours("Tokyo", "", "0");
        
    }
    
    // TC06: Nhâp số lượng khách trẻ em là số âm
    @Test
    public void TC06_ChildNumBelow() throws InterruptedException {
        perform_testTours("Tokyo", "1", "-5");
        
    }

    // TC07: Bỏ trống lượng khách trẻ em
    @Test
    public void TC07_ChildNumBlank() throws InterruptedException {
        perform_testTours("Tokyo", "5", "");
        
    }
    public void perform_testTours(String location, String adults, String childs) throws InterruptedException {

        // Chọn Tours
        WebElement tours = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[3]/button"));
        tours.click();
        
        // Chọn địa điểm Tours
        WebElement locationField = driver.findElement(By.xpath("//*[@id=\"select2-tours_city-container\"]"));
        locationField.click();
        delay(500);

        
        if (!location.isEmpty()) { // Nhập input 

            // Nhập địa điểm Tours
            WebElement locationInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
            locationInput.sendKeys(location);
            delay(500);

            // Nhấn Enter
            locationInput.sendKeys(Keys.ENTER);
            delay(500);
        } else { // Không nhập input
            System.out.println("Thông báo lỗi TC03: Vui lòng chọn địa điểm");
        }

        // Chọn số lượng khách
        WebElement guestBox = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[3]/div/div/div/a"));
        guestBox.click();
        // Khách người lón
        WebElement adultsNum = driver.findElement(By.xpath("//*[@id=\"tours_adults\"]"));
        adultsNum.clear();
        delay(500);
        adultsNum.sendKeys(adults);
        // Khách trẻ em
        WebElement childsNum = driver.findElement(By.xpath("//*[@id=\"tours_child\"]"));
        childsNum.clear();
        delay(500);
        childsNum.sendKeys(childs);

        // // Chọn thời gian đi
        // WebElement departDateDropDown = driver.findElement(By.xpath("//*[@id=\"date\"]"));
        // departDateDropDown.click();
        // // >>>ERROR<<<
        // WebElement departDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[8]")));
        // WebElement departDateSelect = departDateInput.findElement(By.xpath("/html/body/div[8]/div[1]/table/tbody/tr[3]/td[4]"));
        // departDateSelect.click();

        // Nhấn tìm kiếm
        WebElement findBtn = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[3]/form/div/div[4]/button"));
        findBtn.click();
        delay(1000);

        driver.navigate().back();
        delay(1000);

    }
}
