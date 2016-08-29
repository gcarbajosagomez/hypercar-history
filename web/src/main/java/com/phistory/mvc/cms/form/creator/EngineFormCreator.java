package com.phistory.mvc.cms.form.creator;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.EngineForm;
import com.phistory.data.model.engine.Engine;

/**
 * Creates new Engines out of the data contained in EngineForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class EngineFormCreator implements EntityFormCreator<Engine, EngineForm>
{
    /**
     * Create a new EngineForm out of the data contained in an Engine
     */
    @Override
    public EngineForm createFormFromEntity(Engine engine)
    {
        try
        {
            EngineForm engineForm = new EngineForm(engine.getId(),
            								 	   engine.getCode(),
            								 	   engine.getSize(),
            								 	   engine.getType(),
            								 	   engine.getCylinderDisposition(),
            								 	   engine.getNumberOfCylinders(),
            								 	   engine.getNumberOfValves(),
            								 	   engine.getMaxPower(),
            								 	   engine.getMaxRPM(),
            								 	   engine.getMaxPowerRPM(),
            								 	   engine.getMaxTorque(),
            								 	   engine.getMaxTorqueRPM());
            return engineForm;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return new EngineForm();
    }

    /**
     * Create a new Engine out of the data contained in an EngineForm
     */
    @Override
    public Engine createEntityFromForm(EngineForm engineForm)
    {
        try
        {
            Engine engine = new Engine(engineForm.getId(),
            						   engineForm.getCode(),
            						   engineForm.getSize(),
            						   engineForm.getType(),
            						   engineForm.getCylinderDisposition(),
            						   engineForm.getNumberOfCylinders(),
            						   engineForm.getNumberOfValves(),
            						   engineForm.getMaxPower(),
            						   engineForm.getMaxRPM(),
            						   engineForm.getMaxPowerRPM(),
            						   engineForm.getMaxTorque(),
            						   engineForm.getMaxTorqueRPM());
            
            return engine;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return new Engine();
    }
}
