package com.phistory.test.integration.web.index;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.phistory.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class IndexTest
{	
	@Value("${local.server.port}")
	private int port;
	private IndexPage indexPage;
	private WebDriver webDriver;
	
	@Before
	public void before() throws Exception
	{
		this.webDriver = new FirefoxDriver();
		this.webDriver.get("http://localhost:" + port);
		this.indexPage = new IndexPage(this.webDriver);
	}
	
	@Test
	public void testJumbotronIsDisplayed()
	{
		assertThat("Jumbotron should be displayed", indexPage.isJumbotronDisplayed(), is(true));
	}
	
	@Test
	public void testCarouselIsDisplayed()
	{
		assertThat("Carousel should be displayed", indexPage.isCarouselDisplayed(), is(true));
	}
	
	@Test
	public void when_carousel_is_displayed_test_carousel_has_images()
	{
		testCarouselIsDisplayed();
		assertThat("Carousel should have valid images", indexPage.carouselHasValidImages(), is(true));
	}
	
	@Test
	public void testCarouselHasAnActiveImage()
	{
		assertThat("Carousel should have an active image", indexPage.carouselHasAnActiveImage(), is(true));
	}
	
	@Test
	public void when_carousel_has_images_click_left_image_should_slide()
	{
		when_carousel_is_displayed_test_carousel_has_images();
		indexPage.clickLeftCarouselControl();
	}
	
	@Test
	public void when_carousel_has_images_click_right_image_should_slide()
	{
		when_carousel_is_displayed_test_carousel_has_images();
		indexPage.clickRightCarouselControl();
	}
	
	@After
	public void after() 
	{
		if (this.webDriver != null)
		{
			this.webDriver.close();
		}
	}
}
