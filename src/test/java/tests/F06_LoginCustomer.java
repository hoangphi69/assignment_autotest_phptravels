package tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.JsonReader;
import pages.LoginCustomerPage;

public class F06_LoginCustomer extends BaseTest {
  private String[] inputs;
  private LoginCustomerPage page;

  // TC01: Đăng nhập với thông tin hợp lệ
  @Test
  public void TC01_ValidLogin() {
    inputs = getInputs("TC01");
    page.performLogin(inputs);

    delay(2000);

    // Kiểm tra trang được chuyển tiếp
    String actual = driver.getCurrentUrl();
    String expected = getOutput("TC01");
    Assert.assertEquals(actual, expected);
  }

  // TC02: Đăng nhập với email không hợp lệ
  @Test
  public void TC02_InvalidEmail() {
    inputs = getInputs("TC02");
    page.performLogin(inputs);

    // Kiểm tra thông báo lỗi
    WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(page.ERROR_MESSAGE));
    String actual = alert.getText();
    String expected = getOutput("TC02");
    Assert.assertEquals(actual, expected);
  }

  // TC03: Đăng nhập với mật khẩu không hợp lệ
  @Test
  public void TC03_InvalidPassword() {
    inputs = getInputs("TC03");
    page.performLogin(inputs);

    // Kiểm tra thông báo lỗi
    WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(page.ERROR_MESSAGE));
    String actual = alert.getText();
    String expected = getOutput("TC03");
    Assert.assertEquals(actual, expected);
  }

  // TC04: Đăng nhập với email trống
  @Test
  public void TC04_BlankEmail() {
    inputs = getInputs("TC04");
    page.performLogin(inputs);

    // Kiểm tra thông báo lỗi
    WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(page.ERROR_MESSAGE));
    String actual = alert.getText();
    String expected = getOutput("TC04");
    Assert.assertEquals(actual, expected);
  }

  // TC05: Đăng nhập với mật khẩu trống
  @Test
  public void TC05_BlankPassword() {
    inputs = getInputs("TC05");
    page.performLogin(inputs);

    // Kiểm tra thông báo lỗi
    WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(page.ERROR_MESSAGE));
    String actual = alert.getText();
    String expected = getOutput("TC05");
    Assert.assertEquals(actual, expected);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("login-customer-test-data.json", key).get("input");
    return new String[] {
        data.get("email").asText(),
        data.get("password").asText()
    };
  }

  public String getOutput(String key) {
    JsonNode data = JsonReader.getTestData("login-customer-test-data.json", key).get("output");
    return data.asText();
  }

  @BeforeClass
  public void navigateToPage() {
    driver.get(BASE_URL + "login");
  }

  @BeforeMethod
  public void construct() {
    page = new LoginCustomerPage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(2000);
    driver.manage().deleteAllCookies();
    driver.get(BASE_URL + "login");
  }
}
