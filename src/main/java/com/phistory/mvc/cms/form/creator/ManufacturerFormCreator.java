
package com.phistory.mvc.cms.form.creator;

import java.sql.Blob;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.ManufacturerForm;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Manufacturer;
import com.tcp.data.model.Picture;
import com.tcp.data.model.util.PictureUtil;

/**
 * Creates new Manufacturers out of the data contained in ManufacturersForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class ManufacturerFormCreator implements EntityFormCreator<Manufacturer, ManufacturerForm>
{
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
            											 		     null,
            											 		     manufacturer.getStory());
            if (manufacturer.getId() != null)
            {
            	try
            	{
            		PictureEditCommand pictureEditCommand = new PictureEditCommand(new Picture(), null);
            		Picture carPreview = pictureDao.getManufacturerLogo(manufacturer.getId());
            		
            		if (carPreview != null)
            		{
            			pictureEditCommand.setPicture(carPreview);
            		}
            		
            		manufacturerForm.setPreviewPictureEditCommand(pictureEditCommand);
            	}
            	catch(Exception e)
            	{
            		log.error(e.toString(), e);
            	}
            }
            
            return manufacturerForm;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
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
        	Blob logoImage = null;        	
        	Picture logo = manufacturerForm.getPreviewPictureEditCommand().getPicture();
        	
        	if (logo != null && logo.getImage().length() > 0)
        	{
        		logoImage = logo.getImage();
        	}
        	else
        	{
        		logoImage = PictureUtil.createPictureFromMultipartFile(manufacturerForm.getPreviewPictureEditCommand().getPictureFile(), pictureDao);
        	}
        	
            Manufacturer object = new Manufacturer(manufacturerForm.getId(),
            									   manufacturerForm.getName(),
            									   manufacturerForm.getNationality(),
            									   logoImage,
            									   manufacturerForm.getStory());

            return object;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return new Manufacturer();
    }
}
