package com.phistory.mvc.cms.form.util;


import com.phistory.mvc.cms.form.EngineForm;
import com.tcp.data.model.engine.Engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gonzalo
 */
public class EngineFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(EngineFormUtil.class);

    public static EngineForm createFormFromObject(Engine engine)
    {
        try
        {
            EngineForm form = new EngineForm(engine.getId(),
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
            return form;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new EngineForm();
    }

    public static Engine createObjectFromForm(EngineForm form)
    {
        try
        {
            Engine engine = new Engine(form.getId(),
            						   form.getCode(),
            						   form.getSize(),
            						   form.getType(),
            						   form.getCylinderDisposition(),
            						   form.getNumberOfCylinders(),
            						   form.getNumberOfValves(),
            						   form.getMaxPower(),
            						   form.getMaxRPM(),
            						   form.getMaxPowerRPM(),
            						   form.getMaxTorque(),
            						   form.getMaxTorqueRPM());
            
            return engine;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Engine();
    }
}
