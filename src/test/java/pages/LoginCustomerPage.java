package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginCustomerPage {
  private WebDriver driver;

  public By EMAIL_INPUT = By.id("email");

  public By PASSWORD_INPUT = By.id("password");

  public By SUBMIT_BUTTON = By.id("submitBTN");

  public By ERROR_MESSAGE = By.cssSelector(".vt-card.error");

  // Constructor
  public LoginCustomerPage(WebDriver driver) {
    this.driver = driver;
  }

  // Nhập email
  public void enterEmail(String email) {
    WebElement emailInput = driver.findElement(EMAIL_INPUT);
    emailInput.clear();
    emailInput.sendKeys(email);
  }

  // Nhập mật khẩu
  public void enterPassword(String password) {
    WebElement passwordInput = driver.findElement(PASSWORD_INPUT);
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }

  // Nhấn nút đăng nhập
  public void clickSubmitButton() {
    WebElement submitBtn = driver.findElement(SUBMIT_BUTTON);
    submitBtn.submit();
  }

  // Thực hiện đăng nhập
  public void performLogin(String email, String password) {
    enterEmail(email);
    enterPassword(password);
    clickSubmitButton();
  }

  public void performLogin(String... data) {
    performLogin(data[0], data[1]);
  }
}
