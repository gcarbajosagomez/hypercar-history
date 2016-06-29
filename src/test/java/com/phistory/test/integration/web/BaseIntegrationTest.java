package com.phistory.test.integration.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public class BaseIntegrationTest extends AbstractTestNGSpringContextTests
{
	public static final String TEST_SERVER_HOST = "http://localhost:";	
	private static final String CHROME_DRIVER_SYSTEM_PROPERTY_NAME = "webdriver.chrome.driver";	
	protected WebDriver webDriver;
	
	public void before() throws Exception
	{		
		System.setProperty(CHROME_DRIVER_SYSTEM_PROPERTY_NAME, "src/test/resources/com/phistory/test/integration/web/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		this.webDriver = new ChromeDriver(options);
	}
}
