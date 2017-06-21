package com.phistory.test.integration.web.cookiespolicy;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.phistory.mvc.controller.BaseControllerData.COOKIES_POLICY_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class CookiesPolicyTest extends BaseIntegrationTest {

    @Value("${local.server.port}")
    private int               port;
    private CookiesPolicyPage cookiesPolicyPage;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.cookiesPolicyPage = new CookiesPolicyPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               COOKIES_POLICY_URL;
    }

    @Test(groups = "cookiesDirective")
    public void test_cookies_policy_directive_message_div_is_displayed() {
        assertThat("Cookies policy directive message div should be displayed",
                   this.cookiesPolicyPage.isCookiesDirectiveMessageDivDisplayed(), equalTo(true));
    }

    @Test(groups = "cookiesDirective")
    public void test_cookies_policy_directive_you_must_tick_check_box_is_not_displayed() {
        assertThat("Cookies policy directive 'you must tick checkbox' div should not be displayed",
                   this.cookiesPolicyPage.isYouMustTickCheckBoxDisplayed(), equalTo(false));
    }

    @Test(dependsOnMethods = "test_cookies_policy_directive_message_div_is_displayed",
            groups = "cookiesDirective")
    public void test_cookies_policy_accept_cookies_checkbox_is_displayed() {
        assertThat("Cookies policy checkbox should be displayed", this.cookiesPolicyPage.isAcceptCookiesCheckBox(),
                   equalTo(true));
    }

    @Test(dependsOnMethods = "test_cookies_policy_directive_message_div_is_displayed",
            groups = "cookiesDirective")
    public void test_cookies_policy_accept_cookies_button_is_displayed() {
        assertThat("Cookies policy button should be displayed", this.cookiesPolicyPage.isAcceptCookiesButtonDisplayed(),
                   equalTo(true));
    }

    @Test(dependsOnGroups = "cookiesDirective")
    public void test_cookies_policy_directive_message_is_not_dismissed_if_checkbox_is_not_checked() throws InterruptedException {
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.cookiesPolicyPage.clickAcceptCookiesButton();
        test_cookies_policy_directive_message_div_is_displayed();
    }

    @Test(dependsOnMethods = "test_cookies_policy_directive_message_is_not_dismissed_if_checkbox_is_not_checked",
            dependsOnGroups = "cookiesDirective")
    public void test_cookies_policy_directive_message_is_dismissed_if_checkbox_is_checked() throws InterruptedException {
        this.cookiesPolicyPage.clickAcceptCookiesCheckbox();
        this.cookiesPolicyPage.clickAcceptCookiesButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("Cookies policy directive message div should be displayed",
                   this.cookiesPolicyPage.isCookiesDirectiveMessageDivDisplayed(), equalTo(false));
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
