package com.phistory.mvc.springframework.config;

import static com.phistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.phistory.mvc.controller.BaseControllerData.*;
import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CMSBaseController.LOGIN_ERROR;
import static com.phistory.mvc.controller.cms.CMSBaseController.LOGIN_SUCCESS;
import static com.phistory.mvc.controller.cms.CMSBaseController.LOGIN_URL;
import static com.phistory.mvc.controller.cms.CMSBaseController.LOGOUT;
import static com.phistory.mvc.controller.cms.CMSBaseController.QUERY_STRING_SEPARATOR;

import javax.inject.Inject;

import com.phistory.mvc.command.PictureLoadAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up the application's security config.
 * 
 * @author gonzalo
 *
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	public static final String 	CMS_LOGIN_USER					= "admin";
	public static final String 	USER    						= "USER";
	public static final String 	USER_ROLE						= "ROLE_USER";
	private static final String CMS_LOGIN_ENCRYPTED_PASSWORD	= "$2a$11$7FwmOPUQFJL.vrbS0xNETeAGU/4QlpWOuRM8Q8gD9lQlkM7MQGrHS";
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception
	{        
		http        
        .authorizeRequests()
        	.antMatchers("/*").permitAll()
            .antMatchers("/" + CMS_CONTEXT + "**").authenticated()
            .and()
        .formLogin()
        	.loginPage("/" + CMS_CONTEXT + LOGIN_URL)
        	.defaultSuccessUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGIN_SUCCESS)
            .failureUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGIN_ERROR)
            .permitAll()
            .and()
		.logout()
			.logoutUrl("/" + CMS_CONTEXT + LOGIN_URL + "/" + LOGOUT)
			.logoutSuccessUrl("/" + CMS_CONTEXT + LOGIN_URL + QUERY_STRING_SEPARATOR + LOGOUT)
			.permitAll();

        http.headers().disable();
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
        //we don't want these URLs to be secured so that they can be cached by the browser
		web.ignoring().antMatchers(STATIC_RESOURCES_URI + "**");
		web.ignoring().antMatchers("/" + PICTURES_URL + "/" + LOAD_CAR_PICTURE.getName() + "**");
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
			.passwordEncoder(this.passwordEncoder)
		 	.withUser(CMS_LOGIN_USER)
		 	.password(CMS_LOGIN_ENCRYPTED_PASSWORD)
		 	.roles(USER);
		
		auth.inMemoryAuthentication()
			.passwordEncoder(this.passwordEncoder)
			.withUser(CMS_LOGIN_USER)
			.password("$2a$11$Rbt9Gh4W0HUYIEm9NiNh3.cMWZRN4rFSmSEKOUGb20EYqzWcAMGHm")
			.roles(USER);
    }
}
