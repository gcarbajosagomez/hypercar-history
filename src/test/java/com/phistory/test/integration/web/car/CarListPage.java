package com.phistory.test.integration.web.car;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

import com.phistory.test.integration.web.BasePage;

@Component
public class CarListPage extends BasePage
{
	@Getter
	@FindBy(id = "main-car-list-div")
	private WebElement carListLocator;
	@FindBy(id = "pagination-row-div")
	private WebElement paginationDivLocator;
	@FindBy(id = "pagination-ul")
	private WebElement paginationLocator;

	@Override
	public void initializePage() throws Exception
	{
		getWebDriver().get(getBaseUrl() + "/cars.html");
        
		String loginPageTitle = getMessageSource().getMessage("title.allModels", null, Locale.ENGLISH);
		
		if (loginPageTitle.contains(getWebDriver().getTitle()))
		{
            throw new IllegalStateException("This is not the car list page");
        }
		
		initializePageElements();
	}
	
	public boolean isMainCarListDivPresent()
	{
		return carListLocator.isDisplayed();
	}
	
	public boolean isPaginationDivPresent()
	{
		return paginationDivLocator.isDisplayed();
	}
	
	public boolean isPaginatorPresent()
	{
		return paginationLocator.isDisplayed();
	}
	
	public boolean paginatorHasPages()
	{
		List<WebElement> pageSelectors = paginationLocator.findElements(By.cssSelector("li"));
		
		return pageSelectors.parallelStream().anyMatch(page -> page.getAttribute("class").equals("active"));
	}
	
	public CarListPage paginate()
	{
		List<WebElement> inactivePageSelectors = paginationLocator.findElements(By.cssSelector("li"))
														  		  .parallelStream().filter(page -> !page.getAttribute("class").equals("active"))
														  		  .collect(Collectors.toList());
		
		inactivePageSelectors.get(0).click();
		
		return null;
	}
}
