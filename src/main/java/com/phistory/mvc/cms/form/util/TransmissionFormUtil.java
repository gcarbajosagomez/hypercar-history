package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phistory.mvc.cms.form.TransmissionForm;
import com.tcp.data.model.transmission.Transmission;

/**
 *
 * @author Gonzalo
 */
public class TransmissionFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(TransmissionFormUtil.class);

    public static TransmissionForm createFormFromObject(Transmission transmission)
    {
        try
        {
            TransmissionForm form = new TransmissionForm(transmission.getId(),
            											 transmission.getType(),
            											 transmission.getNumOfGears(),
            											 transmission.getCar());

            return form;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TransmissionForm();
    }

    public static Transmission createObjectFromForm(TransmissionForm form)
    {
        try 
        {
            Transmission object = new Transmission(form.getId(),
            									   form.getType(),
            									   form.getNumOfGears(),
            									   form.getCar());

            return object;
        } 
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Transmission();
    }
}
