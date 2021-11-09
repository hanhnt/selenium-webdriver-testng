package webriver;

import java.time.Duration;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic17_Wait {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	FluentWait<WebElement> fluentElement;

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//		driver = new FirefoxDriver();
		// Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();

		// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		explicitWait = new WebDriverWait(driver, 5);

	}

	public void TC01_ExplicitWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.xpath("//button[text()='Start']")).click();
		// explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	public void TC02_ExplicitWait() {
		String dateInput = "Tuesday, November 02, 2021";
		String dateOutput = "Tuesday, November 2, 2021";
		driver.get(
				"https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("calendarContainer")));
		WebElement dateResult = driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1"));
		Assert.assertEquals(dateResult.getText(), "No Selected Dates to display.");
		driver.findElement(By.xpath("//td[@title='" + dateInput + "']")).click();
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.id("ctl00_ContentPlaceholder1_RadAjaxLoadingPanel1ctl00_ContentPlaceholder1_RadCalendar1")));
		explicitWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")));
		Assert.assertEquals(driver.findElement(By.cssSelector("#ctl00_ContentPlaceholder1_Label1")).getText(),
				dateOutput);
	}

	public void TC03_ExplicitWait() {
		String pinkPath = projectPath + "/uploadFile/" + "pink.jpeg";
		String redPath = projectPath + "/uploadFile/" + "red.jpeg";
		String whitePath = projectPath + "/uploadFile/" + "white.jpeg";
		driver.get("https://gofile.io/uploadFiles");
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.text-center")));
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(pinkPath + "\n" + redPath + "\n" + whitePath);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='rowUploadProgress']")));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='text-center']/i[@class='fas fa-spinner fa-spin']")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.callout-success>h5")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.callout-success>h5")).getText(),
				"Your files have been successfully uploaded");
	}

	@Test
	public void TC04_ExplicitWait() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.name("txtUsername")).sendKeys("Admin");
		driver.findElement(By.name("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		//Assert.assertTrue(isJQueryLoadedSuccess(driver));
		Assert.assertTrue(isjQueryLoadSuccess(driver));
		Assert.assertEquals(driver.findElement(By.cssSelector("div[id='total'] span")).getText(), "3 month(s)");
		driver.findElement(By.id("menu_pim_viewPimModule")).click();
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("Peter Mac");
		driver.findElement(By.id("searchBtn")).click();
		Assert.assertTrue(isjQueryLoadSuccess(driver));
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Peter Mac']")).isDisplayed());
	}
	
	
	public void TC05_FluentWait() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		WebElement countdownTimer= driver.findElement(By.id("javascript_countdown_time"));
		fluentElement = new FluentWait<WebElement> (countdownTimer);
		//fluentElement.withTimeout(15, TimeUnit.SECONDS); use with old version selenium
		fluentElement.withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class)
		.until(new Function<WebElement, Boolean>() {

			@Override
			public Boolean apply(WebElement countdown) {
				Boolean status=countdown.getText().endsWith("00");
				System.out.println("Text= "+countdown.getText()+"------"+status);
				return status;
			}
		});
	}
	
	public void TC06_FluentWait() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		waitAndClickToElement(By.xpath("//button[text()='Start']"));
		Assert.assertEquals(getElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		
	}
	
	public WebElement getElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout)).
				pollingEvery(Duration.ofSeconds(polling)).ignoring(NoSuchElementException.class);
		WebElement element= wait.until(new Function<WebDriver , WebElement>(){
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}
	
	public void waitAndClickToElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout)).
				pollingEvery(Duration.ofSeconds(polling)).ignoring(NoSuchElementException.class);
		WebElement element= wait.until(new Function<WebDriver , WebElement>(){
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		element.click();
	}
	
	public boolean isElementDisplay(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout)).
				pollingEvery(Duration.ofSeconds(polling)).ignoring(NoSuchElementException.class);
		Boolean status= wait.until(new Function<WebDriver , Boolean>(){
			public Boolean apply(WebDriver driver) {
				return driver.findElement(locator).isDisplayed();
			}
		});
		return status;
	}

	public boolean isJQueryLoadedSuccess1(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, timeout);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> JQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return（window.jQuery!= null）&&（jQuery.active == 0);");
			}
		};
		return explicitWait.until(JQueryLoad);
	}
	
	public boolean isjQueryLoadSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, timeout);
		jsExecutor=(JavascriptExecutor) driver;
		ExpectedCondition<Boolean>JQueryLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				return (Boolean) jsExecutor.executeScript("return (window.jQuery!=null)&& (jQuery.active===0);");
			}
		};
		return explicitWait.until(JQueryLoad);
	}
	
	long timeout = 30;
	long polling=1;

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		driver.quit();
	}

}
