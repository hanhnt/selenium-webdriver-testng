package webriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic13_FrameAndIframe {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Select select;
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
	
	public void TC01_iFrame() {
		String name="John";
		String phone="0123456789";
		driver.get("https://kyna.vn/");
		sleepInSecond(5);
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));		
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "168K likes");
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
		driver.findElement(By.cssSelector("div.border_overlay")).click();
		driver.findElement(By.cssSelector("input.input_name")).sendKeys(name);
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys(phone);
		select =new Select (driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Java course");
		driver.findElement(By.cssSelector("input.submit")).click();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.floater_inner_seriously>label.logged_in_name")).getText(), name);
		Assert.assertEquals(driver.findElement(By.cssSelector("label.logged_in_phone")).getText(), phone);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.className("search-button")).click();
		List<WebElement> postTitles= driver.findElements(By.cssSelector("div.content h4"));
		for (WebElement postTitle: postTitles) {
			Assert.assertTrue(postTitle.getText().contains("Excel"));
		}
	}
	@Test
	public void TC02_frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name='login_page']")));
		driver.findElement(By.cssSelector("input.text-muted")).sendKeys("automation");		
		driver.findElement(By.cssSelector("a.login-btn")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("input[name='fldPassword']")).isDisplayed());
		driver.findElement(By.xpath("//div[@class='footer-btm']/a[text()='Terms and Conditions']")).click();
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
