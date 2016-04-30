package com.phistory.test.integration.web.car;

import static com.phistory.mvc.controller.BaseControllerData.CARS_URL;
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
public class CarListTest extends AbstractTestNGSpringContextTests
{	
	@Value("${local.server.port}")
	private int port;
	private WebDriver webDriver;
	private CarListPage carListPage;

	@BeforeClass
	public void before() throws Exception
	{
		this.webDriver = new FirefoxDriver();
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CARS_URL);
		this.carListPage = new CarListPage(this.webDriver);
	}
	
	@Test
	public void test_car_list_is_displayed() throws Exception
	{
		assertThat("Main car list div should be displayed", this.carListPage.isMainCarListDivDisplayed());
	}
	
	public void test_there_are_cars_listed() throws Exception
	{
		assertThat("There should be at least one car listed", this.carListPage.getFirstCarListedDivId(), is(notNullValue()));
	}
	
	@Test(dependsOnMethods = "test_car_list_is_displayed")
	public void test_pagination_is_displayed() throws Exception
	{
		assertThat("Pagination should be displayed", this.carListPage.isPaginationDivDisplayed());
		assertThat("Paginator should contain at least one page", this.carListPage.isPaginatorDisplayed());
	}
	
	@Test(dependsOnMethods = "test_pagination_is_displayed")
	public void test_paginate_fordward() throws Exception
	{
		String initialFirstCarListedDivId = this.carListPage.getFirstCarListedDivId();
		
		this.carListPage.paginate(2);
		Thread.sleep(2000);
		assertThat("After paginating cars listed should have changed", this.carListPage.getFirstCarListedDivId(), is(not(initialFirstCarListedDivId)));
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
