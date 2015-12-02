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

/**
 * Configuration class for setting up the application's security config
 * 
 * @author gonzalo
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
    protected void configure(HttpSecurity http) throws Exception
	{        
		http        
        .authorizeRequests()
        	.antMatchers("/*.html").permitAll()
            .antMatchers("/cms/*.html").access("hasRole('USER')").and()
        .formLogin()
        	.defaultSuccessUrl("/cms/login.html?success")
            .loginPage("/cms/login.html")
            .failureUrl("/cms/login.html?error")
            .permitAll()
            .and()
		.logout()
			.logoutUrl("/cms/logout.html")
			.logoutSuccessUrl("/cms/login.html?logout")
			.permitAll();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		 	.withUser("u")
		 	.password("p")
		 	.roles("USER");
    }
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
}
