package com.phistory.test.integration.mvc.springframework.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

/*@PropertySource("classpath:/com/phistory/systemproperty/dev/systemProperties.properties")
@Configuration
@ComponentScan(basePackages = "com.phistory.test.integration.web")*/
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
	public WebDriver webDriver()
	{
		return new FirefoxDriver();
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfigIn()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
