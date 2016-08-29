package com.phistory.mvc.controller.cms.util;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.BaseControllerData;
import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.impl.PictureDao;
import com.phistory.data.model.Picture;
import com.phistory.data.model.Picture.PictureType;
import org.springframework.util.StringUtils;

/**
 * Set of utilities for the PictureController class
 * 
 * @author gonzalo
 *
 */
@Component
public class PictureControllerUtil extends BaseControllerData
{	
	@Inject
	private PictureDao pictureDao;
	
	/**
	 * Handle the save or edition of a Picture
	 * 
	 * @param pictureEditCommand
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
	
	/**
	 * Load a picture depending on the action being performed
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public Picture loadPicture(PictureLoadCommand command) throws Exception
	{
		String action = command.getAction();

        if (StringUtils.hasText(action)) {
            switch (command.getAction()) {
                case LOAD_CAR_PICTURE_ACTION: {
                    if (command.getPictureId() != null) {
                        return pictureDao.getById(command.getPictureId());
                    }
                }
                case LOAD_CAR_PREVIEW_ACTION: {
                    if (command.getCarId() != null) {
                        return pictureDao.getCarPreview(command.getCarId());
                    }
                }
                case LOAD_MANUFACTURER_LOGO_ACTION: {
                    if (command.getManufacturerId() != null) {
                        return pictureDao.getManufacturerLogo(command.getManufacturerId());
                    }
                }
            }
        }
		
		return pictureDao.getById(command.getPictureId());
	}
	
	/**
	 * Print the binary information of a Picture to a HTTP response
	 * 
	 * @param picture
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse printPictureToResponse(Picture picture, HttpServletResponse response) throws Exception
	{		
		if (picture != null && picture.getImage() != null)
		{
			response.setContentType(IMAGE_CONTENT_TYPE);

			int imgBytesLength = (int) picture.getImage().length();
			byte[] imgBytes = picture.getImage().getBytes(1, imgBytesLength);

			response.getOutputStream().write(imgBytes);
			response.getOutputStream().flush();
		}
		
		return response;
	}
}
