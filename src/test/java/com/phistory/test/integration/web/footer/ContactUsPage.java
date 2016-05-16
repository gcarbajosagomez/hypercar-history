package com.phistory.test.integration.web.footer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.phistory.test.integration.web.BasePage;

public class ContactUsPage extends BasePage
{
	@FindBy(id = "contact-us-main-div")
	private WebElement contactUsMainDiv;	
	@FindBy(className = "close")
	private WebElement closeModalButon;
	@FindBy(id = "contact-us-subject")
	private WebElement contactUsSubjectInput;	
	@FindBy(id = "contact-us-sender-name")
	private WebElement contactUsSenderName;	
	@FindBy(id = "contact-us-sender-email")
	private WebElement contactUsSenderEmail;
	@FindBy(id = "contact-us-message")
	private WebElement contactUsMessage;
	@FindBy(id = "contact-us-error-alert-div")
	private WebElement contactUsErrorAlertDiv;
	@FindBy(id = "contact-us-success-alert-div")
	private WebElement contactUsSuccessAlertDiv;
	@FindBy(id = "contact-us-send-message-button")
	private WebElement contactUsSendMessageButton;
	@FindBy(className = "btn-primary")
	private WebElement contactUsConfirmSendMessageButton;
	
	public ContactUsPage(WebDriver webDriver)
	{
		super(webDriver);
		super.initializePageElements();
	}
	
	public boolean isContactUsMainDivDisplayed()
	{
		try
		{
			return this.contactUsMainDiv.isDisplayed();
		} 
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isCloseModalButonDisplayed()
	{
		try
		{
			return this.closeModalButon.isDisplayed();
		} 
		catch (Exception e)
		{
			return false;
		}
	}
	
	public void typeMessageSubject(String subject)
	{
		this.contactUsSubjectInput.sendKeys(subject);
	}
	
	public void typeSenderName(String senderName)
	{
		this.contactUsSenderName.sendKeys(senderName);
	}
	
	public void typeSenderEmail(String senderEmail)
	{
		this.contactUsSenderEmail.sendKeys(senderEmail);
	}
	
	public void typeMessage(String message)
	{
		this.contactUsMessage.sendKeys(message);
	}
	
	public boolean isContactUsErrorAlertDivDisplayed()
	{
		try
		{
			return this.contactUsErrorAlertDiv.isDisplayed();
		} 
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isContactUsSuccessAlertDivDisplayed()
	{
		try
		{
			return this.contactUsSuccessAlertDiv.isDisplayed();
		} 
		catch (Exception e)
		{
			return false;
		}
	}
	
	public void clickSendMessageButton()
	{
		this.contactUsSendMessageButton.click();
	}
	
	public void clickConfirmSendMessageButton()
	{
		this.contactUsConfirmSendMessageButton.click();
	}
	
	public void clickCloseModalButon()
	{
		if (this.closeModalButon.isDisplayed())
		{
			this.closeModalButon.click();
		}
	}
}
