package com.phistory.test.integration.web;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public class BasePage
{	
	protected static final Long WAIT_UNTIL_ELEMENT_CLICKABLE_SECONDS = 2L;
	private WebDriver webDriver;	
	
	public BasePage(WebDriver webDriver)
	{
		this.webDriver = webDriver;
	}

	public void initializePageElements()
	{
		try
		{
			PageFactory.initElements(getWebDriver(), this);	
		} catch (Exception e) {
			log.error("There was an error while initializing page web elements", e);
		}	
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}
}
