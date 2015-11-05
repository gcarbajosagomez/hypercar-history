package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import com.phistory.mvc.cms.form.ManufacturerForm;

/**
 * Command class to edit a Manufacturer form
 *
 * @author Gonzalo
 */
public class ManufacturerFormEditCommand
{
	@Valid
    private ManufacturerForm manufacturerForm;

    public ManufacturerFormEditCommand() {
        this.manufacturerForm = new ManufacturerForm();
    }

    public ManufacturerFormEditCommand(ManufacturerForm manufacturerForm) {
        this.manufacturerForm = manufacturerForm;
    }

    public ManufacturerForm getManufacturerForm() {
        return manufacturerForm;
    }

    public void setManufacturerForm(ManufacturerForm manufacturerForm) {
        this.manufacturerForm = manufacturerForm;
    }  
}
