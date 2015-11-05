package com.phistory.mvc.cms.form.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TyreForm;
import com.tcp.data.model.tyre.Tyre;

/**
 * Creates new TyreForms out of the data contained in TyreForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class TyreFormCreator implements EntityFormCreator<Tyre, TyreForm>
{
    private static final Logger logger = LoggerFactory.getLogger(TyreFormCreator.class);

    /**
     * Create a new TyreForm out of the data contained in a Tyre
     */
    @Override
    public TyreForm createFormFromEntity(Tyre tyre)
    {
        try
        {
            TyreForm tyreForm = new TyreForm(tyre.getId(),
            							 	 tyre.getWidth(),
            							 	 tyre.getProfile(),
            							 	 tyre.getRimDiameter(),
            							 	 tyre.getTrain());

            return tyreForm;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TyreForm();
    }

    /**
     * Create a new Tyre out of the data contained in a TyreForm
     */
    @Override
    public Tyre createEntityFromForm(TyreForm tyreForm)
    {
        try
        {
            Tyre tyre = new Tyre(tyreForm.getId(),
            					 tyreForm.getWidth(),
            					 tyreForm.getProfile(),
            					 tyreForm.getRimDiameter(),
            					 tyreForm.getTrain());

            return tyre;
        }
        catch (Exception ex) 
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Tyre();
    }
}
