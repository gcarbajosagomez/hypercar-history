package com.phistory.test.integration.web.footer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class TechnologyStackPage extends BasePage
{
	@FindBy(id = "technology-stack-modal-div")
	private WebElement technologyStackModalDiv;
	@FindBy(id = "dismiss-technology-stack-modal-button")
	private WebElement dismissTechnologyStackButton;
	
	public TechnologyStackPage(WebDriver webDriver)
	{
		super(webDriver);
		super.initializePageElements();
	}
	
	public boolean isTechnologyStackModalDivDisplayed()
	{
		return this.technologyStackModalDiv.isDisplayed();
	}
	
	public boolean isDismissTechnologyStackModalButtonDisplayed()
	{
		try
		{
			return this.dismissTechnologyStackButton.isDisplayed();
		} 
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void clickDismissTechnologyStackModalButton()
	{
		this.dismissTechnologyStackButton.click();
	}
}
