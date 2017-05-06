package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.ManufacturerEditForm;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;

/**
 * Command class to edit a Manufacturer form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class ManufacturerEditFormCommand implements EditFormCommand {

    @Valid
    private EditForm editForm;

    public ManufacturerEditFormCommand() {
        this.editForm = new ManufacturerEditForm();
    }
}
