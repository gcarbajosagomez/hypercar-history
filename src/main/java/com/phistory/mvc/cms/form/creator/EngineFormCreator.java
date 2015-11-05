package com.phistory.mvc.cms.form.creator;


import com.phistory.mvc.cms.form.EngineForm;
import com.tcp.data.model.engine.Engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Creates new Engines out of the data contained in EngineForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class EngineFormCreator implements EntityFormCreator<Engine, EngineForm>
{
    private static final Logger logger = LoggerFactory.getLogger(EngineFormCreator.class);

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
            logger.error(e.toString(), e);
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
            logger.error(e.toString(), e);
        }
        
        return new Engine();
    }
}
