package com.phistory.mvc.springframework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.phistory.mvc.controller.cms.CmsBaseController.*;

/**
 * Configuration class for setting up the application's security config.
 * 
 * @author gonzalo
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	public static final String CMS_LOGIN_USER		= "admin";
	public static final String CMS_LOGIN_PASSWORD	= "NederlandsLesVanavond2016";
	
	@Override
    protected void configure(HttpSecurity http) throws Exception
	{        
		http        
        .authorizeRequests()
        	.antMatchers("/*").permitAll()
            .antMatchers("/" + CMS_CONTEXT + "*").authenticated()
            .antMatchers("/" + CMS_CONTEXT + "*/*").authenticated()
            .antMatchers("/" + CMS_CONTEXT + "*/*/*").authenticated().and()
        .formLogin()
        	.loginPage("/" + CMS_CONTEXT + LOGIN_URL)
        	.defaultSuccessUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGIN_SUCCESS)
            .failureUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGIN_ERROR)
            .permitAll().and()
		.logout()
			.logoutUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGOUT)
			.logoutSuccessUrl("/" + CMS_CONTEXT + LOGIN_URL)
			.permitAll();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		 	.withUser(CMS_LOGIN_USER)
		 	.password(CMS_LOGIN_PASSWORD)
		 	.roles("USER");
    }
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
}
