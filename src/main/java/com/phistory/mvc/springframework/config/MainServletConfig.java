package com.phistory.mvc.springframework.config;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.tcp.data.mvc.springframework.config.SqlDatabaseConfig;

import freemarker.template.TemplateException;

/**
 * Configuration class for creating the main servlet's beans 
 * 
 * @author gonzalo
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.phistory.mvc.controller")
@Import(SqlDatabaseConfig.class)
public class MainServletConfig extends WebMvcConfigurerAdapter
{		
	@Bean(name="viewResolver", autowire=Autowire.BY_NAME)
	public FreeMarkerViewResolver internalResourceViewResolver()
	{
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		viewResolver.setExposeSpringMacroHelpers(true); 
		viewResolver.setContentType("text/html;charset=UTF-8");
		
	    return viewResolver;
	}
	
	@Bean
	public FreeMarkerConfigurer freemarkerConfigurer() throws IOException, TemplateException
	{
		FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
		freemarkerConfigurer.setTemplateLoaderPaths("WEB-INF/freemarker/", "WEB-INF/freemarker/cms");
		freemarkerConfigurer.setDefaultEncoding("UTF-8");
		
		return freemarkerConfigurer; 
	}	
	
	@Bean
	public ResourceBundleMessageSource messageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(); 
		messageSource.setBasename("com/phistory/textsource/textSources");
		
		return messageSource;
	}
	
	@Bean
	public ResourceBundleMessageSource validationMessageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(); 
		messageSource.setBasenames("/com/phistory/textsource/validationSources");
		
		return messageSource;
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		configurer.favorPathExtension(false);
		configurer.favorParameter(true);
		configurer.parameterName("mediaType");
		configurer.ignoreAcceptHeader(true);
		configurer.useJaf(false);
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
	}
	
	@Bean(name = "previewPictureRandomGenerator")	
	public Random randomGenerator()
	{		
		Random randomGenerator = new Random();
		
		return randomGenerator;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter();
	}
}
