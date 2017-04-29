package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TransmissionForm;
import com.phistory.data.model.transmission.Transmission;

/**
 * Creates new Transmissions out of the data contained in TransmissionForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class TransmissionFormFactory implements EntityFormFactory<Transmission, TransmissionForm>
{
    /**
     * Create a new TransmissionForm out of the data contained in a Transmission
     */
    @Override
    public TransmissionForm createFormFromEntity(Transmission transmission)
    {
        try
        {
            TransmissionForm transmissionForm = new TransmissionForm(transmission.getId(),
            											 	 	     transmission.getType(),
            											 	 	     transmission.getNumOfGears(),
            											 	 	     transmission.getCar());

            return transmissionForm;
        }
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new TransmissionForm();
    }

    /**
     * Create a new Transmission out of the data contained in a TransmissionForm
     */
    @Override
    public Transmission createEntityFromForm(TransmissionForm transmissionForm)
    {
        try 
        {
            Transmission transmission = new Transmission(transmissionForm.getId(),
            									   		 transmissionForm.getType(),
            									   		 transmissionForm.getNumOfGears(),
            									   		 transmissionForm.getCar());

            return transmission;
        } 
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new Transmission();
    }
}
