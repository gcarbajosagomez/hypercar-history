
package com.phistory.mvc.cms.form.creator;

import java.sql.Blob;
import java.util.Optional;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public ManufacturerForm createFormFromEntity(Manufacturer manufacturer) throws Exception
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
            		Optional<Picture> carPreview = Optional.of(pictureDao.getManufacturerLogo(manufacturer.getId()));
            		
            		if (carPreview.isPresent())
            		{
            			pictureEditCommand.setPicture(carPreview.get());
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
            throw e;
        }
    }

    /**
     * Create a new Manufacturer out of the data contained in a ManufacturerForm
     * @throws Exception 
     */
    @Override
    public Manufacturer createEntityFromForm(ManufacturerForm manufacturerForm) throws Exception
    {
        try
        {
        	Optional<MultipartFile> logoFile = Optional.ofNullable(manufacturerForm.getPreviewPictureEditCommand().getPictureFile());
        	Optional<Blob> logo = Optional.empty();
        	
        	if (manufacturerForm.getPreviewPictureEditCommand().getPicture() != null)
        	{
        		logo = Optional.ofNullable(manufacturerForm.getPreviewPictureEditCommand().getPicture().getImage());  
        	}
        	        	
        	if ((logoFile.isPresent() && logoFile.get().getSize() > 0) && (!logo.isPresent() || (logo.isPresent() && logo.get().length() == 0)))
        	{        		
        		logo = Optional.of(PictureUtil.createPictureFromMultipartFile(logoFile.get(), pictureDao));
        	}
        	
            Manufacturer object = new Manufacturer(manufacturerForm.getId(),
            									   manufacturerForm.getName(),
            									   manufacturerForm.getNationality(),
            									   logo.isPresent() ? logo.get() : null,
            									   manufacturerForm.getStory());

            return object;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
