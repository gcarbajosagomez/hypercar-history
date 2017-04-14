package com.phistory.mvc.cms.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.mvc.cms.form.EngineForm;

/**
 * Command class to edit an Engine form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineFormEditCommand
{
	@Valid
    @NotNull
    private EngineForm engineForm = new EngineForm();
}
