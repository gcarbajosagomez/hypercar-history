package com.phistory.mvc.cms.form.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TyreSetForm;
import com.tcp.data.model.tyre.TyreSet;

/**
 * Creates new TyreSets out of the data contained in TyreSetForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class TyreSetFormCreator implements EntityFormCreator<TyreSet, TyreSetForm>
{
    private static final Logger logger = LoggerFactory.getLogger(TyreSetFormCreator.class);

    /**
     * Create a new TyreSetForm out of the data contained in a TyreSet
     */
    @Override
    public TyreSetForm createFormFromEntity(TyreSet tyreSet)
    {
        try
        {
            TyreSetForm tyreSetForm = new TyreSetForm(tyreSet.getId(),
            								   		  tyreSet.getFrontTyre(),
            								   		  tyreSet.getBackTyre(),
            								   		  tyreSet.getCar());

            return tyreSetForm;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
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
            							  tyreSetForm.getFrontTyre(),
            							  tyreSetForm.getBackTyre(), 
            							  tyreSetForm.getCar());

            return tyreSet;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TyreSet();
    }
}
