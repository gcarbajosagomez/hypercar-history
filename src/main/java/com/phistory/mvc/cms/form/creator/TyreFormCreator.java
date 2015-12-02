package com.phistory.mvc.cms.form.creator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TyreForm;
import com.tcp.data.model.tyre.Tyre;

/**
 * Creates new TyreForms out of the data contained in TyreForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class TyreFormCreator implements EntityFormCreator<Tyre, TyreForm>
{
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
        catch (Exception e)
        {
            log.error(e.toString(), e);
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
        catch (Exception e) 
        {
            log.error(e.toString(), e);
        }
        
        return new Tyre();
    }
}
