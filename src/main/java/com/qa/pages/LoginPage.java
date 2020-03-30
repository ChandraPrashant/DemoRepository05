package com.qa.pages;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginPage {

	@BeforeSuite
	public void openSystem() {
		System.out.println("openSystem");
	}

	@BeforeTest
	public void lunchBrowser() {
		System.out.println("lunchBrowser");
	}

	@BeforeClass
	public void EnterURL() {
		System.out.println("EnterURL");
	}

	@BeforeMethod
	public void Login() {
		System.out.println("Login");
	}

	@Test(groups="Smoke")
	public void compose() {
		System.out.println("Login Compose");
	}


	@AfterSuite
	public void shutdown() {
		System.out.println("shutdown");
	}

	@AfterTest
	public void CloseBrowser() {
		System.out.println("CloseBrowser");
	}

	@AfterClass
	public void closeTab() {
		System.out.println("LoginPagecloseTab");
	}

	@AfterMethod
	public void logout() {
		System.out.println("LoginPagelogout");
	}

}
