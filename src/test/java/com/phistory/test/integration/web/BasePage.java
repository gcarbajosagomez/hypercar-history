package com.phistory.test.integration.web;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

public abstract class BasePage
{
	@Inject
	private WebDriver webDriver;
	@Inject
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
