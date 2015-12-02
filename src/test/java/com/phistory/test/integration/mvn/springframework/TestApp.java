package com.phistory.test.integration.mvn.springframework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,
		  						  HibernateJpaAutoConfiguration.class,
		  						  FreeMarkerAutoConfiguration.class})
public class TestApp
{
	public static void main(String[] args)
	{
        SpringApplication.run(TestApp.class);
    }
}
