package com.phistory.test.integration.web.login;

import java.util.Locale;

import javax.inject.Inject;

import lombok.NoArgsConstructor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ResourceBundleMessageSource;

@NoArgsConstructor
public class LoginPage implements InitializingBean
{
	private WebDriver webDriver;
	@Inject
	private ResourceBundleMessageSource messageSource;
	@FindBy(id = "username-input")
	private WebElement usernameLocator;
	@FindBy(id = "password-input")
	private WebElement passwordLocator;
	@FindBy(id = "login-button")
	private WebElement loginButtonLocator;
	@FindBy(id = "logout-button")
	private WebElement logoutButtonLocator;
	
	public LoginPage(WebDriver webDriver) 
	{
        this.webDriver = webDriver;
        webDriver.get("http://localhost:8080/pagani-history-web/cms/login.html");
        PageFactory.initElements(this.webDriver, this);
    }

    public LoginPage typeUsername(String username)
    {
    	usernameLocator.sendKeys(username);
        return this;    
    }
    
    public LoginPage typePassword(String password)
    {
    	passwordLocator.sendKeys(password);
        return this;    
    }
    
    public LoginPage clickLoginButton()
    {
    	loginButtonLocator.click();
        return this;    
    }

	@Override
	public void afterPropertiesSet() throws Exception
	{
		String loginPageTitle = messageSource.getMessage("title.login", null, Locale.ENGLISH);
		
		if (loginPageTitle.contains(webDriver.getTitle()))
		{
            throw new IllegalStateException("This is not the login page");
        }
	}
}
