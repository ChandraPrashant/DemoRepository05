package com.qa.tests;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.Test;
import com.qa.utils.ReadExcelData;
import com.qa.utils.ReadPropertyFile;

public class UglyContactUsPageTest extends ReadPropertyFile {

	static WebDriver driver;
	static Properties prop = null;
	static String proppath = "/src/main/resources/Global.properties";

	@Test(groups = "Smoke")
	public void submitForm() throws Exception {
		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") + proppath);

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("driverpath"));
		// WebDriverManager.chromedriver().setup();
		
		ReadExcelData reader = new ReadExcelData(System.getProperty("user.dir") + prop.getProperty("filepath"));

		int rowCount = reader.getRowCount(prop.getProperty("uglysheetname"));
		
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			// WebDriver Code
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(prop.getProperty("uglys.contactus.url"));
			driver.navigate().refresh();
			
			// Parameterization
			String firstname = reader.getCellData(prop.getProperty("uglysheetname"), "FirstName", rowNum);
			String lastname = reader.getCellData(prop.getProperty("uglysheetname"), "LastName", rowNum);
			String jobtitle = reader.getCellData(prop.getProperty("uglysheetname"), "JobTitle", rowNum);
			String companyname = reader.getCellData(prop.getProperty("uglysheetname"), "CompanyName", rowNum);
			String email = reader.getCellData(prop.getProperty("uglysheetname"), "Email", rowNum);
			String phonenumber = reader.getCellData(prop.getProperty("uglysheetname"), "PhoneNumber", rowNum);

			// WebElements Code
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.firstname"))).sendKeys(firstname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.lastname"))).sendKeys(lastname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.jobtitle"))).sendKeys(jobtitle);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.companyname"))).sendKeys(companyname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.email"))).sendKeys(email);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.phonenumber"))).sendKeys(phonenumber);
			Thread.sleep(1000);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.submitbutton"))).click();
			Thread.sleep(2000);

			// Verification Code
			String status = driver.findElement(By.xpath("//p[contains(text(),'Thanks for your request.')]")).getText();
			if (status.contains("Thanks for your request. We'll be in touch soon.")) {
				System.out.println("ContactUs From Submitted for " + rowNum);
			} else {
				System.out.println("ContactUs From Not Submitted for " + rowNum);
			}

			Thread.sleep(1000);
			driver.quit();
		}

	}
}
