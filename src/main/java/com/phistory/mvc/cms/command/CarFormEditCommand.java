package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import lombok.Data;

import com.phistory.mvc.cms.form.CarForm;

/**
 * Command class to edit a Car form
 *
 * @author Gonzalo
 */
@Data
public class CarFormEditCommand
{
    @Valid
    private CarForm carForm;
    
    public CarFormEditCommand() {
        this.carForm = new CarForm();
    }
}
