package webriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic07_TextboxTextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String loginUrl, userId, password, name, gender, dateOfBirthInput, dateOfBirthOutput, addressInput, addressOutput,
			cityInput, stateInput, pinInput, phoneInput, emailInput, customerIdInput;
	By address = By.name("addr");
	By city = By.name("city");
	By state = By.name("state");
	By pin = By.name("pinno");
	By phone = By.name("telephoneno");
	By email = By.name("emailid");
	By btnSubmit = By.name("sub");
	By txtDateOfBirth = By.id("dob");
	String editAddressInput, editAddressOutput, editCity, editState, editPin, editPhone, editEmail;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		name = "hanh";
		gender = "female";
		dateOfBirthInput = "01/01/1991";
		dateOfBirthOutput = "1991-01-01";
		addressInput = "123 Le Loi \nDa Nang";
		addressOutput = "123 Le Loi Da Nang";
		cityInput = "Da Nang";
		stateInput = "Da Nang";
		pinInput = "123456";
		phoneInput = "0123456789";
		emailInput = "user1" + getRandomNumber() + "@gmail.com";

		editAddressInput = "789 Le Loi\nHo Chi Minh";
		editAddressOutput = "789 Le Loi Ho Chi Minh";
		editCity = "Ho Chi Minh";
		editState = "Ho Chi Minh";
		editPin = "234567";
		editPhone = "0987654321";
		editEmail = "user1" + getRandomNumber() + "@yopmail.com";

		jsExecutor = (JavascriptExecutor) driver;
		driver.get("http://demo.guru99.com/v4/");

	}

	@Test
	public void TC01_Register() {
		loginUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='here']")).click();
		driver.findElement(By.name("emailid")).sendKeys(emailInput);
		driver.findElement(By.name("btnLogin")).click();
		userId = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
	}

	@Test
	public void TC02_Login() {
		driver.get(loginUrl);
		driver.findElement(By.name("uid")).sendKeys(userId);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//marquee[@class='heading3']")).getText(), "Welcome To Manager's Page of Guru99 Bank");		
	}
	@Test
	public void TC03_NewCustomer() {
		driver.findElement(By.xpath("//a[contains(text(),'New Customer')]")).click();
		driver.findElement(By.name("name")).sendKeys(name);
		driver.findElement(By.xpath("//input[@value='f']")).click();

		jsExecutor.executeScript("arguments[0].removeAttribute('type' )", driver.findElement(txtDateOfBirth));

		driver.findElement(txtDateOfBirth).sendKeys(dateOfBirthInput);
		driver.findElement(address).sendKeys(addressInput);
		driver.findElement(By.name("city")).sendKeys(cityInput);
		driver.findElement(By.name("state")).sendKeys(stateInput);
		driver.findElement(By.name("pinno")).sendKeys(pinInput);
		driver.findElement(By.name("telephoneno")).sendKeys(phoneInput);
		driver.findElement(By.name("emailid")).sendKeys(emailInput);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("sub")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='heading3']")).getText(),
				"Customer Registered Successfully!!!");
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),
				gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dateOfBirthOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				addressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),
				cityInput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				stateInput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
				pinInput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				phoneInput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				emailInput);
		customerIdInput = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		
	}
	@Test
	public void TC04_EditCustomer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerIdInput);
		driver.findElement(By.name("AccSubmit")).click();
		String customerName = driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td/input"))
				.getAttribute("value");
		System.out.print("customerName"+customerName);
		Assert.assertEquals(customerName, name);
		String addressEdit = driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText();
		Assert.assertEquals(addressEdit, addressInput);
		driver.findElement(address).clear();
		driver.findElement(address).sendKeys(editAddressInput);
		driver.findElement(city).clear();
		driver.findElement(city).sendKeys(editCity);
		driver.findElement(state).clear();
		driver.findElement(state).sendKeys(editState);
		driver.findElement(pin).clear();
		driver.findElement(pin).sendKeys(editPin);
		driver.findElement(phone).clear();
		driver.findElement(phone).sendKeys(editPhone);
		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(editEmail);
		driver.findElement(btnSubmit).click();

		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
			editAddressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),
				editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
				editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				editPhone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				editEmail);
	}

	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
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
