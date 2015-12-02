package com.phistory.mvc.cms.form.creator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.BrakeSetForm;
import com.tcp.data.model.brake.BrakeSet;

/**
 * Creates new BrakeSets out of the data contained in BrakeSetForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class BrakeSetFormCreator implements EntityFormCreator<BrakeSet, BrakeSetForm>
{
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
            log.error(ex.toString(), ex);
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
            log.error(ex.toString(), ex);
        }
        
        return new BrakeSet();
    }
}