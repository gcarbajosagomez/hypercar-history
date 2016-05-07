package com.phistory.test.integration.web.cms.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class LoginPage extends BasePage
{
	@FindBy(id = "username-input")
	private WebElement usernameLocator;
	@FindBy(id = "password-input")
	private WebElement passwordLocator;
	@FindBy(id = "login-button")
	private WebElement loginButtonLocator;
	@FindBy(id = "logout-button")
	private WebElement logoutButtonLocator;
	@FindBy(className = "alert-danger")
	private WebElement wrongCredentialsAlert;
	
	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isUsernameInputDisplayed()
	{
		return this.usernameLocator.isDisplayed();
	}
	
	public boolean isPasswordInputDisplayed()
	{
		return this.passwordLocator.isDisplayed();
	}
	
	public boolean isLoginButtonDisplayed()
	{
		return this.loginButtonLocator.isDisplayed();
	}

    public void typeUsername(String username)
    {
    	this.usernameLocator.sendKeys(username);
    }
    
    public void typePassword(String password)
    {
    	this.passwordLocator.sendKeys(password);
    }
    
    public void clickLoginButton()
    {
    	this.loginButtonLocator.click();
    }
    
    public void clickLogoutButton()
    {
    	this.loginButtonLocator.click();
    }
	
	public boolean isWrongCredentialsAlertDisplayed()
	{
		return this.wrongCredentialsAlert.isDisplayed();
	}
}
