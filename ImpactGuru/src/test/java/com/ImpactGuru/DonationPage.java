package com.ImpactGuru;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.ImpactGuru.base.TestBase;
import com.ImpactGuru.pages.Login;

public class DonationPage extends TestBase {

	public DonationPage() {
		super();
	}

	Login lp = new Login();
	LocalDate today = LocalDate.now();
	int month = today.getMonthValue();
	int year = today.getYear();
	// System.out.println(month+"/21");

	@BeforeClass
	public void login() throws InterruptedException {

		driver.findElement(By.xpath("//button[@id='auth-local-btn']")).click();
		driver.findElement(By.id("username")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.id("submit")).click();
		Thread.sleep(2000);

		// ----- LOGIN TO STAGING SERVER ------
		driver.get("https://impactguru:8nvbil51pp@staging.igstg.com/fundraiser/help-testdocumentchecklist");

		WebElement closeButton = driver.findElement(By.cssSelector("button.close.exit-pop-up__close-btn.fr-modal__close-btn.btn-circle.btn-sm"));
	}

	@Test(priority = 0)
	public void validateNullValueOverall() throws InterruptedException {
		
		driver.findElement(By.xpath("//a[@id='cmp-nfr-top-side-donate']")).click();
		driver.findElement(By.cssSelector("#story-popup-donate-button")).click();
		Thread.sleep(2000);	
	}

	// -- NEGATIVE --
	@Test(priority = 1)
	public void validateInvalidCardNumber() throws InterruptedException {

		driver.findElement(By.cssSelector("#story_popup_donation_amount")).clear();
		driver.findElement(By.cssSelector("#story_popup_donation_amount")).sendKeys(prop.getProperty("Donation_Amount"));
		driver.findElement(By.xpath("//*[@id='full_name']")).sendKeys(prop.getProperty("donor_name"));
		driver.findElement(By.cssSelector("#email_receipt")).sendKeys(prop.getProperty("donor_email"));
		driver.findElement(By.cssSelector("#mobile")).sendKeys(prop.getProperty("donor_mobile"));
		driver.findElement(By.cssSelector("#city_text")).sendKeys(prop.getProperty("donors_city"));
		driver.findElement(By.cssSelector("#story-popup-donate-button")).click();

		// -- enter card number and click card icon --
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		driver.findElement(By.cssSelector("#cardNumber")).sendKeys(prop.getProperty("invalid_cardNumber"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//input[@id='cardNumber']//parent::div//i")).click(); 
		Thread.sleep(3000);
		System.out.println(driver.findElement(By.xpath("//input[@id='cardNumber']/parent::div[1]/small[3]")).getText());
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='cardNumber']/parent::div[1]/small[3]")).isDisplayed(), true);
		Thread.sleep(2000);		 
	}

	@Test(priority = 2)
	public void validateValidCardNumber() {

		// -- enter card number and click DONATE --
		driver.findElement(By.cssSelector("#cardNumber")).clear();
		driver.findElement(By.cssSelector("#cardNumber")).sendKeys(prop.getProperty("valid_cardNumber"));
		driver.findElement(By.xpath("//input[@id='cardNumber']//parent::div//i")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='cardNumber']/parent::div[1]/small[3]")).isDisplayed(), false);
	}

	
	@Test(priority = 3) 
	public void validateInvalidExpiryDate() throws InterruptedException  {
	  
		driver.findElement(By.cssSelector("#expiryDate")).sendKeys(month-1+"/21");
		Thread.sleep(1000); 
		//Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Enter a valid date')]")).isDisplayed(),"validateInvalidExpiryDate Failed. Validation Message not shown");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='expiryDate']//parent::div//small[2]")).isDisplayed(), true);
	}
	  
	// --- POSITIVE ---
	@Test(priority = 4) 
	public void validateValidExpiryDate() throws InterruptedException {
	  
		driver.findElement(By.cssSelector("#expiryDate")).clear();
		driver.findElement(By.cssSelector("#expiryDate")).sendKeys(month + "/21");
		Thread.sleep(1000); 
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='expiryDate']//parent::div//small[2]")).isDisplayed(), false);
	}
	 

	@Test(priority = 5)
	public void validateTwoDigitCVV() {

		driver.findElement(By.cssSelector("#cvvNumber")).sendKeys(prop.getProperty("two_digit_cvv"));
		driver.findElement(By.xpath("//button[@id='donate-card-contribute']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='cvvNumber']//parent::div//small[4]")).isDisplayed(), true);
	}
	
	@Test (priority = 6)
	public void validateFourDigitCVV() {
		  
		driver.findElement(By.cssSelector("#cvvNumber")).clear();
		driver.findElement(By.cssSelector("#cvvNumber")).sendKeys(prop.getProperty("four_digit_cvv"));
		driver.findElement(By.xpath("//button[@id='donate-card-contribute']")).click( );
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='cvvNumber']//parent::div//small[3]")).isDisplayed(), true);
	}
	  
	// --- POSITIVE ---
	@Test (priority = 8)
	public void validateThreeDigitCVV() throws InterruptedException {
	  
		driver.findElement(By.cssSelector("#cvvNumber")).clear();
		driver.findElement(By.cssSelector("#cvvNumber")).sendKeys(prop.getProperty("three_digit_cvv"));
		driver.findElement(By.xpath("//button[@id='donate-card-contribute']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/*Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		String parentWindow = it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		driver.findElement(By.xpath("//*[contains(text(),'Success')]")).click();
		*/
		Thread.sleep(3000);
		String current = driver.getCurrentUrl();
		Assert.assertEquals(driver.getCurrentUrl(), "https://staging.igstg.com/payment/thank-you");  
	  }
	  
	
	@Test (priority = 9, dependsOnMethods = "validateThreeDigitCVV")
	public void validateMascotIconPresence() {

		//driver.findElement(By.xpath("//*[@class='thankyou-mascot']")).isDisplayed();
		Assert.assertEquals(driver.findElement(By.xpath("//*[@class='thankyou-mascot']")).isDisplayed(), true);		  
	}
	
	@Test (priority = 10, dependsOnMethods = "validateThreeDigitCVV")
	public void validateUserNameInSuccessMessage() {
		//*[@class="thankyou-mascot"]//parent::div//following-sibling::div[1]//div[1]
		String obtained = driver.findElement(By.xpath("//*[@class='thankyou-mascot']//parent::div//following-sibling::div[1]//div[1]")).getText();

			if(obtained.contains(prop.getProperty("donor_name"))) {
				System.out.println("Donor Name Matched");
			} else {
				System.out.println("Donor Name Not Matched");
			}
	}

}