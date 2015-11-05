package com.phistory.mvc.controller.cms.util;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.tcp.data.command.PictureDataCommand;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Picture;
import com.tcp.data.model.Picture.PictureType;

/**
 * Set of utilities for the PictureController class
 * 
 * @author gonzalo
 *
 */
@Component
public class PictureControllerUtil
{	
	@Inject
	private PictureDao pictureDao;
	
	/**
	 * Handle the save or edition of a Picture
	 * 
	 * @param command
	 * @throws Exception
	 */
	public void saveOrEditPicture(PictureEditCommand pictureEditCommand) throws Exception
    {
    	if (pictureEditCommand.getPictureFile() != null && pictureEditCommand.getPictureFile().getSize() > 0)
        {      
    		PictureDataCommand pictureDataCommand = new PictureDataCommand(pictureEditCommand.getPictureFile(), null);
    				
    		if(pictureEditCommand.getPicture().getType().equals(PictureType.PREVIEW_PICTURE))
    		{
    			//we'll override the existing preview picture if any, avoiding creating multiple previews for a given car
    			Picture previewPicture = checkIfPreviewImageExistsForCar(pictureEditCommand);
    			
    			if (previewPicture == null)
    			{
    				previewPicture = pictureEditCommand.getPicture();
    			}
    			
    			pictureDataCommand.setPicture(previewPicture);
    		}
    		else
    		{
    			pictureDataCommand.setPicture(pictureEditCommand.getPicture());
    		}    		
    																	   
            pictureDao.saveOrEditPicture(pictureDataCommand);
        }
    }
	
	/**
	 * Check whether or not a preview image for a given car already exists in the database. There can only be one preview image per car
	 * 
	 * @param pictureEditCommand
	 * @return The preview picture if there is already one in the DB, null otherwise
	 */
	private Picture checkIfPreviewImageExistsForCar(PictureEditCommand pictureEditCommand)
	{
		Long carId = pictureEditCommand.getPicture().getCar().getId();
		
		if (carId != null)
		{
			return pictureDao.getCarPreview(carId);
		}
		
		return null;
	}
}
