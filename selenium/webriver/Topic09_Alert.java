package webriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic09_Alert {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	Alert alert;

	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();		
		// Chrome
//		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
//		driver = new ChromeDriver();

		// wait to find element
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// wait to apply for status of elements: visible, invisible, present
		explicitWait = new WebDriverWait(driver, 15);
		driver.manage().window().maximize();
	}
	@Test
	public void acceptAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		explicitWait.until(ExpectedConditions.alertIsPresent());
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(),
				"You clicked an alert successfully");

	}
	@Test
	public void confirmAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Cancel");
	}
	@Test
	public void promptAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys("nguyenthihanh");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: nguyenthihanh");
	}
	@Test
	public void authenticationAlert() {
		String username = "admin";
		String password = "admin";
		String url = "http://the-internet.herokuapp.com/basic_auth";
		String links[] = url.split("//");
		String link = links[0] + "//" + username + ":" + password + "@" + links[1];
		driver.get(link);
		// alert=explicitWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.example p")).getText(),
				"Congratulations! You must have the proper credentials.");
	}

	public String getUrl(String username, String password, String url) {
		// http://username:password@the-internet.herokuapp.com/basic_auth

		String links[] = url.split("//");
		return links[0] + username + ":" + password + "@" + links[1];
	}

	@Test
	public void authenWay2() {
		String username = "admin";
		String password = "admin";
		String url = "http://the-internet.herokuapp.com/basic_auth";
		driver.get(getUrl(username, password, url));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.example p")).getText(),
				"Congratulations! You must have the proper credentials.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
