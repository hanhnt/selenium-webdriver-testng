package webriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic12_popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait expliciWailt;
	JavascriptExecutor jsExecute;

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//		driver = new FirefoxDriver();
		// Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		expliciWailt = new WebDriverWait(driver, 80);
		jsExecute = (JavascriptExecutor) driver;

	}

	public void TC01_randomPopup() {
		driver.get("https://blog.testproject.io/");
		// sleepInSecond(60);
		By popup = By.cssSelector("div.mailch-wrap");

		if (driver.findElement(popup).isDisplayed()) {
			System.out.println("========Popup hieen thij ========");
			driver.findElement(By.id("close-mailch")).click();
			Assert.assertFalse(driver.findElement(popup).isDisplayed());
		}
		// Wait for page load success
		Assert.assertTrue(isPageLoadSuccess(driver));
		driver.findElement(By.cssSelector("section#search-2 input.search-field")).sendKeys("selenium");
		driver.findElement(By.cssSelector("section#search-2 span.glass")).click();
		List<WebElement> postTitles = driver.findElements(By.cssSelector("h3.post-title>a"));
		for (WebElement postTitle : postTitles) {
			// System.out.println("HANH TEST: "+postTitle.getText() );
			Assert.assertTrue(postTitle.getText().contains("selenium"));
		}
	}

	public void TC02_fixPopup() {
		driver.get("https://ngoaingu24h.vn/");
		Assert.assertFalse(driver.findElement(By.id("modal-login-v1")).isDisplayed());
		driver.findElement(By.cssSelector("button.icon-before")).click();
		Assert.assertTrue(driver.findElement(By.id("modal-login-v1")).isDisplayed());
		driver.findElement(By.id("account-input")).sendKeys("automationfc");
		driver.findElement(By.id("password-input")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("button.btn-login-v1")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div.error-login-panel")).getText(),
				"Tài khoản không tồn tại!");
	}

	public void TC03_randomPopup() {
		driver.get("https://shopee.vn/");

		List<WebElement> popupElement = driver.findElements(By.cssSelector("div.shopee-popup"));
		if (popupElement.size() > 0) {

			driver.findElement(By.cssSelector("div.shopee-popup__close-btn")).click();
			sleepInSecond(10);
			Assert.assertEquals(popupElement.size(), 0);
		}
		driver.findElement(By.className("shopee-searchbar-input__input")).sendKeys("macbook");
		driver.findElement(By.cssSelector("button.btn-solid-primary")).click();
	}

	@Test
	public void TC04_randomPopup() {
		driver.get("https://shopee.vn/");
		List<WebElement> popupElement = driver.findElements(By.cssSelector("div.shopee-popup"));
		if (popupElement.size() > 0) {
			driver.findElement(By.cssSelector("div.shopee-popup__close-btn")).click();
			sleepInSecond(2);
		}
		driver.findElement(By.className("shopee-searchbar-input__input")).sendKeys("macbook");
		driver.findElement(By.cssSelector("button.btn-solid-primary")).click();
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isPageLoadSuccess(WebDriver driver) {
		ExpectedCondition<Boolean> JQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecute.executeScript("return(window.jQuery!=null) &&(jQuery.active==0);");
			}
		};
		return expliciWailt.until(JQueryLoad);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
