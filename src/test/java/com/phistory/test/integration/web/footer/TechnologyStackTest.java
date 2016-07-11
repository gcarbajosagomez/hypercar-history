package com.phistory.test.integration.web.footer;

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
import org.testng.annotations.AfterMethod;
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
public class TechnologyStackTest extends BaseIntegrationTest
{
	@Value("${local.server.port}")
	private int port;
	private TechnologyStackPage technologyStackPage;
	private FooterPage footerPage;
	
	@BeforeClass
	public void before() throws Exception
	{
		super.before();
		this.webDriver.get(TEST_SERVER_HOST + this.port);
		this.technologyStackPage = new TechnologyStackPage(this.webDriver);
		this.footerPage = new FooterPage(this.webDriver);
	}
	
	@Test
	public void test_click_technology_stack_link_should_display_technology_stack_modal() throws InterruptedException
	{
		assertThat("Technology Stack modal should not be displayed at this point",
				   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
				   is(false));
		this.assert_click_technology_stack_link_should_display_technology_stack_modal();
	}
	
	@Test(dependsOnMethods = "test_click_technology_stack_link_should_display_technology_stack_modal")
	public void test_click_dismiss_technology_stack_should_dismiss_technology_stack_modal() throws InterruptedException
	{
		assertThat("Technology Stack modal should not be displayed at this point",
				   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
				   is(false));
		this.assert_click_technology_stack_link_should_display_technology_stack_modal();
		this.technologyStackPage.clickDismissTechnologyStackModalButton();
		Thread.sleep(1000);
		assertThat("Technology Stack modal should not be displayed at this point",
				   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
				   is(false));
	}
	
	private void assert_click_technology_stack_link_should_display_technology_stack_modal() throws InterruptedException
	{
		this.footerPage.clickTechnologyStackLink();
		Thread.sleep(1000);
		assertThat("Technology Stack modal should be displayed", this.technologyStackPage.isTechnologyStackModalDivDisplayed(), is(true));
	}
	
	@AfterMethod
	public void dismissContactUsModal() throws InterruptedException
	{		
		if (this.technologyStackPage.isTechnologyStackModalDivDisplayed())
		{
			this.technologyStackPage.clickDismissTechnologyStackModalButton();
			Thread.sleep(1000);
		}			
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
