package com.phistory.mvc.controller.util;

import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.BaseControllerData;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.sql.Blob;
import java.util.Objects;

/**
 * Set of utilities for the PictureController class
 *
 * @author gonzalo
 */
@Component
public class PictureControllerUtil extends BaseControllerData {

    private SqlPictureDAO      sqlSqlPictureDAO;
    private InMemoryPictureDAO inMemoryInMemoryPictureDAO;

    @Inject
    public PictureControllerUtil(SqlPictureDAO sqlSqlPictureDAO,
                                 InMemoryPictureDAO inMemoryInMemoryPictureDAO) {
        this.sqlSqlPictureDAO = sqlSqlPictureDAO;
        this.inMemoryInMemoryPictureDAO = inMemoryInMemoryPictureDAO;
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
        Blob image = picture.getImage();
        if (Objects.nonNull(picture) && Objects.nonNull(image)) {
            response.setContentType(IMAGE_CONTENT_TYPE);

            int imgBytesLength = (int) image.length();
            byte[] imgBytes = image.getBytes(1, imgBytesLength);

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
     * @throws Exception
     */
    public Picture loadPictureFromDB(PictureLoadCommand command) throws Exception {
        switch (command.getAction()) {
            case LOAD_CAR_PICTURE: {
                if (command.getEntityId() != null) {
                    return this.sqlSqlPictureDAO.getById(command.getEntityId());
                }
            }
            case LOAD_CAR_PREVIEW: {
                if (command.getEntityId() != null) {
                    return this.sqlSqlPictureDAO.getCarPreview(command.getEntityId());
                }
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getEntityId() != null) {
                    return this.sqlSqlPictureDAO.getManufacturerLogo(command.getEntityId());
                }
            }
            default: {
                if (command.getEntityId() != null) {
                    return this.sqlSqlPictureDAO.getById(command.getEntityId());
                }
            }
        }

        return sqlSqlPictureDAO.getById(command.getEntityId());
    }

    /**
     * Load a {@link Picture} from the in-memory storage depending on the action being performed
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
                    return this.loadById(pictureId);
                }
                break;
            }
            case LOAD_CAR_PREVIEW: {
                if (carId != null) {
                    Picture picture = this.inMemoryInMemoryPictureDAO.getCarPreview(carId);
                    if (picture == null) {
                        picture = this.sqlSqlPictureDAO.getCarPreview(pictureId);
                    }
                    return picture;
                }
                break;
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getEntityId() != null) {
                    return sqlSqlPictureDAO.getManufacturerLogo(command.getEntityId());
                }
                break;
            }
            default: {
                if (pictureId != null) {
                    return this.loadById(pictureId);
                }
            }
        }

        return this.sqlSqlPictureDAO.getById(pictureId);
    }

    private Picture loadById(Long pictureId) {
        Picture picture = this.inMemoryInMemoryPictureDAO.getById(pictureId);
        if (picture == null) {
            picture = this.sqlSqlPictureDAO.getById(pictureId);
        }
        return picture;
    }
}
