package com.hhistory.test.integration.web.car;

import com.hhistory.test.integration.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class CarListPage extends BasePage
{
    private static final String LI_CSS_SELECTOR = "li";
    private static final String CARS_PER_PAGE_OPTIONS_CSS_SELECTOR = "a[role='menuitem']";

    @FindBy(id = "main-car-list-div")
	private WebElement carListLocator;
	@FindBy(className = "car-list-row")
	private WebElement carListRowLocator;
	@FindBy(id = "pagination-row-div")
	private WebElement paginationDivLocator;
	@FindBy(id = "pagination-ul")
	private WebElement paginationLocator;
    @FindBy(id = "cars-per-page-dropdown")
    private WebElement carsPerPageDropDownLocator;
    @FindBy(className = "dropdown-menu-right")
    private WebElement carsPerPageDropDownMenuLocator;

    public CarListPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isMainCarListDivDisplayed()
	{
		return this.carListLocator.isDisplayed();
	}
	
	public boolean isPaginationDivDisplayed()
	{
		return this.paginationDivLocator.isDisplayed();
	}
	
	public boolean isPaginatorDisplayed()
	{
		return this.paginationLocator.isDisplayed();
	}
	
	public boolean paginatorHasPages()
	{
		List<WebElement> pageSelectors = this.paginationLocator.findElements(By.cssSelector(LI_CSS_SELECTOR));
		
		return pageSelectors.stream().anyMatch(page -> page.getAttribute("class").equals("active"));
	}
	
	public void paginate(int pageNumber)
	{
		Optional<WebElement> inactivePageSelectors = this.paginationLocator.findElements(By.cssSelector(LI_CSS_SELECTOR))
														  		       	   .stream()
														  		           .filter(page -> !page.getAttribute("class").equals("active"))
														  		           .filter(page -> page.getText().equals(String.valueOf(pageNumber)))
														  		           .findFirst();
		inactivePageSelectors.ifPresent(webElement -> webElement.findElement(By.cssSelector("a")).click());
	}
	
	public String getFirstCarListedDivId() {
		Optional<WebElement> firstCarDiv = this.carListRowLocator.findElements(By.cssSelector("div"))
		                                                         .stream()
		                                                         .findFirst();

		return firstCarDiv.map(webElement -> webElement.getAttribute("id"))
		                  .orElse(null);

	}

    public int getNumberOfCarsDisplayed() {
        return this.carListLocator.findElements(By.className("preview-outer")).size();
    }

    public boolean isCarsPerPageDropDownDisplayed() {
        return this.carsPerPageDropDownLocator.isDisplayed();
    }

    public void clickCarsPerPageDropDownButton() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) super.getWebDriver();
        jsExecutor.executeScript("window.scrollBy(0, 2500)", "");
        this.carsPerPageDropDownLocator.click();
    }

    public int getNumberOfCarsPerPageOptionsDisplayed() {
        return this.carsPerPageDropDownMenuLocator.findElements(By.cssSelector(CARS_PER_PAGE_OPTIONS_CSS_SELECTOR)).size();
    }

    public void clickNumberOfCarsPerPageOptionByValue(String value) {
        List<WebElement> options = this.carsPerPageDropDownMenuLocator.findElements(By.cssSelector(CARS_PER_PAGE_OPTIONS_CSS_SELECTOR));
        options.stream().filter(option -> option.getText().equals(value)).findFirst().get().click();
    }
}
