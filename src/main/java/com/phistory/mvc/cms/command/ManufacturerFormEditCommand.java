package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import lombok.Data;

import com.phistory.mvc.cms.form.ManufacturerForm;

/**
 * Command class to edit a Manufacturer form
 *
 * @author Gonzalo
 */
@Data
public class ManufacturerFormEditCommand
{
	@Valid
    private ManufacturerForm manufacturerForm;
	
	public ManufacturerFormEditCommand() {
        this.manufacturerForm = new ManufacturerForm();
    }
}
