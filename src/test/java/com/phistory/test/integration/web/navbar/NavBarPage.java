package com.phistory.test.integration.web.navbar;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class NavBarPage extends BasePage
{	
	@FindBy(id = "main-navbar-collapse")
	private WebElement mainNavBar;
	@FindBy(id = "language-dropdown-toggle")
	private WebElement languageDrowpDownToggle;
	@FindBy(id = "spanish-language-link")
	private WebElement spanishLanguageLink;
	@FindBy(id = "english-language-link")
	private WebElement englishLanguageLink;
	@FindBy(className = "search-icon")
	private WebElement contentSearchIcon;
	@FindBy(id = "content-search-input")
	private WebElement contentSearchInput;

	public NavBarPage(WebDriver webDriver)
	{
		super(webDriver);
		super.initializePageElements();		
	}
	
	public boolean isMainNavBarDisplayed()
	{
		return this.mainNavBar.isDisplayed();
	}
	
	public void clickLanguageDropDownToggle()
	{
		this.languageDrowpDownToggle.click();
	}
	
	public boolean isSpanishLanguageLinkDisplayed()
	{
		return this.spanishLanguageLink.isDisplayed();
	}
	
	public void clickSpanishLanguageLink()
	{
		this.spanishLanguageLink.click();
	}
	
	public boolean isEnglishLanguageLinkDisplayed()
	{
		return this.englishLanguageLink.isDisplayed();
	}
	
	public void clickEnglishLanguageLink()
	{
		this.englishLanguageLink.click();
	}
	
	public boolean isContentSearchIconDisplayed()
	{
		return this.contentSearchIcon.isDisplayed();
	}
	
	public void clickContentSearchIcon()
	{
		this.contentSearchIcon.click();
	}
	
	public boolean isContentSearchInputDisplayed()
	{
		return this.contentSearchInput.isDisplayed();
	}
	
	public void typeContentToSearch(String contentToSearch)
	{
		this.contentSearchInput.sendKeys(contentToSearch);
	}
	
	public void performSearchContent()
	{
		this.contentSearchInput.sendKeys(Keys.ENTER);
	}
}
