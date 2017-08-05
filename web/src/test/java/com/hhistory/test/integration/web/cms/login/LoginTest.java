package com.hhistory.test.integration.web.cms.login;

import com.hhistory.Main;
import com.hhistory.test.integration.web.BaseIntegrationTest;
import com.hhistory.test.integration.web.cms.car.CmsCarListPage;
import com.hhistory.test.integration.web.navbar.NavBarPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.hhistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.CMS_LOGIN_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class LoginTest extends BaseIntegrationTest {

    @Value("${local.server.port}")
    private int            port;
    private LoginPage      loginPage;
    private NavBarPage     navBarPage;
    private CmsCarListPage cmsCarListPage;
    private LoginTestUtils loginTestUtils;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.loginPage = new LoginPage(this.webDriver);
        this.loginTestUtils = new LoginTestUtils();
        //to let for DB entities to be loaded in-memory
        Thread.sleep(25000);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               CMS_CONTEXT;
    }

    @Test(groups = "preLogin")
    public void test_username_input_is_displayed() {
        assertThat("Username input should be displayed", this.loginPage.isUsernameInputDisplayed());
    }

    @Test(groups = "preLogin")
    public void test_password_input_is_displayed() {
        assertThat("Password input should be displayed", this.loginPage.isPasswordInputDisplayed());
    }

    @Test(groups = "preLogin")
    public void test_login_button_is_displayed() {
        assertThat("Login button should be displayed", this.loginPage.isLoginButtonDisplayed());
    }

    @Test(dependsOnGroups = "preLogin")
    public void test_perform_login_with_wrong_credentials() throws Exception {
        this.loginPage.typeUsername(CMS_LOGIN_USER);
        this.loginPage.typePassword("wrongPWD");
        this.loginPage.clickLoginButton();
        Thread.sleep(1000);
        assertThat("Wrong credentials alert should be displayed", this.loginPage.isWrongCredentialsAlertDisplayed());

        this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + CARS_URL);
        this.cmsCarListPage = new CmsCarListPage(this.webDriver);
        this.cmsCarListPage.initializePageElements();
        this.test_username_input_is_displayed();
    }

    @Test(groups = {"login"}, dependsOnMethods = "test_perform_login_with_wrong_credentials")
    public void test_perform_login() throws Exception {
        this.loginTestUtils.performCMSLogin(this.loginPage);
        Thread.sleep(1000);
        webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + CARS_URL);
        CmsCarListPage cmsCarListPage = new CmsCarListPage(webDriver);
        cmsCarListPage.initializePageElements();
        assertThat("Main car list div should be present after login", this.cmsCarListPage.isMainCarListDivPresent());
    }

    @Test(dependsOnGroups = "login", dependsOnMethods = "test_perform_login")
    public void test_cms_dropdown_toggle_is_displayed() throws Exception {
        this.navBarPage = new NavBarPage(this.webDriver);
        this.navBarPage.initializePageElements();
        assertThat("CMS dropdown toggle should be present after login", this.navBarPage.isCMSDropDownToggleDisplayed());
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
