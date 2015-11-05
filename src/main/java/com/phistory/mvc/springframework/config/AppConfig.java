package com.phistory.mvc.springframework.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Configuration class for creating the common beans to the whole application
 * 
 * @author gonzalo
 *
 */
@Configuration
@EnableWebMvc
@PropertySource(value= "classpath:com/phistory/systemproperty/systemProperties.properties", ignoreResourceNotFound= false)
public class AppConfig extends WebMvcConfigurerAdapter
{		
	@Bean
	public LocaleResolver localeResolver()
	{
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		//in order for the cookie to be accessible via Javascript for example
		localeResolver.setCookieHttpOnly(false);
		localeResolver.setCookieName("def_lang");
		localeResolver.setCookieSecure(false);
		localeResolver.setCookiePath("/");
		//2 weeks
		localeResolver.setCookieMaxAge(1209600);
		
		return localeResolver;
	}		
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	private LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		
		return localeChangeInterceptor;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
        //so that the content of the resources directory is served as static content
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}