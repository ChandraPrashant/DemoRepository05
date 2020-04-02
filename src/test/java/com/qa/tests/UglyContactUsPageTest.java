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

	@Test
	public static void submitForm() throws Exception {

		prop = ReadPropertyFile
				.readPropertiesFile(System.getProperty("user.dir") + "/src/main/resources/Global.properties");

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");

		ReadExcelData reader = new ReadExcelData(
				System.getProperty("user.dir") + "/src/main/resources/ContactUsData.xlsx");

		int rowCount = reader.getRowCount("ContactUs");

		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {

			// WebDriver Code
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(prop.getProperty("uglys.contactus.url"));
			driver.navigate().refresh();

			// Parameterization
			String firstname = reader.getCellData("ContactUs", "FirstName", rowNum);
			String lastname = reader.getCellData("ContactUs", "LastName", rowNum);
			String jobtitle = reader.getCellData("ContactUs", "JobTitle", rowNum);
			String companyname = reader.getCellData("ContactUs", "CompanyName", rowNum);
			String email = reader.getCellData("ContactUs", "Email", rowNum);
			String phonenumber = reader.getCellData("ContactUs", "PhoneNumber", rowNum);

			// WebElements Code
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.firstname"))).sendKeys(firstname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.lastname"))).sendKeys(lastname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.jobtitle"))).sendKeys(jobtitle);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.companyname"))).sendKeys(companyname);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.email"))).sendKeys(email);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.phonenumber"))).sendKeys(phonenumber);
			Thread.sleep(3000);
			driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.submitbutton"))).click();

			if (driver.getPageSource().contains("Thanks for your request. We'll be in touch soon")) {
				System.out.println("ContactUs From Submitted for " + rowNum);
			} else {
				System.out.println("ContactUs From Not Submitted for " + rowNum);
			}
			driver.quit();
		}
	}
}
