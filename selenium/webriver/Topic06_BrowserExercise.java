package webriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic06_BrowserExercise {
	WebDriver driver;
	String projectPath=System.getProperty("user.dir");

	
	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://live.demoguru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}
	
	@Test
	public void tc01VerifyUrl() {
		String loginUrl=driver.getCurrentUrl();
		Assert.assertEquals(loginUrl, "http://live.demoguru99.com/index.php/customer/account/login/");
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String createUrl=driver.getCurrentUrl();
		Assert.assertEquals(createUrl, "http://live.demoguru99.com/index.php/customer/account/create/");
	}
	
	@Test
	public void tc02VerifyTitle() {
		String loginTitle= driver.getTitle();
		Assert.assertEquals(loginTitle, "Customer Login");
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String createTitle= driver.getTitle();
		Assert.assertEquals(createTitle, "Create New Customer Account");
	}
	
	@Test
	public void tc03NavigateFunction() {
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String createUrl=driver.getCurrentUrl();
		Assert.assertEquals(createUrl, "http://live.demoguru99.com/index.php/customer/account/create/");
		driver.navigate().back();
		String loginUrl=driver.getCurrentUrl();
		Assert.assertEquals(loginUrl, "http://live.demoguru99.com/index.php/customer/account/login/");
		driver.navigate().forward();
		String createTitle= driver.getTitle();
		Assert.assertEquals(createTitle, "Create New Customer Account");
	}
	
	@Test
	public void tc04GetPageSourceCode() {
		String loginPageSourceCode=driver.getPageSource();
		Assert.assertTrue(loginPageSourceCode.contains("Login or Create an Account"));
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String createPageSourceCode=driver.getPageSource();
		Assert.assertTrue(createPageSourceCode.contains("Create an Account"));
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
