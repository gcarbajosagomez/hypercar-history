package com.hhistory.test.integration.web.footer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hhistory.test.integration.web.BasePage;

public class FooterPage extends BasePage
{	
	private static final Integer PIXELS_TO_SCROLL_DOWN = 800;
	
	@FindBy(id = "contact-us-link")
	private WebElement contactUsLink;
	@FindBy(id = "technology-stack-link")
	private WebElement technologyStackLink;
	
	public FooterPage(WebDriver webDriver)
	{
		super(webDriver);
		super.initializePageElements();
	}
	
	public void clickContactUsLink()
	{
		JavascriptExecutor jsExecutor = (JavascriptExecutor) super.getWebDriver();
		jsExecutor.executeScript("window.scrollBy(0, 800)", "");
		this.contactUsLink.click();
	}
	
	public void clickTechnologyStackLink()
	{
		JavascriptExecutor jsExecutor = (JavascriptExecutor) super.getWebDriver();
		jsExecutor.executeScript("window.scrollBy(0, " + PIXELS_TO_SCROLL_DOWN + ")", "");
		this.technologyStackLink.click();
	}
}
