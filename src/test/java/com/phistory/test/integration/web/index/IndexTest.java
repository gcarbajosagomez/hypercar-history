package com.phistory.test.integration.web.index;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.phistory.test.integration.mvc.springframework.config.IntegrationWebTestConfig;

@SpringApplicationConfiguration(classes = IntegrationWebTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexTest implements InitializingBean
{	
	@Inject
	private IndexPage indexPage;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		indexPage.initializePage();
	}
	
	@Test
	public void testIsJumbotronIsDisplayed()
	{
		Assert.assertNotNull(indexPage.checkJumbotronIsDisplayed());
	}
	
	@Test
	public void testIsCarouselIsDisplayed()
	{
		Assert.assertNotNull(indexPage.checkCarouselIsDisplayed());
	}
	
	@Test
	public void testCarouselHasImages()
	{
		Assert.assertNotNull(indexPage.checkCarouselHasValidImages());
	}
	
	@Test
	public void testClickLeftArrowCarouselControl()
	{
		indexPage.clickLeftCarouselControl();
	}
	
	@Test
	public void testClickRightArrowCarouselControl()
	{
		indexPage.clickRightCarouselControl();
	}
}
