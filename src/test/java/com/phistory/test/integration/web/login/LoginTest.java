package com.phistory.test.integration.web.login;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.phistory.test.integration.mvc.springframework.config.IntegrationWebTestConfig;
import com.phistory.test.integration.mvn.springframework.TestApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApp.class, IntegrationWebTestConfig.class})
@IntegrationTest("server.port:0")
@WebAppConfiguration
public class LoginTest
{
	@Value("${local.server.port}")
	private int serverPort;
	
	@Inject
	private LoginPage loginPage;
	
	@Test
	public void testLogin()
	{
		loginPage.typeUsername("u");
		loginPage.typePassword("p");
		loginPage.clickLoginButton();
	}

	private String getBaseUrl()
	{
		  return "http://localhost:" + serverPort + "/pagani-history-web";
	}
}
