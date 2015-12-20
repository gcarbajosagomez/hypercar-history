package com.phistory.test.integration.web.cms.login;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.phistory.test.integration.mvc.springframework.config.IntegrationWebTestConfig;
import com.phistory.test.integration.web.cms.car.CmsCarListPage;

@SpringApplicationConfiguration(classes = IntegrationWebTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginTest implements InitializingBean
{	
	@Inject
	private LoginPage loginPage;
	@Inject
	private CmsCarListPage cmsCarListPage;

	@Override
	public void afterPropertiesSet() throws Exception {
		loginPage.initializePage();	
	}
	
	@Test
	public void testLogin() throws Exception
	{
		loginPage.typeUsername("u");
		loginPage.typePassword("p");
		loginPage.clickLoginButton();
		cmsCarListPage.initializePageElements();
		WebDriverWait wait = new WebDriverWait(cmsCarListPage.getWebDriver(), 5000);
		wait.until(ExpectedConditions.elementToBeClickable(cmsCarListPage.getCarListLocator()));
		Assert.assertTrue("Main car list div is not present", cmsCarListPage.isMainCarListDivPresent());
	}
}
