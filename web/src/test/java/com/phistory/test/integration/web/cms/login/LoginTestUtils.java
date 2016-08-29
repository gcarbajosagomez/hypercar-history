package com.phistory.test.integration.web.cms.login;

import static com.phistory.mvc.springframework.config.WebSecurityConfig.CMS_LOGIN_USER;

/**
 * Set of utilities for the {@link LoginTest} class
 *
 * Created by gonzalo on 7/17/16.
 */
public class LoginTestUtils
{
    private static final String CMS_LOGIN_TEST_PASSWORD = "testPassword2016";

    /**
     * Perform the login process to the site's CMS section
     *
     * @param loginPage
     * @throws InterruptedException
     */
    public void performCMSLogin(LoginPage loginPage) throws InterruptedException
    {
        loginPage.typeUsername(CMS_LOGIN_USER);
        loginPage.typePassword(CMS_LOGIN_TEST_PASSWORD);
        loginPage.clickLoginButton();
    }
}
