package com.phistory.mvc.cms.form.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.TransmissionForm;
import com.tcp.data.model.transmission.Transmission;

/**
 * Creates new Transmissions out of the data contained in TransmissionForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class TransmissionFormCreator implements EntityFormCreator<Transmission, TransmissionForm>
{
    private static final Logger logger = LoggerFactory.getLogger(TransmissionFormCreator.class);

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
            logger.error(ex.toString(), ex);
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
            logger.error(ex.toString(), ex);
        }
        
        return new Transmission();
    }
}
