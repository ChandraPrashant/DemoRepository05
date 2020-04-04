package com.qa.tests;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import com.qa.utils.ReadExcelData;
import com.qa.utils.ReadPropertyFile;

public class JBLMyAccountPageTest extends ReadPropertyFile {

	static WebDriver driver;
	static Properties prop = null;
	static String proppath = "/src/main/resources/Global.properties";

	@Test
	public void login() throws Exception {

		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("driverpath"));

		ReadExcelData reader = new ReadExcelData(System.getProperty("user.dir") + prop.getProperty("filepath"));

		int rowCount = reader.getRowCount(prop.getProperty("jblsheetname"));

		reader.addColumn(prop.getProperty("jblsheetname"), "Status");

		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {

			// WebDriver Code
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.get(prop.getProperty("jbl.myaccount.url"));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Parameterization
			String username = reader.getCellData(prop.getProperty("jblsheetname"), "UserName", rowNum);
			String password = reader.getCellData(prop.getProperty("jblsheetname"), "PassWord", rowNum);

			// WebElements Code
			driver.findElement(By.cssSelector(prop.getProperty("jbl.myaccount.username"))).sendKeys(username);
			driver.findElement(By.cssSelector(prop.getProperty("jbl.myaccount.password"))).sendKeys(password);
			Thread.sleep(1000);
			driver.findElement(By.cssSelector(prop.getProperty("jbl.myaccount.login"))).click();
			Thread.sleep(2000);

			// Verification Code
			String actual = driver.getTitle();
			if (actual.contains("Products | Jones & Bartlett Learning")) {
				System.out.println("Login happened successfully for " + rowNum);
				reader.setCellData(prop.getProperty("jblsheetname"), "Status", rowNum, "Pass");
			} else {
				System.out.println("Login not happened for " + rowNum);
				reader.setCellData(prop.getProperty("jblsheetname"), "Status", rowNum, "Fail");
			}

			Thread.sleep(1000);
			driver.quit();
		}
	}
}
