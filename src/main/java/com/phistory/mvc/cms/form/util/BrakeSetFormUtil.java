package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phistory.mvc.cms.form.BrakeSetForm;
import com.tcp.data.model.brake.BrakeSet;

/**
 *
 * @author Gonzalo
 */
public class BrakeSetFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(BrakeSetFormUtil.class);

    public static BrakeSetForm createFormFromObject(BrakeSet brakeSet)
    {
        try 
        {
            BrakeSetForm form = new BrakeSetForm(brakeSet.getId(),
            									 brakeSet.getFrontBrake(),
            									 brakeSet.getBackBrake(),
            									 brakeSet.getCar());

            return form;
        } 
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new BrakeSetForm();
    }

    public static BrakeSet createObjectFromForm(BrakeSetForm form)
    {
        try
        {
            BrakeSet brakeSet = new BrakeSet(form.getId(),
            								 form.getFrontBrake(),
            								 form.getBackBrake(),
            								 form.getCar());

            return brakeSet;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new BrakeSet();
    }
}