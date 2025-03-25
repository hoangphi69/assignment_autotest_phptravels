package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import base.BaseTest;
import base.JsonReader;
import pages.Homepage;

public class F09_ChangeLanguage extends BaseTest {
  private String[] inputs;
  private Homepage page;

  // TC01: Change language to Turkish
  @Test
  public void TC01_ChangeToTurkish() {
    inputs = getInputs("TC01");
    testChangeLanguage(inputs[0], "TC01");
  }

  // TC02: Change language to Russian
  @Test
  public void TC02_ChangeToRussian() {
    inputs = getInputs("TC02");
    testChangeLanguage(inputs[0], "TC02");
  }

  // TC03: Change language to French
  @Test
  public void TC03_ChangeToFrench() {
    inputs = getInputs("TC03");
    testChangeLanguage(inputs[0], "TC03");
  }

  // TC04: Change language to Chinese
  @Test
  public void TC04_ChangeToChinese() {
    inputs = getInputs("TC04");
    testChangeLanguage(inputs[0], "TC04");
  }

  // TC05: Change language to German
  @Test
  public void TC05_ChangeToGerman() {
    inputs = getInputs("TC05");
    testChangeLanguage(inputs[0], "TC05");
  }

  // TC06: Change language to English
  @Test
  public void TC06_ChangeToEnglish() {
    inputs = getInputs("TC06");
    testChangeLanguage(inputs[0], "TC06");
  }

  // TC07: Change language to Arabic
  @Test
  public void TC07_ChangeToArabic() {
    inputs = getInputs("TC07");
    testChangeLanguage(inputs[0], "TC07");
  }

  public void testChangeLanguage(String language, String key) {
    page.performChangeLanguage(language);

    String expected = getOutputs(key)[0];
    String actual = driver.findElement(By.xpath("/html/body/main/div[1]/div[2]/div[1]/h4/strong")).getText();

    Assert.assertEquals(actual, expected);
  }

  public String[] getInputs(String key) {
    JsonNode data = JsonReader.getTestData("change-language-test-data.json", key).get("input");
    return new String[] {
        data.get("language").asText()
    };
  }

  public String[] getOutputs(String key) {
    JsonNode data = JsonReader.getTestData("change-language-test-data.json", key).get("output");
    return new String[] {
        data.get("example").asText()
    };
  }

  @BeforeMethod
  public void construct() {
    page = new Homepage(driver);
  }

  @AfterMethod
  public void navigateBack() {
    delay(1000);
    driver.manage().deleteAllCookies();
    driver.get(BASE_URL);
  }
}
