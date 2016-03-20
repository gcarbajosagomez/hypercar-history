package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.mvc.cms.form.ManufacturerForm;

/**
 * Command class to edit a Manufacturer form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerFormEditCommand
{
	@Valid
    private ManufacturerForm manufacturerForm = new ManufacturerForm();
}
