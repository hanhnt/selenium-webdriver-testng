package webriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic09_ButtonRadioCheckBoxDefault {
	WebDriver driver;
	String projectPath=System.getProperty("user.dir");
	JavascriptExecutor jsExecute;
	WebDriverWait explicitWait;
	
	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		jsExecute= (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		explicitWait=new WebDriverWait(driver, 20);
		driver.manage().window().maximize();		
	}
	
	public void TC01_Button() {
		By btnLogin= By.cssSelector("button.fhs-btn-login");
		driver.get("https://www.fahasa.com/customer/account/create?attempt=1");
		driver.findElement(By.className("fhs_top_account_login_button")).click();
		Assert.assertFalse(driver.findElement(btnLogin).isEnabled());
		
		driver.findElement(By.id("login_username")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("login_password")).sendKeys("12345678");
		Assert.assertTrue(driver.findElement(btnLogin).isEnabled());
		
		//get color button
		String rgbaColor= driver.findElement(btnLogin).getCssValue("background-color");
		System.out.println("RGBA=" +rgbaColor );
		
		//Convert rgbaColor to Hexa
		String hexaColor=Color.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hexa=" +hexaColor );
		Assert.assertEquals(hexaColor, "#C92127");
		
		driver.navigate().refresh();
		driver.findElement(By.className("fhs_top_account_login_button")).click();
		
		//remove attribute login button
		jsExecute.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(btnLogin));
		sleepInSecond(10);
		
		Assert.assertTrue(driver.findElement(btnLogin).isEnabled());
		driver.findElement(btnLogin).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='fhs-input-box checked-error']//div[@class='fhs-input-alert']")).getText(), "Th??ng tin n??y kh??ng th??? ????? tr???ng");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='fhs-input-box fhs-input-display checked-error']//div[@class='fhs-input-alert']")).getText(), "Th??ng tin n??y kh??ng th??? ????? tr???ng");
		
}
	
	public void TC02_CheckboxRadioDefault() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		By chbLuggage=By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		selectElementDefault(chbLuggage);
		Assert.assertTrue(driver.findElement(chbLuggage).isSelected());
		elementUnSelect(chbLuggage);
		Assert.assertFalse(driver.findElement(chbLuggage).isSelected());
		
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		By radioPetro=By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
		selectElementDefault(radioPetro);
		Assert.assertTrue(driver.findElement(radioPetro).isSelected());
	}
	@Test
	public void TC03_CheckboxRadioCustom() {
		driver.get("https://material.angular.io/components/radio/examples");
		By radioSummer= By.cssSelector("input#mat-radio-2-input");
		selectElementCustom(radioSummer);
		Assert.assertTrue(driver.findElement(radioSummer).isSelected());
		
		driver.get("https://material.angular.io/components/checkbox/examples");
		By chbChecked=By.cssSelector("input#mat-checkbox-1-input");
		selectElementCustom(chbChecked);
		Assert.assertTrue(driver.findElement(chbChecked).isSelected());
		unSelectElementCustom(chbChecked);
		Assert.assertFalse(driver.findElement(chbChecked).isSelected());
		
		driver.get("https://material.angular.io/components/checkbox/examples");
		By chbIndeterminate=By.cssSelector("input#mat-checkbox-2-input");
		selectElementCustom(chbIndeterminate);
		Assert.assertTrue(driver.findElement(chbIndeterminate).isSelected());
		unSelectElementCustom(chbIndeterminate);
		Assert.assertFalse(driver.findElement(chbIndeterminate).isSelected());
	}
	
	public void TC04_CheckboxAndRadioButtonOnGoogleForm() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		By chbDanang= By.xpath("//div[@aria-label='???? N???ng']");
		selectElementDefault(chbDanang);
		Assert.assertEquals(driver.findElement(chbDanang).getAttribute("aria-checked"), "true");
		
		List<WebElement> allCheckbox = driver.findElements(By.cssSelector("div[role='checkbox']"));
		for (WebElement element: allCheckbox ) {
			element.click();
			Assert.assertEquals(element.getAttribute("aria-checked"), "true");
		}
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void selectElementCustom(By by) {
		WebElement elment =driver.findElement(by);
		if(!elment.isSelected()) {
		jsExecute.executeScript("arguments[0].click();", elment);
		}
	}
	
	public void unSelectElementCustom(By by) {
		WebElement elment =driver.findElement(by);
		if(elment.isSelected()) {
		jsExecute.executeScript("arguments[0].click();", elment);
		}
	}
	
	public void selectElementDefault(By by) {
		WebElement element= driver.findElement(by);
		if(!element.isSelected()) {
			element.click();
		}		
	}
	
	public void elementUnSelect(By by) {
		WebElement element= driver.findElement(by);
		if(element.isSelected()) {
			element.click();
		}		
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
