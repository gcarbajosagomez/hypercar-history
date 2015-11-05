
package com.phistory.mvc.cms.form.creator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.ManufacturerForm;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Manufacturer;
import com.tcp.data.model.util.PictureUtil;

/**
 * Creates new Manufacturers out of the data contained in ManufacturersForms and vice versa
 * 
 * @author Gonzalo
 */
@Component
public class ManufacturerFormCreator implements EntityFormCreator<Manufacturer, ManufacturerForm>
{
    private static final Logger logger = LoggerFactory.getLogger(ManufacturerFormCreator.class);
    @Inject
    private PictureDao pictureDao;

    /**
     * Create a new ManufacturerForm out of the data contained in a Manufacturer
     */
    @Override
    public ManufacturerForm createFormFromEntity(Manufacturer manufacturer)
    {
        try
        {
            ManufacturerForm manufacturerForm = new ManufacturerForm(manufacturer.getId(),
            											 		     manufacturer.getName(),
            											 		     manufacturer.getNationality(),
            											 		     manufacturer.getLogo(),
            											 		     manufacturer.getStory());

            return manufacturerForm;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new ManufacturerForm();
    }

    /**
     * Create a new Manufacturer out of the data contained in a ManufacturerForm
     */
    @Override
    public Manufacturer createEntityFromForm(ManufacturerForm manufacturerForm)
    {
        try
        {
            Manufacturer object = new Manufacturer(manufacturerForm.getId(),
            									   manufacturerForm.getName(),
            									   manufacturerForm.getNationality(),
            									   PictureUtil.createPictureFromMultipartFile(manufacturerForm.getLogoPictureFile(), pictureDao),
            									   manufacturerForm.getStory());

            return object;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Manufacturer();
    }
}
