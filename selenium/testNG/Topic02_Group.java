package testNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Topic02_Group {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	
	@Parameters({"browser","url"})
	@BeforeClass(alwaysRun=true)
	public void beforeClass(String browserName, String url) {
		switch (browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			driver = new FirefoxDriver();
			break;
			
		case "chrome":
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			driver = new ChromeDriver();
			break;
			
			default:
				new RuntimeException("Please select the browser");
			break;
		}
		driver.get(url);
	}

	@Test(groups = "admin")
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
	
	@AfterClass//(alwaysRun=true)
	public void afterClass() {
		System.out.println("after Class");
		driver.quit();		
	}
}
