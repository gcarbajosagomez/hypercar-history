package com.hhistory.mvc.cms.form;

import com.hhistory.data.model.brake.BrakeType;
import com.hhistory.data.model.brake.DiscBrake;
import com.hhistory.data.model.brake.DrumBrake;
import com.hhistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Brake set form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class BrakeSetEditForm implements EditForm {

    private Long id;

    private BrakeType frontBrakeType;
    private DiscBrake frontDiscBrake;
    private DrumBrake frontDrumBrake;

    private BrakeType rearBrakeType;
    private DiscBrake rearDiscBrake;
    private DrumBrake rearDrumBrake;

    private Car car;

    public BrakeSetEditForm() {
        this.frontDiscBrake = new DiscBrake();
        this.frontDrumBrake = new DrumBrake();
        this.rearDiscBrake = new DiscBrake();
        this.rearDrumBrake = new DrumBrake();
    }
}
