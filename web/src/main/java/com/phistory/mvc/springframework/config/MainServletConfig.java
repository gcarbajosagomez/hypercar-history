package com.phistory.mvc.springframework.config;

import com.phistory.data.mvc.springframework.config.SqlDatabaseConfig;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static com.phistory.mvc.controller.BaseControllerData.*;
import static java.util.Locale.ENGLISH;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.springframework.http.MediaType.APPLICATION_JSON;

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
    private static final Integer TWO_WEEKS_SECONDS = 1209600;

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
		messageSource.setBasenames("com/phistory/textsource/textSources");
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
		localeResolver.setDefaultLocale(ENGLISH);
		//in order for the cookie to be accessible via Javascript for example
		localeResolver.setCookieHttpOnly(false);
		localeResolver.setCookieName("def_lang");
		localeResolver.setCookieSecure(false);
		localeResolver.setCookiePath("/");
		//2 weeks
		localeResolver.setCookieMaxAge(TWO_WEEKS_SECONDS);
		
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
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheControl(CacheControl.maxAge(30, MINUTES));
		Properties cacheMappings = new Properties();
        cacheMappings.setProperty("/" + CARS_URL + "/*", "-1");
        cacheMappings.setProperty("/" + PICTURES_URL + "/*", TWO_WEEKS_SECONDS.toString());
        webContentInterceptor.setCacheMappings(cacheMappings);

		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(webContentInterceptor);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
        //so that the content of the resources directory is served as static content
		registry.addResourceHandler(STATIC_RESOURCES_URI + "**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:" + STATIC_RESOURCES_URI + "robots.txt");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:" + STATIC_RESOURCES_URI + "img/favicon.ico");
    }
}
