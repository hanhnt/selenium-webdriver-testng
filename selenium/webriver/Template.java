package webriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Template {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	JavascriptExecutor jsExecute;
	WebDriverWait expliciWailt;
	
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

	public boolean isPageLoadSuccess(WebDriver driver) {
		ExpectedCondition<Boolean> JQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecute.executeScript("return(window.jQuery!=null) &&(jQuery.active==0);");
			}
		};
		return expliciWailt.until(JQueryLoad);
	}
	
	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void switchWindows(String windowID) {
		Set<String> allWindows=driver.getWindowHandles();
		for (String windows: allWindows) {
			//neu window = voi parent thi khong switch
			if(!windows.equals(windowID)) {
				driver.switchTo().window(windows);
			} 
		}
	}
	
	public void closeAllWindowWithoutParentWindow(String parentWindow) {
		Set<String> allWindow=driver.getWindowHandles();
		for (String window: allWindow) {
			if(!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}
	
	public void switchWindowByTitle(String expectedPageTitle) {
		Set<String> allWindows=driver.getWindowHandles();
		for (String window: allWindows) {
			driver.switchTo().window(window);
			
			String actualTitle=driver.getTitle();
			if(actualTitle.equals(expectedPageTitle)) {break;}
			
		}
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
