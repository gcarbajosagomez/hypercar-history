package com.phistory.test.integration.web.cms.car;

import java.util.Locale;

import lombok.Getter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

import com.phistory.test.integration.web.BasePage;

@Component
public class CmsCarListPage extends BasePage
{
	@Getter
	@FindBy(id = "main-car-list-div")
	private WebElement carListLocator;

	@Override
	public void initializePage() throws Exception
	{
		getWebDriver().get(getBaseUrl() + "/cms/login.html");
        
		String loginPageTitle = getMessageSource().getMessage("title.allModels", null, Locale.ENGLISH);
		
		if (loginPageTitle.contains(getWebDriver().getTitle()))
		{
            throw new IllegalStateException("This is not the CMS car list page");
        }
		
		initializePageElements();
	}
	
	public boolean isMainCarListDivPresent()
	{
		return carListLocator.isDisplayed();
	}
}
