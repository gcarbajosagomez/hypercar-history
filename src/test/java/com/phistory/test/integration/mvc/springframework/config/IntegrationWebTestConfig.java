package com.phistory.test.integration.mvc.springframework.config;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.phistory.test.integration.web.login.LoginPage;

@Configuration
public class IntegrationWebTestConfig
{
	@Bean
	public ResourceBundleMessageSource messageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(); 
		messageSource.setBasename("com/phistory/textsource/textSources");
		
		return messageSource;
	}
	
	@Bean
	public LoginPage loginPage() throws MalformedURLException
	{
		WebDriver webDriver = new FirefoxDriver();
		
		return new LoginPage(webDriver);
	}
}
