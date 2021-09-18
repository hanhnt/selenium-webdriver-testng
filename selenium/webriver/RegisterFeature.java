package webriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterFeature {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void BeforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}

	
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

	
	public void RegisterWithInvalidEmail() {
		
		driver.findElement(By.id("txtFirstname")).sendKeys("hanhnt");
		driver.findElement(By.id("txtEmail")).sendKeys("123@123@123");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@123@123");	
		driver.findElement(By.id("txtPassword")).sendKeys("12345678");
		driver.findElement(By.id("txtCPassword")).sendKeys("12345678");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		String actualEmailError = driver.findElement(By.id("txtEmail-error")).getText();
		Assert.assertEquals(actualEmailError, "Vui lòng nhập email hợp lệ");
		
		String actualConfirmEmailError = driver.findElement(By.id("txtCEmail-error")).getText();
		Assert.assertEquals(actualConfirmEmailError, "Email nhập lại không đúng");
		}

	
	public void RegisterWithIncorrectConfirmEmail() {
		driver.findElement(By.id("txtFirstname")).sendKeys("hanhnt");
		driver.findElement(By.id("txtEmail")).clear();
		driver.findElement(By.id("txtEmail")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("123@123@123");	
		driver.findElement(By.id("txtPassword")).sendKeys("12345678");
		driver.findElement(By.id("txtCPassword")).sendKeys("12345678");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		String actualConfirmEmailError = driver.findElement(By.id("txtCEmail-error")).getText();
		Assert.assertEquals(actualConfirmEmailError, "Email nhập lại không đúng");
	}

	
	public void RegisterWithPasswordLessThan6() {
		driver.findElement(By.id("txtFirstname")).sendKeys("hanhnt");
		driver.findElement(By.id("txtEmail")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("txtCEmail")).clear();
		driver.findElement(By.id("txtCEmail")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("1234");
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("1234");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		String actualPasswordError = driver.findElement(By.id("txtPassword-error")).getText();
		Assert.assertEquals(actualPasswordError, "Mật khẩu phải có ít nhất 6 ký tự");

		String actualConfirmPasswordError = driver.findElement(By.id("txtCPassword-error")).getText();
		Assert.assertEquals(actualConfirmPasswordError, "Mật khẩu phải có ít nhất 6 ký tự");

	}

	
	public void RegisterWithIncorrectConfrimPassword() {
		driver.findElement(By.id("txtFirstname")).sendKeys("hanhnt");
		driver.findElement(By.id("txtEmail")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("hanh@gmail.com");	
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("1234567");
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("1234568");
		driver.findElement(By.id("txtPhone")).sendKeys("0123456789");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();

		String actualConfirmPasswordError = driver.findElement(By.id("txtCPassword-error")).getText();
		Assert.assertEquals(actualConfirmPasswordError, "Mật khẩu bạn nhập không khớp");
		}

	@Test
	public void InvalidPhone() {
		driver.findElement(By.id("txtFirstname")).sendKeys("hanhnt");
		driver.findElement(By.id("txtEmail")).sendKeys("hanh@gmail.com");
		driver.findElement(By.id("txtCEmail")).sendKeys("hanh@gmail.com");	
		driver.findElement(By.id("txtPassword")).sendKeys("1234567");
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("1234567");
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("12345677");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		
		String actualPhoneError = driver.findElement(By.id("txtPhone-error")).getText();
		Assert.assertEquals(actualPhoneError, "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019");
		
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("0123456");
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		String actualPhoneError1 = driver.findElement(By.id("txtPhone-error")).getText();
		Assert.assertEquals(actualPhoneError1, "Số điện thoại phải từ 10-11 số.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
