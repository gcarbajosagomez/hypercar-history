package com.hhistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Form class to send a 'Contact Us' message
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsMessageForm {

    @NotNull
    @Size(min = 1)
    private String subject;

    private String senderName;
    private String senderEmail;

    @NotNull
    @Size(min = 1)
    private String message;
}
