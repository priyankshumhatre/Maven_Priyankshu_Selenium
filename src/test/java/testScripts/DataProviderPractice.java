package testScripts;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.ExcelManagementUtilities;

public class DataProviderPractice {

	WebDriver driver;

	@BeforeClass
	void initialSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("http://automationbykrishna.com/");
		driver.findElement(By.linkText("Registration")).click();
	}

	@DataProvider
	public Object[][] getLoginTestData() throws RuntimeException, IOException {
		Object[][] data = ExcelManagementUtilities.getExcelSheetData(
				"D:\\Technocredits - Java_Selenium\\Workspace\\Maven_Selenium_Priyankshu\\src\\test\\resources\\LoginData.xlsx",
				"dataSheet");
		return data;
	}

	@Test(dataProvider = "getLoginTestData")
	void loginTest(String userName, String password, String expectedMessage) {
		boolean testPass = false;
		// Enter User Name
		WebElement userNameField = driver.findElement(By.id("unameSignin"));
		userNameField.clear();
		userNameField.sendKeys(userName);

		// Enter Password
		WebElement passwordField = driver.findElement(By.id("pwdSignin"));
		passwordField.clear();
		passwordField.sendKeys(password);

		// Click on Button
		WebElement loginButton = driver.findElement(By.id("btnsubmitdetails"));
		loginButton.click();

		Alert loginAlert = driver.switchTo().alert();
		String alertText = loginAlert.getText();
		if ((password.length() <= 8 && alertText.equals(expectedMessage))
				|| (password.length() > 8 && alertText.equals(expectedMessage))) {
			testPass = true;
		}
		loginAlert.dismiss();
		Assert.assertTrue(testPass);
	}
	
	@AfterClass
	void closeBrowser() {
		driver.quit();
	}
}
