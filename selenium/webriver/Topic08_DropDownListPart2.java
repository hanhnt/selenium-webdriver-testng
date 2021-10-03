package webriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic08_DropDownListPart2 {
	WebDriver driver;
	String projectPath=System.getProperty("user.dir");
	Select select;
	String firstName, lastName, date, month, year,email, company,password,confirmPassword;
	By gendeByr=By.id("gender-male");
	By firstNameBy=By.id("FirstName");
	By lastnameBy =By.id("LastName"); 
	
	@BeforeClass
	public void beforClass() {
		System.setProperty("webdriver.gecko.driver", projectPath+"/browserDrivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/register");
		
		firstName="user1";
		lastName="nguyen";
		date="10";
		month="May";
		year="1990";
		email="user1"+ getRandomNumber()+"@gmail.com";
		company="companyA";
		password="12345678";
		confirmPassword="12345678";
	}
	@Test
	public void HandleDropDownList() {
		driver.findElement(By.className("ico-register"));
		driver.findElement(By.id("gender-male")).click();
		driver.findElement(firstNameBy).sendKeys(firstName);
		driver.findElement(lastnameBy).sendKeys(lastName);
		
		select= new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(date);
		Assert.assertEquals(select.getOptions().size(), 32);
		
		select= new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getOptions().size(), 13);
	
		select= new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getOptions().size(), 112);
		
		driver.findElement(By.id("Email")).sendKeys(email);
		
		driver.findElement(By.id("Company")).sendKeys(company);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(confirmPassword);
		
		driver.findElement(By.id("register-button")).click();
		
		String registerMsg= driver.findElement(By.className("result")).getText();
		Assert.assertEquals(registerMsg, "Your registration completed");
		
		driver.findElement(By.className("ico-account")).click();
		
		select=new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		
		select=new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		
		select= new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
