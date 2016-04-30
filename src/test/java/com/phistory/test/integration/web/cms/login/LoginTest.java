package com.phistory.test.integration.web.cms.login;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.CMS_LOGIN_PASSWORD;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.CMS_LOGIN_USER;
import static com.phistory.test.integration.web.BaseIntegrationTest.TEST_SERVER_HOST;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.phistory.Main;
import com.phistory.test.integration.web.cms.car.CmsCarListPage;

@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
						listeners = { DependencyInjectionTestExecutionListener.class,
									  DirtiesContextTestExecutionListener.class })
public class LoginTest extends AbstractTestNGSpringContextTests
{	
	@Value("${local.server.port}")
	private int port;
	private WebDriver loginPageWebDriver;
	private LoginPage loginPage;
	private WebDriver cmsCarListWebDriver;
	private CmsCarListPage cmsCarListPage;

	@BeforeClass
	public void before() throws Exception
	{
		this.loginPageWebDriver = new FirefoxDriver();
		this.loginPageWebDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT);
		this.loginPage = new LoginPage(this.loginPageWebDriver);
	}
	
	@Test(groups = "login")
	public void test_username_input_is_displayed()
	{
		assertThat("Username input should be present", this.loginPage.isUsernameInputDisplayed());
	}
	
	@Test(groups = "login")
	public void test_password_input_is_displayed()
	{
		assertThat("Password input should be present", this.loginPage.isPasswordInputDisplayed());
	}
	
	@Test(groups = "login")
	public void test_login_button_is_displayed()
	{
		assertThat("Login button should be present", this.loginPage.isLoginButtonDisplayed());
	}
	
	@Test(dependsOnGroups = "login")
	public void test_perform_login() throws Exception
	{
		this.loginPage.typeUsername(CMS_LOGIN_USER);
		this.loginPage.typePassword(CMS_LOGIN_PASSWORD);
		this.loginPage.clickLoginButton();
		Thread.sleep(1000);
		this.cmsCarListWebDriver = new FirefoxDriver();
		this.cmsCarListWebDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + "/" + CARS);
		this.cmsCarListPage = new CmsCarListPage(this.cmsCarListWebDriver);
		this.cmsCarListPage.initializePageElements();
		assertThat("Main car list div should be present after login", this.cmsCarListPage.isMainCarListDivPresent());
	}
	
	@AfterClass
	public void after() 
	{
		if (this.loginPageWebDriver != null)
		{
			this.loginPageWebDriver.close();
		}
	}
}
