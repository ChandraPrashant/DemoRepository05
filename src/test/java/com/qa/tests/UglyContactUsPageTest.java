package com.qa.tests;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import com.qa.utils.ReadPropertyFile;

public class UglyContactUsPageTest extends ReadPropertyFile {


	static FileInputStream fis = null;
	static XSSFWorkbook wb = null;
	static XSSFSheet sheet = null;
	static XSSFRow currentRow = null;
	static WebDriver driver;
	static Properties prop = null;

	@Test
	public static void submitForm() throws Exception {
		
		prop = ReadPropertyFile.readPropertiesFile(System.getProperty("user.dir") +"/src/main/resources/Global.properties");

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");

		fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/ContactUsData.xlsx");

		wb = new XSSFWorkbook(fis);

		sheet = wb.getSheet(prop.getProperty("sheetname"));

		int rowCount = sheet.getLastRowNum();

		int colCount = sheet.getRow(0).getLastCellNum();

		for (int i = 1; i < rowCount; i++) {

			currentRow = sheet.getRow(i);

			for (int j = 0; j < colCount; j++) {
				
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(prop.getProperty("uglys.contactus.url"));
				driver.navigate().refresh();
				String firstname = currentRow.getCell(0).toString();
				String lastname = currentRow.getCell(1).toString();
				String jobtitle = currentRow.getCell(2).toString();
				String companyname = currentRow.getCell(3).toString();
				String email = currentRow.getCell(4).toString();
				String phonenumber = currentRow.getCell(5).toString();

				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.firstname"))).sendKeys(firstname);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.lastname"))).sendKeys(lastname);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.jobtitle"))).sendKeys(jobtitle);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.companyname")))
						.sendKeys(companyname);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.email"))).sendKeys(email);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.phonenumber")))
						.sendKeys(phonenumber);
				Thread.sleep(3000);
				driver.findElement(By.cssSelector(prop.getProperty("uglys.contactus.submitbutton"))).click();
				
				if(driver.getPageSource().contains("Thanks for your request. We'll be in touch soon")) {
					System.out.println("ContactUs From Submitted for " + currentRow);
				}else {
					System.out.println("ContactUs From Not Submitted for " + currentRow);
				}	
				driver.quit();
				break;
			}
		}
	}

}