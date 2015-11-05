package com.phistory.mvc.springframework.config;


import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.phistory.mvc.springframework.config.AppConfig;
import com.phistory.mvc.springframework.config.MainServletConfig;

/**
 * Context initializer class
 * 
 * @author gonzalo
 *
 */
public class ApplicationContextInitializer implements WebApplicationInitializer
{
	@Override
    public void onStartup(ServletContext container) throws ServletException
	{
		// Create the 'root' Spring application context
	    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	    rootContext.register(AppConfig.class, WebSecurityConfig.class);

	    // Manage the lifecycle of the root application context
	    container.addListener(new ContextLoaderListener(rootContext));
	      
	    // security filter
	    FilterRegistration.Dynamic securityFilter = container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
	    securityFilter.addMappingForUrlPatterns(null, false, "*.html");

	    // Create the dispatcher servlet's Spring application context
	    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	    dispatcherContext.register(MainServletConfig.class);

	    // Register and map the dispatcher servlet
	    ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
	    dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("*.html");
    }
}
