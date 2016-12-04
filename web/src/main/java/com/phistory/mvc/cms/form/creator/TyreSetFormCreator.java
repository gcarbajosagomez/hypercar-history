package com.phistory.mvc.cms.form.creator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TyreSetForm;
import com.phistory.data.model.tyre.TyreSet;

/**
 * Creates new TyreSets out of the data contained in TyreSetForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class TyreSetFormCreator implements EntityFormCreator<TyreSet, TyreSetForm>
{
	/**
     * Create a new TyreSetForm out of the data contained in a TyreSet
     */
    @Override
    public TyreSetForm createFormFromEntity(TyreSet tyreSet)
    {
        try
        {
            TyreSetForm tyreSetForm = new TyreSetForm(tyreSet.getId(),
                                                      tyreSet.getManufacturer(),
                                                      tyreSet.getType(),
                                                      tyreSet.getModel(),
            								   		  tyreSet.getFrontTyre(),
            								   		  tyreSet.getBackTyre(),
            								   		  tyreSet.getCar());

            return tyreSetForm;
        }
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new TyreSetForm();
    }

    /**
     * Create a new TyreSet out of the data contained in a TyreSetForm
     */
    @Override
    public TyreSet createEntityFromForm(TyreSetForm tyreSetForm)
    {
        try 
        {
            TyreSet tyreSet = new TyreSet(tyreSetForm.getId(),
                                          tyreSetForm.getManufacturer(),
                                          tyreSetForm.getType(),
                                          tyreSetForm.getModel(),
            							  tyreSetForm.getFrontTyre(),
            							  tyreSetForm.getBackTyre(), 
            							  tyreSetForm.getCar());

            return tyreSet;
        }
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new TyreSet();
    }
}
