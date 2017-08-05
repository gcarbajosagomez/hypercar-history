package com.hhistory.mvc.cms.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hhistory.mvc.cms.form.CarEditForm;
import com.hhistory.mvc.cms.form.EditForm;
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
    @JsonDeserialize(as = CarEditForm.class)
    private EditForm editForm;

    public CarEditFormCommand() {
        this.editForm = new CarEditForm();
    }
}
