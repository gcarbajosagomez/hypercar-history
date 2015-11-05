package com.phistory.mvc.cms.form.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.BrakeSetForm;
import com.tcp.data.model.brake.BrakeSet;

/**
 * Creates new BrakeSets out of the data contained in BrakeSetForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class BrakeSetFormCreator implements EntityFormCreator<BrakeSet, BrakeSetForm>
{
    private static final Logger logger = LoggerFactory.getLogger(BrakeSetFormCreator.class);

    /**
     * Create a new BrakeSetForm out of the data contained in a BrakeSet
     */
    @Override
    public BrakeSetForm createFormFromEntity(BrakeSet brakeSet)
    {
        try 
        {
            BrakeSetForm BrakeSetForm = new BrakeSetForm(brakeSet.getId(),
            									 		 brakeSet.getFrontBrake(),
            									 		 brakeSet.getBackBrake(),
            									 		 brakeSet.getCar());

            return BrakeSetForm;
        } 
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new BrakeSetForm();
    }

    /**
     * Create a new BrakeSet out of the data contained in a BrakeSetForm
     */
    @Override
    public BrakeSet createEntityFromForm(BrakeSetForm brakeSetForm)
    {
        try
        {
            BrakeSet brakeSet = new BrakeSet(brakeSetForm.getId(),
            								 brakeSetForm.getFrontBrake(),
            								 brakeSetForm.getBackBrake(),
            								 brakeSetForm.getCar());

            return brakeSet;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new BrakeSet();
    }
}