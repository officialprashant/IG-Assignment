package com.ImpactGuru.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ImpactGuru.base.TestBase;

public class Login extends TestBase {


	@FindBy(xpath = "//button[@id='auth-local-btn']")
	WebElement loginSection;

	@FindBy(id = "username")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "submit")
	WebElement submitButton;

	public Login() {
		PageFactory.initElements(driver, this);
	}

	
	// ------ Return methods ------
	public WebElement loginArea() {
		return loginSection;
	}

	public WebElement Username() {
		return username;
	}

	public WebElement Password() {
		return password;
	}

	public WebElement SubmitBUtton() {
		return submitButton;
	}

}
