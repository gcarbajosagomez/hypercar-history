package com.phistory.test.integration.web.footer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class FooterPage extends BasePage
{	
	@FindBy(id = "contact-us-link")
	private WebElement contactUsLink;
	
	public FooterPage(WebDriver webDriver)
	{
		super(webDriver);
		super.initializePageElements();
	}
	
	public void clickContactUsLink()
	{
		this.contactUsLink.click();
	}
}