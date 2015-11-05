package com.phistory.mvc.cms.command;

import javax.validation.Valid;

import com.phistory.mvc.cms.form.CarForm;

/**
 * Command class to edit a Car form
 *
 * @author Gonzalo
 */
public class CarFormEditCommand
{
    @Valid
    private CarForm carForm;

    public CarFormEditCommand() {
        this.carForm = new CarForm();
    }

    public CarForm getCarForm() {
        return carForm;
    }

    public void setCarForm(CarForm carForm) {
        this.carForm = carForm;
    }    
}
