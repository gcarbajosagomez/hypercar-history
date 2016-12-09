package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.CONTACT_US_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phistory.mvc.cms.form.ContactUsMessageForm;

/**
 * Controller to handle Contact us URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = CONTACT_US_URL,
				method = {POST, HEAD})
public class ContactUsController extends BaseController
{
	private static final String SENDER_EMAIL_ADDRESS    = "paganihistory.contact.us.send@gmail.com";
	private static final String SENDER_EMAIL_PASSWORD	= "paganiHistorySender";
	private static final String DEFAULT_SMTP_HOST 		= "smtp.gmail.com";
	private static final String DEFAULT_SMTP_PORT 		= "587";
	
	@RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> handleSendContactUsMessage(@RequestBody @Valid ContactUsMessageForm contactUsMessageForm,
														  BindingResult result)
	{
		Map<String, String> resultMessage = new HashMap<>();
		
		try
		{
			if(!result.hasErrors())
			{
				Properties properties = System.getProperties();
				properties.put("mail.smtp.auth", 				"true");
				properties.put("mail.smtp.starttls.enable", 	"true");
				properties.put("mail.smtp.host", 				DEFAULT_SMTP_HOST);
				properties.put("mail.smtp.port", 				DEFAULT_SMTP_PORT);
				Session session = Session.getInstance(properties, new Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
					}
				});
	
				Message message = new MimeMessage(session);				

				StringBuilder replyTo = new StringBuilder();
				String senderName = contactUsMessageForm.getSenderName();
				if (!StringUtils.isEmpty(senderName))
				{
					replyTo.append(senderName.replaceAll("[ ]+", ".")).append("");
				}
				
				String senderEmail = contactUsMessageForm.getSenderEmail();
				if (!StringUtils.isEmpty(senderEmail))
				{
					replyTo.append(senderEmail);
				}
				
				message.setReplyTo(new Address[]{new InternetAddress(replyTo.toString())});
				message.setText(contactUsMessageForm.getMessage());				
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SENDER_EMAIL_ADDRESS));
				message.setSubject(contactUsMessageForm.getSubject());
				Transport.send(message);
	
				String successMessage = super.getMessageSource().getMessage("footer.contactUs.messageSentSuccessfully",
																		    null,
																		    LocaleContextHolder.getLocale());     
				
				resultMessage.put(CONTACT_US_SUCCESS_MESSAGE, successMessage);	
				log.info("'Contact Us' message from: " + replyTo + " sent successfully");		
			}
			else
			{
				String validationErrorsMessage = super.getMessageSource().getMessage("footer.contactUs.mandatoryFieldsError",
					    															 null,
					    															 LocaleContextHolder.getLocale());   
				
				resultMessage.put(CONTACT_US_EXCEPTION_MESSAGE, validationErrorsMessage);
			}
		}
		catch (MessagingException me)
		{			  
			log.error(me.toString(), me);
			String errorMessage = super.getMessageSource().getMessage("footer.contactUs.error",
					  												  null,
					  												  LocaleContextHolder.getLocale()); 
			
			resultMessage.put(CONTACT_US_EXCEPTION_MESSAGE, errorMessage);
		}		
		
		return resultMessage;
	}
}
