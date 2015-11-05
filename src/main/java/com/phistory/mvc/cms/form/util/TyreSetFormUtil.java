package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phistory.mvc.cms.form.TyreSetForm;
import com.tcp.data.model.tyre.TyreSet;

/**
 *
 * @author Gonzalo
 */
public class TyreSetFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(TyreSetFormUtil.class);

    public static TyreSetForm createFormFromObject(TyreSet tyreSet)
    {
        try
        {
            TyreSetForm form = new TyreSetForm(tyreSet.getId(),
            								   tyreSet.getFrontTyre(),
            								   tyreSet.getBackTyre(),
            								   tyreSet.getCar());

            return form;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TyreSetForm();
    }

    public static TyreSet createObjectFromForm(TyreSetForm form)
    {
        try 
        {
            TyreSet tyreSet = new TyreSet(form.getId(),
            							  form.getFrontTyre(),
            							  form.getBackTyre(), 
            							  form.getCar());

            return tyreSet;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new TyreSet();
    }
}
