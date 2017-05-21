package com.phistory.mvc.cms.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonDeserialize(as = ManufacturerEditForm.class)
    private EditForm editForm;

    public ManufacturerEditFormCommand() {
        this.editForm = new ManufacturerEditForm();
    }
}