package com.qa.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.utils.ReadPropertyFile;
import com.test.utility.TestUtil;

public class FreeCRMAccountPageTest extends ReadPropertyFile {

	static WebDriver driver;
	static Properties prop = null;
	static String proppath = "/src/main/resources/Global.properties";

	@BeforeMethod
	public void setup() throws Exception {

		// Property Path Code
		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		// WebDriver Code
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("driverpath"));

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(prop.getProperty("freecrm.myaccount.url"));
		Thread.sleep(2000);
		
	}

	@DataProvider
	public Iterator<Object[]> getTestData() throws Exception {

		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		ArrayList<Object[]> testData = TestUtil.getDataFromExcel(prop.getProperty("freecrmsheetname"));
		return testData.iterator();
	}

	@Test(dataProvider = "getTestData",groups = "Smoke")
	public void login(String username, String password) throws Exception {

		// WebElements Code
		driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.username"))).sendKeys(username);
		driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.password"))).sendKeys(password);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.login"))).click();
		Thread.sleep(1000);

		// Verification Code
		try {
			String status = driver.findElement(By.xpath("//span[contains(text(),'Prashant Chandra')]")).getText();
			if (status.contains("Prashant")) {
				System.out.println("Login happened successfully for " + username + " | " + password);
			}
		} catch (Exception e) {
			e.getMessage();
			System.out.println("Login not happened for " + username + " | " + password);
		}

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
