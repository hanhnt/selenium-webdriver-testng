package webriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic14_Windows {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//		driver = new FirefoxDriver();
		// Chrome
				System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
				driver = new ChromeDriver();
		
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();		
	}
	
	public void TC01_window() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		String parentID=driver.getWindowHandle();
		
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
				
		switchWindows(parentID);
		String googlePageID=driver.getWindowHandle();
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.findElement(By.name("q")).sendKeys("selenium");
		driver.findElement(By.name("btnK")).click();
		
		switchWindows(googlePageID);
		sleepInSecond(5);
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		String fbPageID=driver.getWindowHandle();
		switchWindows(parentID);
		Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
	}
	@Test
	public void TC02_switchWindowByTitle() {
		String urlPage;
		driver.get("https://automationfc.github.io/basic-form/index.html");		
		String parentPage=driver.getWindowHandle();
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		switchWindowByTitle("Google");
		 urlPage= driver.getCurrentUrl();
		Assert.assertEquals("https://www.google.com.vn/", urlPage);
		driver.findElement(By.name("q")).sendKeys("selenium");
		driver.findElement(By.name("btnK")).click();
		
		switchWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		
		switchWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		urlPage=driver.getCurrentUrl();
		Assert.assertEquals("https://tiki.vn/", urlPage);
		closeAllWindowWithoutParentWindow(parentPage);
	}
	
	
	public void switchWindows(String windowID) {
		Set<String> allWindows=driver.getWindowHandles();
		for (String windows: allWindows) {
			//neu window = voi parent thi khong switch
			if(!windows.equals(windowID)) {
				driver.switchTo().window(windows);
			} 
		}
	}
	
	public void closeAllWindowWithoutParentWindow(String parentWindow) {
		Set<String> allWindow=driver.getWindowHandles();
		for (String window: allWindow) {
			if(!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}
	
	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void switchWindowByTitle(String expectedPageTitle) {
		Set<String> allWindows=driver.getWindowHandles();
		for (String window: allWindows) {
			driver.switchTo().window(window);
			
			String actualTitle=driver.getTitle();
			if(actualTitle.equals(expectedPageTitle)) {break;}
			
		}
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
