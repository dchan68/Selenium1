package com.fdmgroup.testScript;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.fdmgroup.util.DriverUtilities;

public class TestBasicSeleniumCommands {
	private DriverUtilities driverUtilities;
	private WebDriver driver;
	
	@Before
	public void setUp() {
		driverUtilities = DriverUtilities.getInstance();
		driver = driverUtilities.getDriver();
	}
	
	//TO RUN BOTH TESTS AT ONCE, COMMENT OUT THE @IGNORE IN THE FIRST TEST CASE AND COMMENT OUT THE driver.quit() from first test case.
	
	@Test
	@Ignore
	public void testBrowserCommands() throws IOException {
		driver.get("http://www.google.com");
		
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcFile, new File("C:\\Users\\darya\\Google Drive\\FDM\\Automation\\SeleniumExample1\\screenshot.png"));
		
		driver.manage().window().maximize();	
		driver.navigate().to("https://mvnrepository.com/artifact/junit/junit/4.13.1");
		
		System.out.println("Current URL is : " + driver.getCurrentUrl());
		System.out.println("Title is : " + driver.getTitle());
		System.out.println("Browser Name is : " + ((RemoteWebDriver) driver).getCapabilities().getBrowserName());
		System.out.println("Browser Version is : " + ((RemoteWebDriver) driver).getCapabilities().getVersion());
		
		driver.navigate().refresh();
		driver.navigate().back();
		driver.navigate().forward();
		driver.quit(); //quit entire browser
		//driver.close();  //close current tab
		
	}
	
	@Test
	@Ignore
	public void testSignIn() throws InterruptedException{
		driver.manage().window().maximize(); 
		driver.get("http://zero.webappsecurity.com/index.html");	
		driver.findElement(By.className("signin")).click();
		Thread.sleep(2000);
		driver.findElement(By.name("user_login")).sendKeys("username");
		driver.findElement(By.name("user_password")).sendKeys("password");
		Thread.sleep(3000);
		driver.findElement(By.name("submit")).click();
		driver.findElement(By.xpath("//*[@id=\"details-button\"]")).click();
		//Can comment out the line above and uncomment the line directly below. Both will do the same thing using diff method, xpath or by id
		//driver.findElement(By.id("details-button")).click();
		driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
		//driver.findElement(By.id("proceed-link")).click();

		//Getting the first header, which is "Cash Accounts"
		WebElement homePageLabel = driver.findElement(By.className("board-header"));
		String actualHomePageLabelText = homePageLabel.getText();
		assertEquals("Cash Accounts", actualHomePageLabelText);
		
		List<WebElement> listOfTitleOnHomePage = driver.findElements(By.tagName("h2"));
		
		for (WebElement webElement : listOfTitleOnHomePage) {
			System.out.println(webElement.getText());
		}
		
		driver.findElement(By.linkText("Account Activity")).click();
		String actualAccountActivityTitle = driver.getTitle();
		assertEquals("Zero - Account Activity", actualAccountActivityTitle);
		
//		driver.findElement(By.partialLinkText("Bills")).click();
//		String actualPayBillTitle = driver.getTitle();
//		assertEquals("Zero - Pay Bills", actualPayBillTitle);
		
		WebElement accountDropDown = driver.findElement(By.name("accountId"));
		Select accountSelected = new Select(accountDropDown);
		accountSelected.selectByVisibleText("Loan"); //loan
		Thread.sleep(2000);
		accountSelected.selectByIndex(4); //credit card
		Thread.sleep(2000);
		accountSelected.selectByValue("6"); //brokerage
		
		driver.quit();
	}
	
	@Test
	@Ignore
	public void testCustomizedXPath() {
		driver.manage().window().maximize(); 
		driver.get("http://zero.webappsecurity.com/index.html");
		//driver.findElement(By.xpath("//*[@id=\"signin_button\"]")).click();
		driver.findElement(By.xpath("//*[contains(@class,'signin')]")).click();
		driver.findElement(By.xpath("//*[contains(@class,'brand')]")).click();
		driver.quit();
	}
	
	@Test
	public void testLoginAndLogoutFunctionality() throws InterruptedException {
		driver.manage().window().maximize(); 
		driver.get("http://zero.webappsecurity.com/index.html");
		driver.findElement(By.xpath("//*[contains(@class,'signin')]")).click();
		
		WebElement usernameFill = driver.findElement(By.xpath("//*[@name='user_login']"));
		usernameFill.sendKeys("asdfadsf");
		usernameFill.clear();
		usernameFill.sendKeys("username");
		
		driver.findElement(By.xpath("//*[contains(@name,'password')]")).sendKeys("password");
		
		WebElement rememberMeCheckBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
		
		if(!rememberMeCheckBox.isSelected()) {
			rememberMeCheckBox.click();
			System.out.println(rememberMeCheckBox.isSelected());
		}
		
		WebElement signInButton = driver.findElement(By.xpath("//*[@value='Sign in']"));
		
//		signInButton.click();
//		signInButton.sendKeys(Keys.ENTER);
		signInButton.submit();
		driver.findElement(By.xpath("//*[@id=\"details-button\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"proceed-link\"]")).click();
		
		WebElement profileDropDown = driver.findElement(By.xpath("//*[@id=\"settingsBox\"]/ul/li[3]/a"));
		String actualProfileName = profileDropDown.getText();
		assertEquals("username", actualProfileName);
		profileDropDown.click();
		
		WebElement logoutOption = driver.findElement(By.xpath("//*[@id=\"logout_link\"]"));
		Thread.sleep(3000);
		if(logoutOption.isDisplayed()) {
			logoutOption.click();
		}
		driver.quit();
	}
}


