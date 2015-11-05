
package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phistory.mvc.cms.form.ManufacturerForm;
import com.tcp.data.dao.Dao;
import com.tcp.data.model.Manufacturer;
import com.tcp.data.model.util.PictureUtil;

/**
 *
 * @author Gonzalo
 */
public class ManufacturerFormUtil
{

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerFormUtil.class);

    public static ManufacturerForm createFormFromObject(Manufacturer object)
    {
        try
        {
            ManufacturerForm form = new ManufacturerForm(object.getId(),
            											 object.getName(),
            											 object.getNationality(),
            											 object.getLogo(),
            											 object.getStory());

            return form;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new ManufacturerForm();
    }

    public static Manufacturer createObjectFromForm(ManufacturerForm form, Dao<Manufacturer, Long> dao)
    {
        try
        {
            Manufacturer object = new Manufacturer(form.getId(),
            									   form.getName(),
            									   form.getNationality(),
            									   PictureUtil.createPictureFromMultipartFile(form.getLogoPictureFile(), dao),
            									   form.getStory());

            return object;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Manufacturer();
    }
}
