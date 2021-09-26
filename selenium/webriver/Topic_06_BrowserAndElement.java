package webriver;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_BrowserAndElement {
	WebDriver driver;
	String projectPath=System.getProperty("user.dir");
	By txtEmail= By.id("email");
	By txtUsername= By.id("new_username");
	By txtPassword= By.id("new_password");
	By btnSignUp= By.id("create-account");
	//By chbNewsLetter= By.xpath("//input[@id='marketing_newsletter']");
	By chbNewsLetter= By.id("marketing_newsletter");
	
	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://login.mailchimp.com/signup/");
	}
	@Test
	public void tc04RegisterMailChip() {
		//driver.findElement(txtEmail).sendKeys("user1@gmail.com");
		sendkeyElement(txtEmail, "user1@gmail.com");
		sendkeyElement(txtUsername, "user1");
		//number
		sendkeyElement(txtPassword, "123");
		Assert.assertTrue(driver.findElement(By.cssSelector(".number-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(btnSignUp).isEnabled());
		//Lowercase
		driver.findElement(txtPassword).clear();
		sendkeyElement(txtPassword, "user");
		Assert.assertTrue(driver.findElement(By.cssSelector(".lowercase-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(btnSignUp).isEnabled());
		//Uppercase
		driver.findElement(txtPassword).clear();
		sendkeyElement(txtPassword, "USER");
		Assert.assertTrue(driver.findElement(By.cssSelector(".uppercase-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(btnSignUp).isEnabled());
		//special
		driver.findElement(txtPassword).clear();
		sendkeyElement(txtPassword, "!@#%^^");
		Assert.assertTrue(driver.findElement(By.cssSelector(".special-char.completed")).isDisplayed());
		Assert.assertFalse(driver.findElement(btnSignUp).isEnabled());
		//more than 8 characters
		driver.findElement(txtPassword).clear();
		sendkeyElement(txtPassword, "usertesting");
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(btnSignUp).isEnabled());
		//full valid data
		driver.findElement(txtPassword).clear();
		sendkeyElement(txtPassword, "User123!");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");
		//js.executeScript("arguments[0].scrollIntoView();", chbNewsLetter);
		driver.findElement(chbNewsLetter).click();
		Assert.assertTrue(driver.findElement(chbNewsLetter).isSelected());
	}
	
	public void sendkeyElement(By by, String value) {
		WebElement element = driver.findElement(by);
		element.sendKeys(value);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
