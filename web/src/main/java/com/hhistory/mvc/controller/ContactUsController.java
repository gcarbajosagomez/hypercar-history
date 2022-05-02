package com.hhistory.mvc.controller;

import com.hhistory.mvc.cms.form.ContactUsMessageForm;
import com.hhistory.mvc.dto.ContactUsMessageDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Properties;

import static com.hhistory.mvc.controller.BaseControllerData.CONTACT_US_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Controller to handle Contact us URLs
 *
 * @author Gonzalo
 */
@Slf4j
@RestController
@RequestMapping(value = CONTACT_US_URL,
        method = {POST, HEAD})
public class ContactUsController extends BaseController {

    private static final String SENDER_EMAIL_ADDRESS    = "paganihistory.contact.us.send@gmail.com";
    private static final String RECIPIENT_EMAIL_ADDRESS = "paganihistory.contact.us@gmail.com";
    private static final String SENDER_EMAIL_PASSWORD   = "paganiHistorySender";
    private static final String DEFAULT_SMTP_HOST       = "smtp.gmail.com";
    private static final String DEFAULT_SMTP_PORT       = "587";

    @RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ContactUsMessageDTO handleSendContactUsMessage(@RequestBody @Valid ContactUsMessageForm contactUsMessageForm,
                                                          BindingResult result) {

        try {
            if (!result.hasErrors()) {
                Properties properties = System.getProperties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", DEFAULT_SMTP_HOST);
                properties.put("mail.smtp.port", DEFAULT_SMTP_PORT);
                Session session = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
                    }
                });

                Message message = new MimeMessage(session);

                StringBuilder replyTo = new StringBuilder();
                String senderName = contactUsMessageForm.getSenderName();
                if (!StringUtils.isEmpty(senderName)) {
                    replyTo.append(senderName.replaceAll("[ ]+", ".")).append("-");
                }

                String senderEmail = contactUsMessageForm.getSenderEmail();
                if (!StringUtils.isEmpty(senderEmail)) {
                    replyTo.append(senderEmail);
                }

                message.setReplyTo(new Address[] {new InternetAddress(replyTo.toString())});
                message.setText(contactUsMessageForm.getMessage());
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL_ADDRESS));
                message.setSubject(contactUsMessageForm.getSubject());
                Transport.send(message);
                log.info("'Contact Us' message from: " + replyTo + " sent successfully");

                String successMessage = super.getMessageSource().getMessage("footer.contactUs.messageSentSuccessfully",
                                                                            new Object[] {},
                                                                            LocaleContextHolder.getLocale());

                return ContactUsMessageDTO.builder()
                                          .successMessage(successMessage)
                                          .build();
            } else {
                String validationErrorMessage = super.getMessageSource().getMessage("footer.contactUs.mandatoryFieldsError",
                                                                                    new Object[] {},
                                                                                    LocaleContextHolder.getLocale());

                return ContactUsMessageDTO.builder()
                                          .errorMessage(validationErrorMessage)
                                          .build();
            }
        } catch (MessagingException me) {
            log.error(me.toString(), me);
            String errorMessage = super.getMessageSource().getMessage("footer.contactUs.error",
                                                                      new Object[] {},
                                                                      LocaleContextHolder.getLocale());

            return ContactUsMessageDTO.builder()
                                      .errorMessage(errorMessage)
                                      .build();
        }
    }
}
