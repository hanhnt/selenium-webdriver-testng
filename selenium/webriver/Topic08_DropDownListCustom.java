package webriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic08_DropDownListCustom {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath=System.getProperty("user.dir");
	JavascriptExecutor jsExecutor;
	Select select;
	
	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		
		//wait to find element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		
		//wait to apply for status of elements: visible, invisible, present
		explicitWait=new WebDriverWait(driver, 15);
		
		jsExecutor = (JavascriptExecutor) driver;
	}
	
	
	public void TC01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
//		By parent = By.id("number-button");
//		By allItems=By.cssSelector("#number-menu div");
		//selectItemInDropDownList(parent, allItems, "19");
		selectItemInDropDownList(By.id("number-button"), By.cssSelector("#number-menu div"), "19");
	}
	
	public void TC02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		//div.divider.default.text
		By parent = By.cssSelector("i.dropdown.icon");
		By allItems=By.cssSelector("div[role='option']>span");
		selectItemInDropDownList(parent, allItems, "Elliot Fu");
	}
	
	public void TC03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		By parent = By.className("btn-group");
		By allItems=By.cssSelector("ul[class='dropdown-menu']>li>a");
		selectItemInDropDownList(parent, allItems, "Second Option");
	}
	
	public void TC04_Angula() {
		//  //span[@aria-labelledby='games_hidden']
		driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
		By parent = By.xpath("//span[@aria-owns='games_options']");
		By allItems=By.cssSelector("ul#games_options>li");
		selectItemInDropDownList(parent, allItems, "Badminton");
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[@aria-owns='games_options']/input")).getAttribute("aria-label"), "Badminton");
	}
	
	
	public void TC05_Editable() {
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		select=new Select(driver.findElement(By.id("base")));
		select.selectByVisibleText("Ford");
		By parent=By.xpath("//div[text()='Default']/following-sibling::div/input");
		By allItems=By.cssSelector("#default-place ul li");
		selectItemInDropDownList(parent, allItems, "Volkswagen");
	}
	@Test
	public void TC06_Kenko() {
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		
		Assert.assertTrue(explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("kd-loader"))));
		Assert.assertTrue(explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.k-i-loading"))));
		selectItemInDropDownList(By.cssSelector("span[aria-owns='categories_listbox']"), By.cssSelector("span.k-state-default h3"), "Produce");
		
		Assert.assertTrue(explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.k-i-loading"))));
		selectItemInDropDownList(By.cssSelector("span[aria-owns='products_listbox']"), By.cssSelector("#products_listbox>li"), "Longlife Tofu");
		
		Assert.assertTrue(explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.k-i-loading"))));
		selectItemInDropDownList(By.cssSelector("span[aria-owns='shipTo_listbox']"), By.cssSelector("#shipTo_listbox li"), "Kirchgasse 6");
	}
	
	public void selectItemInDropDownList(By parentBy, By childBy, String text ) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(parentBy));
		driver.findElement(parentBy).click();
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(childBy));
		List<WebElement> allElement=driver.findElements(childBy);
		for (WebElement element: allElement) {
			if(element.getText().trim().equals(text)) {
				if(element.isDisplayed())
				{
					element.click();
					}							
			} else {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);				                          
				element.click();
			}
		}
	}

//	@AfterClass
//	public void afterClass() {
//		driver.quit();
//	}
}
