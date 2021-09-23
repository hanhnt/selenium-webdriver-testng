package webriver;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

public class Login {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	By btnLogin = By.name("send");
	By txtEmail = By.name("login[username]");
	By txtPassword = By.name("login[password]");
	By errorEmailMsg = By.id("advice-validate-email-email");
	By errorPassword = By.id("advice-validate-password-pass");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@BeforeMethod
	public void beforMethod() {
		driver.get("http://live.demoguru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}

	@Test
	public void loginWithEmptyEmailAndPass() {
		driver.findElement(btnLogin).click();
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
				"This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),
				"This is a required field.");
	}

	@Test
	public void loginWithInvalidEmail() {
		driver.findElement(txtEmail).sendKeys("1234@123123");
		driver.findElement(txtPassword).sendKeys("123456");
		driver.findElement(btnLogin).click();
		Assert.assertEquals(driver.findElement(errorEmailMsg).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void loginWithPasswordLessThan6() {
		// driver.findElement(txtEmail).clear();
		driver.findElement(txtEmail).sendKeys("automation@gmail.com");
		// driver.findElement(txtPassword).clear();
		driver.findElement(txtPassword).sendKeys("123");
		driver.findElement(btnLogin).click();
		Assert.assertEquals(driver.findElement(errorPassword).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void loginWithIncorrectEmailPassword() {
		driver.findElement(txtEmail).sendKeys("automation@gmail.com");
		// driver.findElement(txtPassword).clear();
		driver.findElement(txtPassword).sendKeys("123123123");
		driver.findElement(btnLogin).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(),
				"Invalid login or password.");
	}

	@Test
	public void registerAccount() {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(1000);
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys("le");
		driver.findElement(By.id("middlename")).sendKeys("thi");
		driver.findElement(By.id("lastname")).sendKeys("An");
		driver.findElement(By.id("email_address")).sendKeys("user" + randomInt + "@gmail.com");
		driver.findElement(By.id("password")).sendKeys("12345678");
		driver.findElement(By.id("confirmation")).sendKeys("12345678");
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertEquals(driver.findElement(By.className("success-msg")).getText(),
				"Thank you for registering with Main Website Store.");
	}
	@Test
	public void loginWithValidEmailAndPass() {
		driver.findElement(txtEmail).sendKeys("user1@gmail.com");
		driver.findElement(txtPassword).sendKeys("12345678");
		driver.findElement(btnLogin).click();
		driver.findElement(By.xpath("//div[@class='page-title']/h1")).getText();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title']/h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='hello']")).getText(), "Hello, Test Test Test!");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='box-content' and contains(.,'Change Password')]")).isDisplayed());			
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
