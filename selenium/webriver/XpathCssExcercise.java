package webriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class XpathCssExcercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void BeforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}

	@Test
	public void RegisterWithEmptyData() {

		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		String actualNameError = driver.findElement(By.id("txtFirstname-error")).getText();
		Assert.assertEquals(actualNameError, "Vui lòng nhập họ tên");

		String actualEmailError = driver.findElement(By.id("txtEmail-error")).getText();
		Assert.assertEquals(actualEmailError, "Vui lòng nhập email");

		String actualConfirmEmailError = driver.findElement(By.id("txtCEmail-error")).getText();
		Assert.assertEquals(actualConfirmEmailError, "Vui lòng nhập lại địa chỉ email");

		String actualPasswordError = driver.findElement(By.id("txtPassword-error")).getText();
		Assert.assertEquals(actualPasswordError, "Vui lòng nhập mật khẩu");

		String actualConfirmPasswordError = driver.findElement(By.id("txtCPassword-error")).getText();
		Assert.assertEquals(actualConfirmPasswordError, "Vui lòng nhập lại mật khẩu");

		String actualPhoneError = driver.findElement(By.id("txtPhone-error")).getText();
		Assert.assertEquals(actualPhoneError, "Vui lòng nhập số điện thoại.");
	}

	@Test
	public void TC02() {

	}

	@Test
	public void TC03() {

	}

	@Test
	public void TC04() {

	}

	@Test
	public void TC05() {

	}

	@Test
	public void TC06() {

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
