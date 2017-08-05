package com.hhistory.mvc.cms.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hhistory.mvc.cms.form.EditForm;
import com.hhistory.mvc.cms.form.EngineEditForm;
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
public class EngineEditFormCommand implements EditFormCommand {

    @Valid
    @NotNull
    @JsonDeserialize(as = EngineEditForm.class)
    private EditForm editForm;

    public EngineEditFormCommand() {
        this.editForm = new EngineEditForm();
    }
}
