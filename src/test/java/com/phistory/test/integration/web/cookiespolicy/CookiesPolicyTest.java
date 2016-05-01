package com.phistory.test.integration.web.cookiespolicy;

import static com.phistory.mvc.controller.BaseControllerData.COOKIES_POLICY_URL;
import static com.phistory.test.integration.web.BaseIntegrationTest.TEST_SERVER_HOST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
						listeners = { DependencyInjectionTestExecutionListener.class,
									  DirtiesContextTestExecutionListener.class })
public class CookiesPolicyTest extends AbstractTestNGSpringContextTests
{
	@Value("${local.server.port}")
	private int port;
	private WebDriver webDriver;
	private CookiesPolicyPage cookiesPolicyPage;
	
	@BeforeClass
	public void before() throws Exception
	{
		this.webDriver = new FirefoxDriver();
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + COOKIES_POLICY_URL);
		this.cookiesPolicyPage = new CookiesPolicyPage(this.webDriver);
	}
	
	@Test(groups = "cookiesDirective")
	public void test_cookies_policy_directive_message_div_is_displayed()
	{
		assertThat("Cookies policy directive message div should be displayed", this.cookiesPolicyPage.isCookiesDirectiveMessageDivDisplayed(), is(true));
	}
	
	@Test(groups = "cookiesDirective")
	public void test_cookies_policy_directive_you_must_tick_check_box_is_not_displayed()
	{
		assertThat("Cookies policy directive 'you must tick checkbox' div should not be displayed", this.cookiesPolicyPage.isYouMustTickCheckBoxDisplayed(), is(false));
	}
	
	@Test(dependsOnMethods = "test_cookies_policy_directive_message_div_is_displayed",
		  groups = "cookiesDirective")
	public void test_cookies_policy_accept_cookies_checkbox_is_displayed()
	{
		assertThat("Cookies policy checkbox should be displayed", this.cookiesPolicyPage.isAcceptCookiesCheckBox(), is(true));
	}
	
	@Test(dependsOnMethods = "test_cookies_policy_directive_message_div_is_displayed",
		  groups = "cookiesDirective")
	public void test_cookies_policy_accept_cookies_button_is_displayed()
	{
		assertThat("Cookies policy button should be displayed", this.cookiesPolicyPage.isAcceptCookiesButtonDisplayed(), is(true));
	}
	
	@Test(dependsOnGroups = "cookiesDirective")
	public void test_cookies_policy_directive_message_is_not_dismissed_if_checkbox_is_not_checked()
	{
		this.cookiesPolicyPage.clickAcceptCookiesButton();
		test_cookies_policy_directive_message_div_is_displayed();
	}
	
	@Test(dependsOnMethods = "test_cookies_policy_directive_message_is_not_dismissed_if_checkbox_is_not_checked",
		  dependsOnGroups = "cookiesDirective")
	public void test_cookies_policy_directive_message_is_dismissed_if_checkbox_is_checked() throws InterruptedException
	{
		this.cookiesPolicyPage.clickAcceptCookiesCheckbox();
		this.cookiesPolicyPage.clickAcceptCookiesButton();
		Thread.sleep(1000);
		assertThat("Cookies policy directive message div should be displayed", this.cookiesPolicyPage.isCookiesDirectiveMessageDivDisplayed(), is(false));
	}
	
	@AfterClass
	public void after() 
	{
		if (this.webDriver != null)
		{
			this.webDriver.close();
		}
	}
}
