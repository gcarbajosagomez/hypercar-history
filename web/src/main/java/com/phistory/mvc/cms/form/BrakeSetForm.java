package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.phistory.data.model.brake.Brake;
import com.phistory.data.model.car.Car;

/**
 * Brake set form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class BrakeSetForm {

    private Long  id;
    private Brake frontBrake;
    private Brake rearBrake;
    private Car   car;

    public BrakeSetForm() {
        frontBrake = new Brake();
        rearBrake = new Brake();
    }
}
