package com.phistory.test.integration.web.car;

import static com.phistory.mvc.controller.BaseControllerData.CARS_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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

@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
						listeners = { DependencyInjectionTestExecutionListener.class,
									  DirtiesContextTestExecutionListener.class })
public class CarListTest extends BaseIntegrationTest
{	
	@Value("${local.server.port}")
	private int port;
	private CarListPage carListPage;

	@BeforeClass
	public void before() throws Exception
	{
		super.before();		
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
		assertThat("There should be at least one car listed", this.carListPage.getFirstCarListedDivId(), equalTo(notNullValue()));
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
