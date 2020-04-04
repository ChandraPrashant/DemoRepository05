package com.qa.tests;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import com.qa.utils.ReadExcelData;
import com.qa.utils.ReadPropertyFile;

public class FreeCRMAccountPageTest extends ReadPropertyFile {

	static WebDriver driver;
	static Properties prop = null;
	static String proppath = "/src/main/resources/Global.properties";

	@Test(groups = "Smoke")
	public void login() throws Exception {

		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("driverpath"));

		ReadExcelData reader = new ReadExcelData(System.getProperty("user.dir") + prop.getProperty("filepath"));

		int rowCount = reader.getRowCount(prop.getProperty("freecrmsheetname"));

		reader.addColumn(prop.getProperty("freecrmsheetname"), "Status");

		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {

			// WebDriver Code
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.get(prop.getProperty("freecrm.myaccount.url"));

			// Parameterization
			String username = reader.getCellData(prop.getProperty("freecrmsheetname"), "UserName", rowNum);
			String password = reader.getCellData(prop.getProperty("freecrmsheetname"), "PassWord", rowNum);

			// WebElements Code
			driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.username"))).sendKeys(username);
			driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.password"))).sendKeys(password);
			Thread.sleep(1000);
			driver.findElement(By.cssSelector(prop.getProperty("freecrm.myaccount.login"))).click();
			Thread.sleep(2000);

			// Verification Code
			try {
				String status = driver.findElement(By.xpath("//span[contains(text(),'Prashant Chandra')]")).getText();
				if (status.contains("Prashant")) {
					System.out.println("Login happened successfully for " + rowNum);
					reader.setCellData(prop.getProperty("freecrmsheetname"), "Status", rowNum, "Pass");
				}
			} catch (Exception e) {
				e.getMessage();
				System.out.println("Login not happened for " + rowNum);
				reader.setCellData(prop.getProperty("freecrmsheetname"), "Status", rowNum, "Fail");
			}

			Thread.sleep(1000);
			driver.quit();
		}
	}
}
