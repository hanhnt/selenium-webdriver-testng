package webriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic15_JavaScript {
	JavascriptExecutor jsExecutor;
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
		jsExecutor = (JavascriptExecutor) driver;
	}

	@Test
	public void TC01_JavaScriptExecutor() {
		navigateToUrlByJS("http://live.techpanda.org/");
		String url= (String)jsExecutor.executeScript("return document.URL");
		Assert.assertEquals(url, "http://live.techpanda.org/");
		
		String domain= (String)jsExecutor.executeScript("return document.domain");
		Assert.assertEquals(domain, "live.techpanda.org");
		clickToElementByJS("//a[text()='Mobile']");
		//driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']")).click();
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']/button");
		//sleepInSecond(5);
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		//Assert.assertTrue(getInnerText().contains("Samsung Galaxy was added to your shopping cart."));
		clickToElementByJS("//a[text()='Customer Service']");
		Assert.assertTrue(areExpectedTextInInnerText("CUSTOMER SERVICE"));
		
		scrollToBottomPage();
		driver.findElement(By.id("newsletter")).sendKeys("hanh@gmail.com");
		sendkeyToElementByJS("//input[@id='newsletter']", "hanh@gmail.com");
		clickToElementByJS("//button[@title='Subscribe']");
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription"));
	}
	
	
	public void TC02_ValidationMessageHTML5() {
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		clickToElementByJS("//input[@name='submit-btn']");
		// var element= $$("input[name='fname']")[0]
//			    element.validationMessage; dung cai nay de lay Please fill out this field. tren trinh duyet

		Assert.assertEquals(getElementValidationMessage("//input[@name='fname']"), "Please fill out this field.");
		driver.findElement(By.name("fname")).sendKeys("hanh");
		clickToElementByJS("//input[@name='submit-btn']");
		Assert.assertEquals(getElementValidationMessage("//input[@name='pass']"), "Please fill out this field.");
		
		driver.findElement(By.name("fname")).clear();
		driver.findElement(By.name("fname")).sendKeys("hanh");
		driver.findElement(By.name("pass")).sendKeys("12345678");
		clickToElementByJS("//input[@name='submit-btn']");
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please fill out this field.");
		
		driver.findElement(By.name("fname")).clear();
		driver.findElement(By.name("fname")).sendKeys("hanh");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("12345678");
		driver.findElement(By.name("em")).sendKeys("hanh@gmail");
		clickToElementByJS("//input[@name='submit-btn']");
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "Please match the requested format.");
		
		driver.findElement(By.name("fname")).clear();
		driver.findElement(By.name("fname")).sendKeys("hanh");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("12345678");
		driver.findElement(By.name("em")).clear();
		driver.findElement(By.name("em")).sendKeys("123@#$!");
		clickToElementByJS("//input[@name='submit-btn']");
		Assert.assertEquals(getElementValidationMessage("//input[@id='em']"), "A part following '@' should not contain the symbol '#'.");
		
		driver.findElement(By.name("fname")).clear();
		driver.findElement(By.name("fname")).sendKeys("hanh");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("12345678");
		driver.findElement(By.name("em")).clear();
		driver.findElement(By.name("em")).sendKeys("hanh@gmail.com");
		clickToElementByJS("//input[@name='submit-btn']");
		Assert.assertEquals(getElementValidationMessage("//b[text()='✱ ADDRESS ']/parent::label/following-sibling::select"), "Please select an item in the list.");		
	}
	
	public void TC03_removeAttribute() {
		navigateToUrlByJS("http://demo.guru99.com/v4/");
	}

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		if (status) {
			return true;
		}
		return false;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
