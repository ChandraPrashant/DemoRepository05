package com.qa.tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.Logger;


public class LoginTest {
	
	WebDriver driver;
	Logger log = Logger.getLogger(LoginTest.class);
	
	
	@BeforeMethod
	public void setup() throws InterruptedException{
		log.info("****************************** Starting test cases execution  *****************************************");

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");	
		driver = new ChromeDriver(); 
		log.info("launching chrome broswer");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
		driver.get("https://www.freecrm.com/");
		Thread.sleep(3000);
		log.info("entering application URL");
		log.warn("Hey this just a warning message");
		log.fatal("hey this is just fatal error message");
		log.debug("this is debug message");
	}
	

	@AfterMethod
	public void tearDown(){
		driver.quit();
		log.info("****************************** Browser is closed *****************************************");

		
	}

}
