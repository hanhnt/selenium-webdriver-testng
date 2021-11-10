package testNG;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Topic01_Annotation {	
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("before Class");
	}

	@Test
	public void TC_01_CreateUser() {
		System.out.println("Create User");
	}

	@Test
	public void TC_02_EditUser() {
		System.out.println("Edit User");
	}

	@Test
	public void TC_02_DeleteUser() {
		System.out.println("Delete User");
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("before Method");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("after Method");
	}
	
	
	@AfterClass(alwaysRun=true)
	public void afterClass() {
		System.out.println("after Class");
		
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("before Test");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("after Test");
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("before Suite");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("after Suite");
	}

}
