package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.CarEditForm;
import com.phistory.mvc.cms.form.EditForm;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Command class to edit a Car form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class CarEditFormCommand implements EditFormCommand {

    @Valid
    @NotNull
    private EditForm editForm;

    public CarEditFormCommand() {
        this.editForm = new CarEditForm();
    }
}
