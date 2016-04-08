package com.phistory.test.integration.web.car;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.phistory.test.integration.mvc.springframework.config.IntegrationWebTestConfig;

//@SpringApplicationConfiguration(classes = IntegrationWebTestConfig.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class CarListTest implements InitializingBean
{	
	@Inject
	private CarListPage carListPage;

	@Override
	public void afterPropertiesSet() throws Exception {
		carListPage.initializePage();	
	}
	
	//@Test
	public void testCarList() throws Exception
	{
		Assert.assertTrue("Main car list div is not present", carListPage.isMainCarListDivPresent());
	}
	
	//@Test
	public void testPagination() throws Exception
	{
		Assert.assertTrue("Pagination div is not present", carListPage.isPaginationDivPresent());
		Assert.assertTrue("Paginator doesn't contain any page", carListPage.isPaginatorPresent());
		
		carListPage.paginate();
	}
}
