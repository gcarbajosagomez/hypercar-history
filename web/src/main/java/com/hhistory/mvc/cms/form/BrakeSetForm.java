package com.hhistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.hhistory.data.model.brake.Brake;
import com.hhistory.data.model.car.Car;

/**
 * Brake set form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class BrakeSetForm implements EditForm {

    private Long  id;
    private Brake frontBrake;
    private Brake rearBrake;
    private Car   car;

    public BrakeSetForm() {
        this.frontBrake = new Brake();
        this.rearBrake = new Brake();
    }
}
