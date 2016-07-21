package com.phistory.test.integration.web.cms.car;

import com.phistory.test.integration.web.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class CmsCarListPage extends BasePage
{
	@FindBy(id = "main-car-list-div")
	private WebElement mainCarListDivLocator;
	
	public CmsCarListPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isMainCarListDivPresent()
	{
		try
		{
			return this.mainCarListDivLocator.isDisplayed();
		} 
		catch(Exception e)
		{
			log.error(e.toString());
			return false;
		}
	}
}
