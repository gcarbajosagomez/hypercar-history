package com.phistory.mvc.springframework.config;

import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_DATA;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
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
@ComponentScan(basePackages = {"com.phistory.mvc"})
@Import(SqlDatabaseConfig.class)
@PropertySource(value= "classpath:com/phistory/systemproperty/systemProperties.properties", ignoreResourceNotFound= false)
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
		freemarkerConfigurer.setTemplateLoaderPaths("classpath:/com/phistory/freemarker/");
		freemarkerConfigurer.setDefaultEncoding("UTF-8");
		
		Properties properties = new Properties();
		properties.setProperty("number_format", "0.##");
		freemarkerConfigurer.setFreemarkerSettings(properties);
		
		return freemarkerConfigurer; 
	}	
	
	@Bean
	public ResourceBundleMessageSource messageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(); 
		messageSource.setBasenames("com/phistory/textsource/textSources",
								   "com/phistory/textsource/validationSources");		
		return messageSource;
	}
	
	@Bean(name = "previewPictureRandomGenerator")	
	public Random randomGenerator()
	{		
		return new Random();
	}
	
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
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{		
		return new BCryptPasswordEncoder(11);
	}	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		configurer.favorPathExtension(false);
		configurer.favorParameter(true);
		configurer.parameterName("mediaType");
		configurer.ignoreAcceptHeader(true);
		configurer.useJaf(false);
		configurer.defaultContentType(APPLICATION_JSON);
		configurer.mediaType("json", APPLICATION_JSON);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(LANGUAGE_DATA);
		
		registry.addInterceptor(localeChangeInterceptor);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
        //so that the content of the resources directory is served as static content
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/static/robots.txt");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/img/favicon.ico");
    }
}
