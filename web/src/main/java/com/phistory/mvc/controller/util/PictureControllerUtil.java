package com.phistory.mvc.controller.util;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.sql.impl.PictureDAO;
import com.phistory.data.model.Picture;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.BaseControllerData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static com.phistory.data.model.Picture.PictureType.PREVIEW_PICTURE;

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
	private PictureDAO pictureDAO;
    @Inject
	private com.phistory.data.dao.inmemory.PictureDAO inMemoryPictureDAO;
	
	/**
	 * Handle the save or edition of a Picture
	 * 
	 * @param pictureEditCommand
	 * @throws Exception
	 */
	public void saveOrEditPicture(PictureEditCommand pictureEditCommand) throws Exception
    {
        MultipartFile pictureFile = pictureEditCommand.getPictureFile();
        Picture picture = pictureEditCommand.getPicture();

    	if (pictureFile != null && pictureFile.getSize() > 0)
        {      
    		PictureDataCommand pictureDataCommand = new PictureDataCommand(pictureFile, null);
            pictureDataCommand.setPicture(picture);
            this.pictureDAO.saveOrEdit(pictureDataCommand);
            pictureEditCommand.setPicture(this.pictureDAO.getCarPreview(picture.getCar().getId()));
        }
    }
	
	/**
	 * Load a {@link Picture} from the DB depending on the action being performed
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public Picture loadPictureFromDB(PictureLoadCommand command) throws Exception
	{
        switch (command.getAction()) {
            case LOAD_CAR_PICTURE: {
                if (command.getPictureId() != null) {
                    return this.pictureDAO.getById(command.getPictureId());
                }
            }
            case LOAD_CAR_PREVIEW: {
                if (command.getCarId() != null) {
                    return this.pictureDAO.getCarPreview(command.getCarId());
                }
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getManufacturerId() != null) {
                    return this.pictureDAO.getManufacturerLogo(command.getManufacturerId());
                }
            }
			default: {
				if (command.getPictureId() != null) {
					return this.pictureDAO.getById(command.getPictureId());
				}
			}
        }

        return pictureDAO.getById(command.getPictureId());
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

	/**
	 * Load a {@link Picture} from the DB depending on the action being performed
	 *
	 * @param command
	 * @return
	 */
	public Picture loadPicture(PictureLoadCommand command)
	{
		Long pictureId = command.getPictureId();
		Long carId = command.getCarId();

		switch (command.getAction()) {
			case LOAD_CAR_PICTURE: {
				if (pictureId != null) {
                    this.inMemoryPictureDAO.getById(pictureId);
				}
			}
			case LOAD_CAR_PREVIEW: {
				if (carId != null) {
					return this.inMemoryPictureDAO.getPictures().stream()
							.filter(picture -> picture.getType().equals(PREVIEW_PICTURE))
							.filter(picture -> picture.getCar() != null && picture.getCar().getId().equals(carId))
							.findFirst()
							.orElse(null);
				}
			}
			case LOAD_MANUFACTURER_LOGO: {
				if (command.getManufacturerId() != null) {
					return pictureDAO.getManufacturerLogo(command.getManufacturerId());
				}
			}
			default: {
				if (command.getPictureId() != null) {
                    this.inMemoryPictureDAO.getById(pictureId);
				}
			}
		}

		return this.inMemoryPictureDAO.getById(pictureId);
	}
}
