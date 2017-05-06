package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.BrakeSetForm;
import com.phistory.data.model.brake.BrakeSet;

/**
 * Creates new BrakeSets out of the data contained in BrakeSetForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class BrakeSetFormFactory implements EntityFormFactory<BrakeSet, BrakeSetForm> {

    @Override
    public BrakeSetForm buildFormFromEntity(BrakeSet brakeSet) {
        try {
            return new BrakeSetForm(brakeSet.getId(),
                                    brakeSet.getFrontBrake(),
                                    brakeSet.getRearBrake(),
                                    brakeSet.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new BrakeSetForm();
    }

    @Override
    public BrakeSet buildEntityFromForm(BrakeSetForm brakeSetForm) {
        try {
            return new BrakeSet(brakeSetForm.getId(),
                                brakeSetForm.getFrontBrake(),
                                brakeSetForm.getRearBrake(),
                                brakeSetForm.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new BrakeSet();
    }
}