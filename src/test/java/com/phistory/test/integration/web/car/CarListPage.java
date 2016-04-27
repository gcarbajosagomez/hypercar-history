package com.phistory.test.integration.web.car;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class CarListPage extends BasePage
{
	@FindBy(id = "main-car-list-div")
	private WebElement carListLocator;
	@FindBy(id = "car-list-row")
	private WebElement carListRowLocator;
	@FindBy(id = "pagination-row-div")
	private WebElement paginationDivLocator;
	@FindBy(id = "pagination-ul")
	private WebElement paginationLocator;
	
	public CarListPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isMainCarListDivPresent()
	{
		return this.carListLocator.isDisplayed();
	}
	
	public boolean isPaginationDivPresent()
	{
		return this.paginationDivLocator.isDisplayed();
	}
	
	public boolean isPaginatorPresent()
	{
		return this.paginationLocator.isDisplayed();
	}
	
	public boolean paginatorHasPages()
	{
		List<WebElement> pageSelectors = this.paginationLocator.findElements(By.cssSelector("li"));
		
		return pageSelectors.stream().anyMatch(page -> page.getAttribute("class").equals("active"));
	}
	
	public void paginate(int pageNumber)
	{
		Optional<WebElement> inactivePageSelectors = this.paginationLocator.findElements(By.cssSelector("li"))
														  		       	   .stream()
														  		           .filter(page -> !page.getAttribute("class").equals("active"))
														  		           .filter(page -> page.getText().equals(String.valueOf(pageNumber)))
														  		           .findFirst();
		if (inactivePageSelectors.isPresent())
		{
			inactivePageSelectors.get().findElement(By.cssSelector("a")).click();
		}
	}
	
	public String getFirstCarListedDivId() {
		Optional<WebElement> firstCarDiv = this.carListRowLocator.findElements(By.cssSelector("div")).stream().findFirst();
		
		if (firstCarDiv.isPresent())
		{
			return firstCarDiv.get().getAttribute("id");
		}
		
		return null;
	}
}
