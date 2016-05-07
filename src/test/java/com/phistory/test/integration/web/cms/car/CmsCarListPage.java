package com.phistory.test.integration.web.cms.car;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

@Slf4j
public class CmsCarListPage extends BasePage
{
	@Getter
	@FindBy(id = "main-car-list-div")
	private WebElement carListLocator;
	
	public CmsCarListPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isMainCarListDivPresent()
	{
		try
		{
			return carListLocator.isDisplayed();
		} 
		catch(Exception e)
		{
			log.error(e.toString());
			
			return false;
		}
	}
}
