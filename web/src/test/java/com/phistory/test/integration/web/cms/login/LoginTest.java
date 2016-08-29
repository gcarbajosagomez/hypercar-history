package com.phistory.test.integration.web.cms.login;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.CMS_LOGIN_USER;
import static org.hamcrest.MatcherAssert.assertThat;

import com.phistory.test.integration.web.navbar.NavBarPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import com.phistory.test.integration.web.cms.car.CmsCarListPage;

@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
						listeners = { DependencyInjectionTestExecutionListener.class,
									  DirtiesContextTestExecutionListener.class })
public class LoginTest extends BaseIntegrationTest
{
	@Value("${local.server.port}")
	private int port;
	private LoginPage loginPage;
	private NavBarPage navBarPage;
	private CmsCarListPage cmsCarListPage;
	private LoginTestUtils loginTestUtils;

    @BeforeClass
    @Override
	public void setupTest() throws Exception
	{
		super.setupBaseTest();
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT);
		this.loginPage = new LoginPage(this.webDriver);
        this.loginTestUtils = new LoginTestUtils();
	}
	
	@Test(groups = "preLogin")
	public void test_username_input_is_displayed()
	{
		assertThat("Username input should be displayed", this.loginPage.isUsernameInputDisplayed());
	}
	
	@Test(groups = "preLogin")
	public void test_password_input_is_displayed()
	{
		assertThat("Password input should be displayed", this.loginPage.isPasswordInputDisplayed());
	}
	
	@Test(groups = "preLogin")
	public void test_login_button_is_displayed()
	{
		assertThat("Login button should be displayed", this.loginPage.isLoginButtonDisplayed());
	}
	
	@Test(dependsOnGroups = "preLogin")
	public void test_perform_login_with_wrong_credentials() throws Exception
	{
		this.loginPage.typeUsername(CMS_LOGIN_USER);
		this.loginPage.typePassword("wrongPWD");
		this.loginPage.clickLoginButton();
		Thread.sleep(1000);
		assertThat("Wrong credentials alert should be displayed", this.loginPage.isWrongCredentialsAlertDisplayed());
		
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + CARS);
		this.cmsCarListPage = new CmsCarListPage(this.webDriver);
		this.cmsCarListPage.initializePageElements();
		this.test_username_input_is_displayed();
	}
	
	@Test(groups = {"login"}, dependsOnMethods = "test_perform_login_with_wrong_credentials")
	public void test_perform_login() throws Exception
	{
        this.loginTestUtils.performCMSLogin(this.loginPage);
        Thread.sleep(1000);
        webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + CARS);
        CmsCarListPage cmsCarListPage = new CmsCarListPage(webDriver);
        cmsCarListPage.initializePageElements();
		assertThat("Main car list div should be present after login", this.cmsCarListPage.isMainCarListDivPresent());
	}

	@Test(dependsOnGroups = "login")
	public void test_cms_dropdown_toggle_is_displayed() throws Exception
	{
		this.navBarPage = new NavBarPage(this.webDriver);
		this.navBarPage.initializePageElements();
		assertThat("CMS dropdown toggle should be present after login", this.navBarPage.isCMSDropDownToggleDisplayed());
	}

    @AfterClass
    @Override
	public void tearDownTest()
	{
		if (this.webDriver != null)
		{
			this.webDriver.close();
		}
	}
}
