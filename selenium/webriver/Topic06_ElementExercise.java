package webriver;

import static org.testng.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.EmailableReporter;

public class Topic06_ElementExercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	By txtEmail = By.id("mail");
	By radioAgeUnder18 = By.id("under_18");
	By txtEducation = By.id("edu");
	By lbUser5 = By.xpath("//h5[text()='Name: User5']");
	By dropDownJob1 = By.id("job1");
	By dropDownJob2 = By.id("job2");
	By chbInterest = By.id("development");
	By slider1 = By.id("slider-1");
	By txtPassword = By.id("password");
	By radioDisable = By.id("radio-disabled");
	By txtBiography = By.id("bio");
	By dropDownJob3 = By.id("job3");
	By chbDisable = By.id("check-disbaled");
	By slider2 = By.id("slider-2");
	By chbJava = By.id("java");

	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}

	@Test
	public void tc01IsDisplayRefactor() {
		Assert.assertTrue(isElementDisplay(txtEmail));
		Assert.assertTrue(isElementDisplay(radioAgeUnder18));
		Assert.assertTrue(isElementDisplay(txtEducation));
		Assert.assertFalse(isElementDisplay(lbUser5));
		if (isElementDisplay(txtEmail)) {
			sendkeyElement(txtEmail, "AutomationTesting");
		}
		if (isElementDisplay(txtEducation)) {
			sendkeyElement(txtEducation, "master");
		}
		if (isElementDisplay(radioAgeUnder18)) {
			clickElement(radioAgeUnder18);
		}
	}

	@Test
	public void tc02IsEnableRefactor() {
		Assert.assertTrue(isElementEnable(txtEmail));
		Assert.assertTrue(isElementEnable(radioAgeUnder18));
		Assert.assertTrue(isElementEnable(txtEducation));
		Assert.assertTrue(isElementEnable(dropDownJob1));
		Assert.assertTrue(isElementEnable(dropDownJob2));
		Assert.assertTrue(isElementEnable(chbInterest));
		Assert.assertTrue(isElementEnable(slider1));

		Assert.assertFalse(isElementEnable(txtPassword));
		Assert.assertFalse(isElementEnable(radioDisable));
		Assert.assertFalse(isElementEnable(txtBiography));
		Assert.assertFalse(isElementEnable(dropDownJob3));
		Assert.assertFalse(isElementEnable(chbDisable));
		Assert.assertFalse(isElementEnable(slider2));
	}

	@Test
	public void tc03IsSelectRefactor() {
		clickElement(radioAgeUnder18);
		clickElement(chbJava);
		Assert.assertTrue(isElementSelect(radioAgeUnder18));
		Assert.assertTrue(isElementSelect(chbJava));
		clickElement(chbJava);
		Assert.assertFalse(isElementSelect(chbJava));
	}

	public boolean isElementDisplay(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			System.out.println("Element [" + by + "] is display");
			return true;
		} else
			System.out.println("Element [" + by + "] is not display");
		return false;
	}

	public void sendkeyElement(By by, String value) {
		WebElement element = driver.findElement(by);
		element.sendKeys(value);
	}

	public void clickElement(By by) {
		WebElement element = driver.findElement(by);
		element.click();
	}

	public boolean isElementEnable(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			System.out.println("Element [" + by + "] is enable");
			return true;
		} else
			System.out.println("Element [" + by + "] is not enable");
		return false;
	}

	public boolean isElementSelect(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			System.out.println("Element [" + by + "] is enable");
			return true;
		} else
			System.out.println("Element [" + by + "] is not enable");
		return false;
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
