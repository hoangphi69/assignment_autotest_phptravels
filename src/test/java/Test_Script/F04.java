package Test_Script;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import utils.BaseTest;

public class F04 extends BaseTest{

    // TC01: Nhập chính xác trường thông tin
    @Test
    public void TC01_ValidInput() throws  InterruptedException {
        perform_testCar("Tokyo", "Tokyo", "03:00", "03:00");
    }

    // TC02: Nhập sai địa điểm đón (tham số location lại trả về kết quả trước đó khi nhập sai tên)
    @Test
    public void TC02_IncorrectLocation1() throws  InterruptedException {
        perform_testCar("GitGud", "Tokyo", "03:00", "03:00");
    }

    // TC03: Bỏ trống địa điểm đón (tham số location lại trả về kết quả trước đó khi không nhập địa điểm)
    @Test
    public void TC03_IncorrectLocation2() throws  InterruptedException {
        perform_testCar("", "Tokyo", "03:00", "03:00");
    }

    // TC04: Nhập sai địa điểm trả khách (tham số location lại trả về kết quả trước đó khi nhập sai tên)
    @Test
    public void TC04_IncorrectLocation3() throws  InterruptedException {
        perform_testCar("Tokyo", "GitGud", "03:00", "03:00");
    }

    // TC05: Bỏ trống địa điểm trả khách (tham số location lại trả về kết quả trước đó khi không nhập địa điểm)
    @Test
    public void TC05_IncorrectLocation4() throws  InterruptedException {
        perform_testCar("Tokyo", "", "03:00", "03:00");
    }

    // TC06: Bỏ trống cả 2 địa điểm (tham số location lại trả về kết quả trước đó khi không nhập địa điểm)
    @Test
    public void TC06_IncorrectLocation5() throws  InterruptedException {
        perform_testCar("", "", "03:00", "03:00");
    }

    // TC07: Bỏ trống thời gian đón
    @Test
    public void TC07_PickUpTimeBlank() throws  InterruptedException {
        perform_testCar("Tokyo", "Tokyo", "", "03:00");
    }

    public void perform_testCar(String locationBegin, String locationEnd, String pick_upTime, String drop_offTime) throws InterruptedException {

        // Chọn Cars
        WebElement tours = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/ul/li[4]/button"));
        tours.click();

        
        // >>>>>>>>>>>       Chọn điểm đón           <<<<<<<<<<<<<<<
        // Chọn địa điểm đón
        WebElement locationBeginField = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[1]/div[1]/div[2]/span/span[1]/span/span[1]/div"));
        locationBeginField.click();
        delay(500);

        
        if (!locationBegin.isEmpty()) { // Nhập input 

            // Nhập địa điểm đón
            WebElement locationBeginInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
            locationBeginInput.sendKeys(locationBegin);
            delay(500);

            // Nhấn Enter
            locationBeginInput.sendKeys(Keys.ENTER);
            delay(500);
        } else { // Không nhập input
            System.out.println("Thông báo lỗi TC03: Vui lòng chọn địa điểm");
        }// >>>>>>>>>>>      Kết thúc 1 Field         <<<<<<<<<<<<<<<


        // >>>>>>>>>>>       Chọn điểm trả khách           <<<<<<<<<<<<<<<
        // Chọn địa điểm trả khách
        WebElement locationEndField = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[2]/div[1]/div[2]/span/span[1]/span"));
        locationEndField.click();
        delay(500);

        // Nhập địa điểm trả khách
        WebElement locationEndInput = driver.findElement(By.xpath("/html/body/span/span/span[1]/input"));
        locationEndInput.sendKeys(locationEnd);
        delay(500);

        // Nhấn Enter
        locationEndInput.sendKeys(Keys.ENTER);
        delay(500);
        // >>>>>>>>>>>       Kết thúc 1 Field          <<<<<<<<<<<<<<<


        // >>>>>>>>>>>       Start Pick-up Date/ Pick-up Time          <<<<<<<<<<<<<<<
        // //Pickup Date
        // WebElement pickUpDate = driver.findElement(By.xpath("//*[@id=\"cars_from_date\"]"));
        // pickUpDate.click();
        // delay(500);
        // WebElement pickDate = driver.findElement(By.xpath("/html/body/div[9]/div[1]/table/tbody/tr[2]/td[4]"));
        // pickDate.click();

        //Pickup Time
        WebElement pickupTime = driver.findElement(By.xpath("//*[@id=\"cars_from_time\"]"));
        pickupTime.click();
        Select pickTime = new Select(pickupTime);
        delay(1000);
        pickTime.selectByValue(pick_upTime);
        // >>>>>>>>>>>       End Pick-up Date/ Pick-up Time         <<<<<<<<<<<<<<<

        
        // >>>>>>>>>>>       Start Drop-off Date/ Drop-off Time          <<<<<<<<<<<<<<<
        // //Drop-off Date
        // WebElement dropOffDate = driver.findElement(By.xpath("//*[@id=\"cars_to_date\"]"));
        // dropOffDate.click();
        // delay(500);
        // WebElement dropDate = driver.findElement(By.xpath("/html/body/div[10]/div[1]/table/tbody/tr[2]/td[5]"));
        // dropDate.click();

        //Drop-off Time
        WebElement dropOffTime = driver.findElement(By.xpath("//*[@id=\"cars_to_time\"]"));
        dropOffTime.click();
        Select dropTime = new Select(dropOffTime);
        delay(1000);
        dropTime.selectByValue(drop_offTime);
        // >>>>>>>>>>>       EndDrop-off Date/ Drop-off Time         <<<<<<<<<<<<<<<

    

        // Chọn số lượng khách
        WebElement guestBox = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[5]/div/div/div/a"));
        guestBox.click();
        WebElement adultsAdd = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[5]/div/div/div/div/div[1]/div/div/div[2]"));
        adultsAdd.click();


        // Nhấn tìm kiếm
        WebElement findBtn = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[2]/div/div/div/div/div[4]/form/div/div[6]/button"));
        findBtn.click();
        delay(1000);

        driver.navigate().back();
        delay(1000);

    }
    
}
