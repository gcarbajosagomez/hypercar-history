package com.phistory.test.integration.web.cookiespolicy;

import com.phistory.test.integration.web.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CookiesPolicyPage extends BasePage
{
	@FindBy(id = "cookiesdirective")
	private WebElement cookiesDirectiveMessageDiv;
	@FindBy(id = "epdnotick")
	private WebElement youMustTickCheckBox;	
	@FindBy(id = "epdagree")
	private WebElement acceptCookiesCheckBox;
	@FindBy(id = "explicitsubmit")
	private WebElement acceptCookiesButton;
	
	public CookiesPolicyPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();
	}
	
	public boolean isCookiesDirectiveMessageDivDisplayed()
	{
		try 
		{
			return this.cookiesDirectiveMessageDiv.isDisplayed();
		}
		catch(NoSuchElementException nsee)
		{
			return false;
		}
		
	}
	
	public boolean isYouMustTickCheckBoxDisplayed()
	{
		return this.youMustTickCheckBox.isDisplayed();
	}
	
	public boolean isAcceptCookiesCheckBox()
	{
		return this.acceptCookiesCheckBox.isDisplayed();
	}
	
	public boolean isAcceptCookiesButtonDisplayed()
	{
		return this.acceptCookiesButton.isDisplayed();
	}
	
	public void clickAcceptCookiesCheckbox()
	{
		this.acceptCookiesCheckBox.click();
	}
	
	public void clickAcceptCookiesButton()
	{
		this.acceptCookiesButton.click();
	}
}
