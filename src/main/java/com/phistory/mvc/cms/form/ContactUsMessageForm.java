package com.phistory.mvc.cms.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form class to send a 'Contact Us' message
 * 
 * @author Gonzalo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsMessageForm implements Serializable
{
	private static final long serialVersionUID = -6368673540543267552L;
	
	@NotNull
	@Size(min = 1)
	private String subject;
	private String senderName;
	private String senderEmail;
	@NotNull
	@Size(min = 1)
	private String message;
}
