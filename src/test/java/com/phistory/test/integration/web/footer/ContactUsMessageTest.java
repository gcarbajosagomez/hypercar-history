package com.phistory.test.integration.web.footer;

import static com.phistory.test.integration.web.BaseIntegrationTest.TEST_SERVER_HOST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.phistory.Main;

@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
						listeners = { DependencyInjectionTestExecutionListener.class,
									  DirtiesContextTestExecutionListener.class })
public class ContactUsMessageTest extends AbstractTestNGSpringContextTests
{
	private static final String IRRELEVANT_MESSAGE_SUBJECT 		= "irrelevantSubject";
	private static final String IRRELEVANT_MESSAGE_SENDER_NAME 	= "irrelevantMessageSender";
	private static final String IRRELEVANT_MESSAGE_SENDER_EMAIL = "irrelevant.email@gmail.com";
	private static final String IRRELEVANT_MESSAGE		 		= "irrelevantTestMessage";
	@Value("${local.server.port}")
	private int port;
	private WebDriver webDriver;
	private ContactUsPage contactUsPage;
	private FooterPage footerPage;
	
	@BeforeClass
	public void before() throws Exception
	{
		this.webDriver = new FirefoxDriver();
		this.webDriver.get(TEST_SERVER_HOST + this.port);
		this.contactUsPage = new ContactUsPage(this.webDriver);
		this.footerPage = new FooterPage(this.webDriver);
	}
	
	private void test_click_contact_us_link_should_display_contact_us_modal() throws InterruptedException
	{
		this.footerPage.clickContactUsLink();
		Thread.sleep(1000);
		assertThat("Contact Us message modal should be displayed", this.contactUsPage.isContactUsMainDivDisplayed(), is(true));
	}
	
	@Test(groups = "contactUsMessageTests")
	public void test_initial_click_contact_us_link_should_display_contact_us_modal() throws InterruptedException
	{
		assertThat("Contact Us message modal should not be displayed at this point", this.contactUsPage.isContactUsMainDivDisplayed(), is(false));
		this.test_click_contact_us_link_should_display_contact_us_modal();
	}
	
	@Test(groups = "contactUsMessageTests", dependsOnMethods = "test_initial_click_contact_us_link_should_display_contact_us_modal")
	public void test_send_message_without_required_fields_should_show_error() throws InterruptedException
	{
		this.test_click_contact_us_link_should_display_contact_us_modal();
		assertThat("Error message should not be displayed at this point", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(false));
		this.contactUsPage.typeSenderName(IRRELEVANT_MESSAGE_SENDER_NAME);
		this.contactUsPage.typeSenderEmail(IRRELEVANT_MESSAGE_SENDER_EMAIL);
		this.contactUsPage.clickSendMessageButton();
		Thread.sleep(1000);
		this.contactUsPage.clickConfirmSendMessageButton();
		Thread.sleep(1000);
		assertThat("Error message should be displayed", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(true));
	}
	
	@Test(groups = "contactUsMessageTests", dependsOnMethods = "test_initial_click_contact_us_link_should_display_contact_us_modal")
	public void test_send_message_without_required_fields_should_show_error_and_after_filling_all_fields_should_send_message() throws InterruptedException
	{
		this.test_click_contact_us_link_should_display_contact_us_modal();
		assertThat("Error message should not be displayed at this point", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(false));
		this.contactUsPage.typeSenderName(IRRELEVANT_MESSAGE_SENDER_NAME);
		this.contactUsPage.typeSenderEmail(IRRELEVANT_MESSAGE_SENDER_EMAIL);
		this.contactUsPage.clickSendMessageButton();
		Thread.sleep(1000);
		this.contactUsPage.clickConfirmSendMessageButton();
		Thread.sleep(1000);
		assertThat("Error message should be displayed", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(true));
		this.contactUsPage.typeMessageSubject(IRRELEVANT_MESSAGE_SUBJECT);
		this.contactUsPage.typeMessage(IRRELEVANT_MESSAGE);
		this.contactUsPage.clickSendMessageButton();
		Thread.sleep(1000);
		this.contactUsPage.clickConfirmSendMessageButton();
		Thread.sleep(6000);
		assertThat("Success message should be displayed", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(false));
		assertThat("Success message should be displayed", this.contactUsPage.isContactUsSuccessAlertDivDisplayed(), is(true));
	}
	
	@Test(groups = "contactUsMessageTests", dependsOnMethods = "test_initial_click_contact_us_link_should_display_contact_us_modal")
	public void test_send_message_with_required_fields_should_send_message() throws InterruptedException
	{
		this.test_click_contact_us_link_should_display_contact_us_modal();
		assertThat("Error message should not be displayed at this point", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(false));
		this.contactUsPage.typeMessageSubject(IRRELEVANT_MESSAGE_SUBJECT);
		this.contactUsPage.typeSenderName(IRRELEVANT_MESSAGE_SENDER_NAME);
		this.contactUsPage.typeSenderEmail(IRRELEVANT_MESSAGE_SENDER_EMAIL);
		this.contactUsPage.typeMessage(IRRELEVANT_MESSAGE);
		this.contactUsPage.clickSendMessageButton();
		Thread.sleep(1000);
		this.contactUsPage.clickConfirmSendMessageButton();
		Thread.sleep(6000);
		assertThat("Success message should be displayed", this.contactUsPage.isContactUsErrorAlertDivDisplayed(), is(false));
		assertThat("Success message should be displayed", this.contactUsPage.isContactUsSuccessAlertDivDisplayed(), is(true));
	}
	
	@Test(dependsOnGroups = "contactUsMessageTests")
	public void test_click_close_button_should_dismiss_contact_us_modal() throws InterruptedException
	{
		this.test_click_contact_us_link_should_display_contact_us_modal();
		assertThat("Contact Us message modal should be displayed", this.contactUsPage.isContactUsMainDivDisplayed(), is(true));
		this.dismissContactUsModal();
	}
	
	@AfterMethod
	public void dismissContactUsModal() throws InterruptedException
	{
		this.contactUsPage.clickCloseModalButon();
		Thread.sleep(1000);
		assertThat("Contact Us message modal should not be displayed", this.contactUsPage.isContactUsMainDivDisplayed(), is(false));
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
