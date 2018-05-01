package com.hhistory.mvc.cms.form.factory.impl;

import com.hhistory.data.model.brake.*;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.hhistory.mvc.cms.form.BrakeSetEditForm;

import static com.hhistory.data.model.brake.BrakeType.*;

/**
 * Creates new BrakeSets out of the data contained in BrakeSetForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class BrakeSetFormFactory implements EntityFormFactory<BrakeSet, BrakeSetEditForm> {

    @Override
    public BrakeSetEditForm buildFormFromEntity(BrakeSet brakeSet) {
        try {
            Brake frontBrake = brakeSet.getFrontBrake();
            BrakeType frontBrakeType = frontBrake.getType();
            DiscBrake frontDiscBrake = frontBrakeType == DISC ? (DiscBrake) frontBrake : new DiscBrake();
            DrumBrake frontDrumBrake = frontBrakeType == DRUM ? (DrumBrake) frontBrake : new DrumBrake();

            Brake rearBrake = brakeSet.getRearBrake();
            BrakeType rearBrakeType = rearBrake.getType();
            DiscBrake rearDiscBrake = rearBrakeType == DISC ? (DiscBrake) rearBrake : new DiscBrake();
            DrumBrake rearDrumBrake = rearBrakeType == DRUM ? (DrumBrake) rearBrake : new DrumBrake();

            return new BrakeSetEditForm(brakeSet.getId(),
                                        frontBrakeType,
                                        frontDiscBrake,
                                        frontDrumBrake,
                                        rearBrakeType,
                                        rearDiscBrake,
                                        rearDrumBrake,
                                        brakeSet.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new BrakeSetEditForm();
    }

    @Override
    public BrakeSet buildEntityFromForm(BrakeSetEditForm brakeSetEditForm) {
        try {
            Brake frontBrake = buildBrakeByType(brakeSetEditForm.getFrontBrakeType(),
                                                brakeSetEditForm.getFrontDiscBrake());

            Brake rearBrake = buildBrakeByType(brakeSetEditForm.getRearBrakeType(),
                                               brakeSetEditForm.getRearDiscBrake());

            return new BrakeSet(brakeSetEditForm.getId(),
                                frontBrake,
                                rearBrake,
                                brakeSetEditForm.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new BrakeSet();
    }

    private Brake buildBrakeByType(BrakeType brakeType, Brake brake) {
        if (brakeType == DRUM) {
            return new DrumBrake(brake.getId(), brake.getTrain());
        }
        return brake;
    }
}