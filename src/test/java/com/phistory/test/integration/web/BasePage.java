package com.phistory.test.integration.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

public abstract class BasePage
{	
	private WebDriver webDriver = new FirefoxDriver();
	
	private ResourceBundleMessageSource messageSource;
	@Value("${page.baseUrl}")
	private String baseUrl;
	
	public abstract void initializePage() throws Exception;
	
	public void initializePageElements() throws Exception
	{
		PageFactory.initElements(getWebDriver(), this);	
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}
	
	protected ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	protected String getBaseUrl() {
		return baseUrl;
	}
}
