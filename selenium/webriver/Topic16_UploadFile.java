package webriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic16_UploadFile {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String pink="pink.jpeg";
	String red="red.jpeg";
	String white="white.jpeg";
	
	String pinkPath=projectPath+"/uploadFile/"+pink;
	String redPath=projectPath+"/uploadFile/"+red;
	String whitePath=projectPath+"/uploadFile/"+white;
	
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
	
	
	public void TC01_uploadFile() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		driver.findElement(By.name("files[]")).sendKeys(pinkPath);
		driver.findElement(By.name("files[]")).sendKeys(redPath);
		driver.findElement(By.name("files[]")).sendKeys(whitePath);
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+pink+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+red+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+white+"']")).isDisplayed());
		
		List<WebElement> buttons= driver.findElements(By.cssSelector("tr button.start"));
		for (WebElement button: buttons ) {
			button.click();
		}
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+pink+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+red+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+white+"']")).isDisplayed());
	}
	
	@Test
	public void TC02_uploadMultipleFile() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		driver.findElement(By.name("files[]")).sendKeys(pinkPath +"\n"+ redPath +"\n"+ whitePath);
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+pink+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+red+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='"+white+"']")).isDisplayed());
		
		List<WebElement> buttons= driver.findElements(By.cssSelector("tr button.start"));
		for (WebElement button: buttons ) {
			button.click();
		}
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+pink+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+red+"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p[class='name']>a[title='"+white+"']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
