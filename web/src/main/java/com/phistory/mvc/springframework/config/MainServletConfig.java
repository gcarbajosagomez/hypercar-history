package com.phistory.mvc.springframework.config;

import com.phistory.data.mvc.springframework.config.SqlDatabaseConfig;
import com.phistory.mvc.language.Language;
import com.phistory.mvc.springframework.filter.PathVariableLocaleFilter;
import com.phistory.mvc.springframework.interceptor.LocaleChangeInterceptor;
import freemarker.template.TemplateException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static com.phistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.phistory.mvc.command.PictureLoadAction.LOAD_CAR_PREVIEW;
import static com.phistory.mvc.controller.BaseControllerData.*;
import static java.util.Locale.ENGLISH;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Configuration class for creating the main servlet's beans
 *
 * @author gonzalo
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.phistory.mvc"})
@Import(SqlDatabaseConfig.class)
@PropertySource(value = "classpath:com/phistory/systemproperty/application.properties")
public class MainServletConfig extends WebMvcConfigurerAdapter {

    private static final String  NO_CACHE_VALUE                  = "0";
    private static final Integer TWO_WEEKS_SECONDS                = 1209600;
    private static final Integer THIRTY_MINUTES_SECONDS           = 1800;
    private static final String  ROBOTS_FILE_NAME                 = "robots.txt";
    private static final String  SITEMAP_FILE_NAME                = "sitemap.xml";
    private static final String  FAVICON_FILE_NAME                = "favicon.ico";
    private static final String  DEFAULT_TIMEZONE                 = "UTC";
    private static final String  FREEMARKER_ENCODING              = "UTF-8";
    private static final String  FREEMARKER_TEMPLATE_FILES_SUFFIX = ".ftl";

    @Bean
    public FreeMarkerViewResolver internalResourceViewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(FREEMARKER_TEMPLATE_FILES_SUFFIX);
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setContentType("text/html;charset=" + FREEMARKER_ENCODING);

        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer freemarkerConfigurer = new FreeMarkerConfigurer();
        freemarkerConfigurer.setTemplateLoaderPaths("classpath:/com/phistory/freemarker/");
        freemarkerConfigurer.setDefaultEncoding(FREEMARKER_ENCODING);

        Properties properties = new Properties();
        properties.setProperty("number_format", "0.##");
        properties.setProperty("time_zone", DEFAULT_TIMEZONE);
        properties.setProperty("sql_date_and_time_time_zone", DEFAULT_TIMEZONE);
        freemarkerConfigurer.setFreemarkerSettings(properties);

        return freemarkerConfigurer;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("com/phistory/textsource/textSources");
        return messageSource;
    }

    @Bean(name = "previewPictureRandomGenerator")
    public Random randomGenerator() {
        return new Random();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor(this.localeResolver());
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(ENGLISH);
        //in order for the cookie to be accessible via Javascript for example
        localeResolver.setCookieHttpOnly(false);
        localeResolver.setCookieName(LANGUAGE_COOKIE_NAME);
        localeResolver.setCookieSecure(false);
        localeResolver.setCookiePath("/");
        localeResolver.setCookieMaxAge(TWO_WEEKS_SECONDS);

        return localeResolver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PathVariableLocaleFilter());
        registration.addUrlPatterns("*");
        registration.addInitParameter(LANGUAGE_DATA, Language.ENGLISH.getIsoCode());
        registration.setName("localeFilter");
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
        configurer.favorParameter(true);
        configurer.parameterName("mediaType");
        configurer.ignoreAcceptHeader(true);
        configurer.useJaf(false);
        configurer.defaultContentType(APPLICATION_JSON);
        configurer.mediaType("json", APPLICATION_JSON);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        Properties cacheMappings = new Properties();
        cacheMappings.setProperty("/" + CARS_URL, NO_CACHE_VALUE);
        cacheMappings.setProperty("/" + CARS_URL + "/*", NO_CACHE_VALUE);
        cacheMappings.setProperty("/" + SEARCH_URL + "/*", NO_CACHE_VALUE);
        cacheMappings.setProperty("/" + PICTURES_URL + "/" + LOAD_CAR_PICTURE.getName() + "*", TWO_WEEKS_SECONDS.toString());
        cacheMappings.setProperty("/" + PICTURES_URL + "/" + LOAD_CAR_PREVIEW.getName() + "/*", NO_CACHE_VALUE);
        webContentInterceptor.setCacheMappings(cacheMappings);

        registry.addInterceptor(this.localeChangeInterceptor());
        registry.addInterceptor(webContentInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //so that the content of the resources directory is served as static content
        registry.addResourceHandler(STATIC_RESOURCES_URI + "**")
                .addResourceLocations("classpath:" + STATIC_RESOURCES_URI)
                .setCachePeriod(THIRTY_MINUTES_SECONDS);

        registry.addResourceHandler("/" + ROBOTS_FILE_NAME)
                .addResourceLocations("classpath:" + STATIC_RESOURCES_URI + ROBOTS_FILE_NAME);

        registry.addResourceHandler("/" + SITEMAP_FILE_NAME)
                .addResourceLocations("classpath:" + STATIC_RESOURCES_URI + SITEMAP_FILE_NAME);

        registry.addResourceHandler("/" + FAVICON_FILE_NAME)
                .addResourceLocations("classpath:" + STATIC_RESOURCES_URI + "img/" + FAVICON_FILE_NAME);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }
}
