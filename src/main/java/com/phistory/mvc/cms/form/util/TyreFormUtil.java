package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phistory.mvc.cms.form.TyreForm;
import com.tcp.data.model.tyre.Tyre;

/**
 *
 * @author Gonzalo
 */
public class TyreFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(TyreSetFormUtil.class);

    public static TyreForm createFormFromObject(Tyre tyre)
    {
        try
        {
            TyreForm form = new TyreForm(tyre.getId(),
            							 tyre.getWidth(),
            							 tyre.getProfile(),
            							 tyre.getRimDiameter(),
            							 tyre.getTrain());

            return form;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TyreForm();
    }

    public static Tyre createObjectFromForm(TyreForm form)
    {
        try
        {
            Tyre tyre = new Tyre(form.getId(),
            					 form.getWidth(),
            					 form.getProfile(),
            					 form.getRimDiameter(),
            					 form.getTrain());

            return tyre;
        }
        catch (Exception ex) 
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Tyre();
    }
}
