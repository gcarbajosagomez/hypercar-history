package com.phistory.mvc.controller.util;

import com.phistory.data.dao.sql.impl.PictureDAO;
import com.phistory.data.model.Picture;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.BaseControllerData;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * Set of utilities for the PictureController class
 *
 * @author gonzalo
 */
@Component
public class PictureControllerUtil extends BaseControllerData {
    @Inject
    private PictureDAO pictureDAO;
    @Inject
    private com.phistory.data.dao.inmemory.PictureDAO inMemoryPictureDAO;

    /**
     * Load a {@link Picture} from the DB depending on the action being performed
     *
     * @param command
     * @return
     * @throws Exception
     */
    public Picture loadPictureFromDB(PictureLoadCommand command) throws Exception {
        switch (command.getAction()) {
            case LOAD_CAR_PICTURE: {
                if (command.getEntityId() != null) {
                    return this.pictureDAO.getById(command.getEntityId());
                }
            }
            case LOAD_CAR_PREVIEW: {
                if (command.getEntityId() != null) {
                    return this.pictureDAO.getCarPreview(command.getEntityId());
                }
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getEntityId() != null) {
                    return this.pictureDAO.getManufacturerLogo(command.getEntityId());
                }
            }
			default: {
				if (command.getEntityId() != null) {
					return this.pictureDAO.getById(command.getEntityId());
				}
			}
        }

        return pictureDAO.getById(command.getEntityId());
    }

    /**
     * Print the binary information of a Picture to a HTTP response
     *
     * @param picture
     * @param response
     * @return
     * @throws Exception
     */
    public HttpServletResponse printPictureToResponse(Picture picture, HttpServletResponse response) throws Exception {
        if (picture != null && picture.getImage() != null) {
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
    public Picture loadPicture(PictureLoadCommand command) {
        Long pictureId = command.getEntityId();
        Long carId = command.getEntityId();

		switch (command.getAction()) {
			case LOAD_CAR_PICTURE: {
				if (pictureId != null) {
                    return this.inMemoryPictureDAO.getById(pictureId);
				}
			}
			case LOAD_CAR_PREVIEW: {
				if (carId != null) {
					return this.inMemoryPictureDAO.loadPreview(carId);
				}
			}
			case LOAD_MANUFACTURER_LOGO: {
				if (command.getEntityId() != null) {
					return pictureDAO.getManufacturerLogo(command.getEntityId());
				}
			}
			default: {
				if (carId != null) {
                    return this.inMemoryPictureDAO.getById(pictureId);
				}
			}
		}

		return this.inMemoryPictureDAO.getById(pictureId);
	}
}
