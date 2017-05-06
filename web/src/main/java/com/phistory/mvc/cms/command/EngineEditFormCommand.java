package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.EngineEditForm;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Command class to edit an Engine form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class EngineEditFormCommand {

    @Valid
    @NotNull
    private EngineEditForm engineEditForm;

    public EngineEditFormCommand() {
        this.engineEditForm = new EngineEditForm();
    }
}
