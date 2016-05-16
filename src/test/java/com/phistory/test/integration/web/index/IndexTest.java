package com.phistory.test.integration.web.index;

import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static com.phistory.test.integration.web.BaseIntegrationTest.TEST_SERVER_HOST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
public class IndexTest extends AbstractTestNGSpringContextTests
{	
	private IndexPage indexPage;
	@Value("${local.server.port}")
	private int port;
	private WebDriver webDriver;
	
	@BeforeClass
	public void before() throws Exception
	{
		this.webDriver = new FirefoxDriver();
		this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + INDEX_URL);
		this.indexPage = new IndexPage(this.webDriver);
	}
	
	@Test
	public void test_jumbotron_is_displayed()
	{
		assertThat("Jumbotron should be displayed", this.indexPage.isJumbotronDisplayed(), is(true));
	}
	
	@Test
	public void test_carousel_is_displayed()
	{
		assertThat("Carousel should be displayed", this.indexPage.isCarouselDisplayed(), is(true));
	}
	
	@Test(dependsOnMethods = "test_carousel_is_displayed")
	public void when_carousel_is_displayed_test_carousel_has_images()
	{
		assertThat("Carousel should have valid images", this.indexPage.carouselHasValidImages(), is(true));
	}
	
	@Test(dependsOnMethods = "when_carousel_is_displayed_test_carousel_has_images")
	public void test_carousel_has_an_active_image()
	{
		assertThat("Carousel should have an active image", this.indexPage.carouselHasAnActiveImage(), is(true));
	}
	
	@Test(dependsOnMethods = "test_carousel_has_an_active_image")
	public void when_carousel_has_images_click_left_image_should_slide() throws InterruptedException
	{
		String previousCarouselActiveImageDivId = this.indexPage.getCarouselActiveImageDivId();
		this.indexPage.clickLeftCarouselControl();
		Thread.sleep(1500);
		assertThat("Carousel should image should have slid after clicking left arrow",
				   this.indexPage.getCarouselActiveImageDivId(),
				   is(not(previousCarouselActiveImageDivId)));
	}
	
	@Test(dependsOnMethods = "test_carousel_has_an_active_image")
	public void when_carousel_has_images_click_right_image_should_slide() throws InterruptedException
	{
		String previousCarouselActiveImageDivId = this.indexPage.getCarouselActiveImageDivId();
		this.indexPage.clickRightCarouselControl();		
		Thread.sleep(1500);
		assertThat("Carousel should image should have slid after clicking right arrow",
				   this.indexPage.getCarouselActiveImageDivId(),
				   is(not(previousCarouselActiveImageDivId)));
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
