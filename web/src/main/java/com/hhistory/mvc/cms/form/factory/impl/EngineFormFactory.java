package com.hhistory.mvc.cms.form.factory.impl;

import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.hhistory.mvc.cms.form.EngineEditForm;
import com.hhistory.data.model.engine.Engine;

/**
 * Creates new Engines out of the data contained in EngineForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class EngineFormFactory implements EntityFormFactory<Engine, EngineEditForm> {

    @Override
    public EngineEditForm buildFormFromEntity(Engine engine) {
        try {
            return new EngineEditForm(engine.getId(),
                                      engine.getCode(),
                                      engine.getSize(),
                                      engine.getType(),
                                      engine.getCylinderDisposition(),
                                      engine.getCylinderBankAngle(),
                                      engine.getNumberOfCylinders(),
                                      engine.getNumberOfValves(),
                                      engine.getMaxPower(),
                                      engine.getMaxRPM(),
                                      engine.getMaxPowerRPM(),
                                      engine.getMaxTorque(),
                                      engine.getMaxTorqueRPM());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new EngineEditForm();
    }

    @Override
    public Engine buildEntityFromForm(EngineEditForm engineEditForm) {
        try {
            return new Engine(engineEditForm.getId(),
                              engineEditForm.getCode(),
                              engineEditForm.getSize(),
                              engineEditForm.getType(),
                              engineEditForm.getCylinderDisposition(),
                              engineEditForm.getCylinderBankAngle(),
                              engineEditForm.getNumberOfCylinders(),
                              engineEditForm.getNumberOfValves(),
                              engineEditForm.getMaxPower(),
                              engineEditForm.getMaxRPM(),
                              engineEditForm.getMaxPowerRPM(),
                              engineEditForm.getMaxTorque(),
                              engineEditForm.getMaxTorqueRPM());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new Engine();
    }
}
