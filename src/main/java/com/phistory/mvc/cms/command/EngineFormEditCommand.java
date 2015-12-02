package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import lombok.Data;

import com.phistory.mvc.cms.form.EngineForm;

/**
 * Command class to edit an Engine form
 *
 * @author Gonzalo
 */
@Data
public class EngineFormEditCommand
{
	@Valid
    private EngineForm engineForm;	
	
	public EngineFormEditCommand() {
		engineForm = new EngineForm();
	}
}
