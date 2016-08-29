package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.mvc.cms.form.CarForm;

/**
 * Command class to edit a Car form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarFormEditCommand
{
    @Valid
    private CarForm carForm = new CarForm();
}
