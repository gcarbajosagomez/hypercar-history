package com.phistory.test.integration.web.car;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
public class CarDetailsTest extends BaseIntegrationTest
{
	private static final String IRRELEVANT_CAR_ID = "2";
	@Value("${local.server.port}")
	private int port;
	private CarDetailsPage carDetailsPage;

	@BeforeClass
	public void before() throws Exception
	{
		super.before();
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CARS + "/" + IRRELEVANT_CAR_ID);
		this.carDetailsPage = new CarDetailsPage(this.webDriver);
	}
	
	@Test
	public void test_is_car_pictures_carousel_is_displayed()
	{
		assertThat("Car pictures carousel should be displayed", this.carDetailsPage.isCarPicturesCarouselDisplayed(), is(true));
	}
	
	@Test(dependsOnMethods = "test_is_car_pictures_carousel_is_displayed")
	public void test_is_car_pictures_carousel_has_images()
	{
		assertThat("Car pictures carousel should have images", this.carDetailsPage.carPicturesCarouselHasImages(), is(true));
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
