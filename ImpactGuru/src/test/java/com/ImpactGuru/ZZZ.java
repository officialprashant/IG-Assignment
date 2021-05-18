package com.ImpactGuru;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ImpactGuru.base.TestBase;
import com.ImpactGuru.pages.Login;

public class ZZZ extends TestBase {


	public ZZZ() {
		super();
	}

	Login lp = new Login();
	LocalDate today = LocalDate.now();
	int month = today.getMonthValue();
	int year = today.getYear();
	
	@Test
	public void test() throws InterruptedException {
		driver.findElement(By.xpath("//button[@id='auth-local-btn']")).click();
		driver.findElement(By.id("username")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.id("submit")).click();
		Thread.sleep(2000);

		// ----- LOGIN TO STAGING SERVER ------
		driver.get("https://impactguru:8nvbil51pp@staging.igstg.com/fundraiser/help-testdocumentchecklist");
		
		driver.findElement(By.xpath("//a[@id='cmp-nfr-top-side-donate']")).click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("#story_popup_donation_amount")).clear();
		driver.findElement(By.cssSelector("#story_popup_donation_amount")).sendKeys("1000");

		Select sc = new Select(driver.findElement(By.xpath("//select[@id='ig_tips_dropdown']")));
		//sc.selectByVisibleText(text);
		driver.findElement(By.xpath("//*[@id='full_name']")).sendKeys("OMKAR");
		driver.findElement(By.cssSelector("#email_receipt")).sendKeys("om@gmali.com");
		driver.findElement(By.cssSelector("#mobile")).sendKeys("8000000000");
		driver.findElement(By.cssSelector("#city_text")).sendKeys("Mumbai");
		driver.findElement(By.cssSelector("#story-popup-donate-button")).click();
		
		
		  // -- enter card number and click DONATE --
		  //driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		  driver.findElement(By.cssSelector("#cardNumber")).sendKeys("5555555555554444");
		 
		  driver.findElement(By.cssSelector("#expiryDate")).clear();
		  driver.findElement(By.cssSelector("#expiryDate")).sendKeys(month + "/21");
		  
		  driver.findElement(By.cssSelector("#cvvNumber")).sendKeys("222");
		  driver.findElement(By.xpath("//button[@id='donate-card-contribute']")).click();
		  
		  Thread.sleep(3000);
		  String current = driver.getCurrentUrl();
		  //Assert.assertEquals(driver.getCurrentUrl(), "https://staging.igstg.com/payment/thank-you");
		 // Assert.assertEquals(driver.getCurrentUrl(), "https://staging.igstg.com/payment/thank-you");
		  String obtained = driver.findElement(By.xpath("//*[@class='thankyou-mascot']//parent::div//following-sibling::div[1]//div[1]")).getText();
		System.out.println("Obtained="+obtained);
		if(obtained.contains("OMKAR")) {
			System.out.println("hogaya match");
		} else {
			System.out.println("GADBADD");
		}
			
	}
}
