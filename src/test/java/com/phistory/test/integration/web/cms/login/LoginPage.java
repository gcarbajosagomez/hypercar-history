package com.phistory.test.integration.web.cms.login;

import java.util.Locale;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

import com.phistory.test.integration.web.BasePage;

@Component
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
	
	@Override
	public void initializePage() throws Exception
	{
		getWebDriver().get(getBaseUrl() + "/cms/login.html");
     
        String loginPageTitle = getMessageSource().getMessage("title.login", null, Locale.ENGLISH);
		
		if (loginPageTitle.contains(getWebDriver().getTitle()))
		{
            throw new IllegalStateException("This is not the CMS login page");
        }
		
		initializePageElements();
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
    
    public LoginPage clickLogoutButton()
    {
    	loginButtonLocator.click();
        return this;    
    }
}
