package webriver;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic11_Action {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	Actions action;
	JavascriptExecutor jsExecute;
	Keys key;
	String osName=System.getProperty("os.name");
	Alert alert;
	String jsHelperPath= projectPath + "/dragAndDrop/dragAndDropHelper";
	//String jqueryLoadHelperPath= projectPath + "/dragAndDrop/jqueryLoadHelper";

	@BeforeClass
	public void beforClass() {
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//		driver = new FirefoxDriver();
		// Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		driver = new ChromeDriver();
		
		// wait to find element
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		action = new Actions(driver);
		jsExecute = (JavascriptExecutor) driver;
		if (osName.contains("Mac")) {
			key=Keys.COMMAND;
		} else {key=Keys.CONTROL;}
	}

	public void TC01_hoverToElement() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		action.moveToElement(driver.findElement(By.id("age"))).perform();
		String expectedText = "We ask for your age only for statistical purposes.";
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(), expectedText);
	}

	public void TC02_hoverToElement() {
		driver.get("https://www.myntra.com/");
		action.moveToElement(driver.findElement(By.cssSelector("a[data-group='kids']"))).perform();
		action.click(driver.findElement(By.xpath("//a[text()='Home & Bath']"))).perform();
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb']")).getText(),
				"Kids Home Bath");
		Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Kids Home Bath']")).getText(), "Kids Home Bath");
	}

	public void TC03_clickAndHoldElement() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allItems = driver.findElements(By.cssSelector("li[class='ui-state-default ui-selectee']"));
		action.clickAndHold(allItems.get(0)).moveToElement(allItems.get(3)).release().perform();
		List<WebElement> allItemsChose = driver.findElements(By.cssSelector("li[class$='ui-selected']"));
		Assert.assertEquals(allItemsChose.size(), 4);
	}
	
	public void TC04_clickRandomElement() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allItems = driver.findElements(By.cssSelector("li[class='ui-state-default ui-selectee']"));
		action.keyDown(key).perform();
		action.click(allItems.get(0)).click(allItems.get(2)).click(allItems.get(4)).click(allItems.get(6))
				.click(allItems.get(8)).perform();
		action.keyUp(key).perform();
		List<WebElement> allItemsChose = driver.findElements(By.cssSelector("li[class$='ui-selected']"));
		Assert.assertEquals(allItemsChose.size(), 5);
	}

	
	public void TC05_doubleClickElement() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		jsExecute.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//button[text()='Double click me']")));
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(5);
		Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
	}
	
	public void TC06_rightClickElement() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-visible")).isDisplayed());
		action.click(driver.findElement(By.cssSelector("li.context-menu-visible"))).perform();
		driver.switchTo().alert().accept();
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
	}
	
	public void TC07_dragDropElement() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		WebElement smallCircle= driver.findElement(By.id("draggable"));
		WebElement bigCircle= driver.findElement(By.id("droptarget"));
		action.dragAndDrop(smallCircle,bigCircle).perform();		
		Assert.assertEquals(bigCircle.getText(),"You did great!");
		String rgba= bigCircle.getCssValue("background-color").toLowerCase();
		String hexa= org.openqa.selenium.support.Color.fromString(rgba).asHex();
		Assert.assertEquals(hexa, "#03a9f4");
	}
	@Test
	public void TC08_dragDropElementHTML5ByCSS() throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		String jsHelperContentFile = getContentFile(jsHelperPath);
		
		// A to B 
		jsExecute.executeScript(jsHelperContentFile);
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

		// B to A
		jsExecute.executeScript(jsHelperContentFile);
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());
	}
	//Run case nay can setting tren Mac
	public void TC09_dragDropElementHTML5ByXpath() throws AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		dragAndDropHtml5ByXpath("//div[@id='column-a']",  "//div[@id='column-b']");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());
		
		dragAndDropHtml5ByXpath("//div[@id='column-a']",  "//div[@id='column-b']");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());		
	}
	
	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
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
	
	public void dragAndDropHtml5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
