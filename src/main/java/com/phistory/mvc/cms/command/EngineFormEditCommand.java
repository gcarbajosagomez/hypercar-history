package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import com.phistory.mvc.cms.form.EngineForm;

/**
 * Command class to edit an Engine form
 *
 * @author Gonzalo
 */
public class EngineFormEditCommand
{
	@Valid
    private EngineForm engineForm;	

	public EngineFormEditCommand() {
		engineForm = new EngineForm();
	}

	public EngineForm getEngineForm() {
		return engineForm;
	}

	public void setEngineForm(EngineForm engineForm) {
		this.engineForm = engineForm;
	}
}
