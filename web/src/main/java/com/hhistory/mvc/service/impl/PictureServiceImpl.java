package com.hhistory.mvc.service.impl;

import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.command.PictureLoadCommand;
import com.hhistory.mvc.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;
import static com.hhistory.mvc.controller.BaseControllerData.IMAGE_CONTENT_TYPE;

/**
 * @author gonzalo
 */
@Component
@Slf4j
public class PictureServiceImpl implements PictureService {

    private SqlPictureDAO        sqlPictureDAO;
    private SqlPictureRepository sqlPictureRepository;
    private PictureDAO           pictureDAO;

    @Inject
    public PictureServiceImpl(SqlPictureDAO sqlPictureDAO,
                              SqlPictureRepository sqlPictureRepository,
                              @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO) {
        this.sqlPictureDAO = sqlPictureDAO;
        this.sqlPictureRepository = sqlPictureRepository;
        this.pictureDAO = pictureDAO;
    }

    @Override
    public HttpServletResponse printPictureToResponse(Picture picture, HttpServletResponse response) {
        return Optional.ofNullable(picture)
                       .map(Picture::getImage)
                       .map(image -> {
                           response.setContentType(IMAGE_CONTENT_TYPE);
                           try {
                               int imgBytesLength = (int) image.length();
                               byte[] imgBytes = image.getBytes(1, imgBytesLength);

                               response.getOutputStream().write(imgBytes);
                               response.getOutputStream().flush();
                           } catch (Exception e) {
                               log.error("There was an error while printing picture image to HTTP response", e);
                           }
                           return response;
                       })
                       .orElse(response);
    }

    @Override
    public Picture loadPictureFromDB(PictureLoadCommand command) throws Exception {
        switch (command.getAction()) {
            case LOAD_CAR_PICTURE: {
                if (command.getEntityId() != null) {
                    return this.sqlPictureRepository.findOne(command.getEntityId());
                }
            }
            case LOAD_CAR_PREVIEW: {
                if (command.getEntityId() != null) {
                    return this.sqlPictureDAO.getCarPreview(command.getEntityId())
                                             .orElse(null);
                }
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getEntityId() != null) {
                    return this.sqlPictureDAO.getManufacturerLogo(command.getEntityId());
                }
            }
            default: {
                if (command.getEntityId() != null) {
                    return this.sqlPictureRepository.findOne(command.getEntityId());
                }
            }
        }

        return this.sqlPictureRepository.findOne(command.getEntityId());
    }

    @Override
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
                    return this.pictureDAO.getCarPreview(carId)
                                          .orElseGet(() -> this.sqlPictureDAO.getCarPreview(pictureId)
                                                                                     .orElse(null));
                }
                break;
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getEntityId() != null) {
                    return this.sqlPictureDAO.getManufacturerLogo(command.getEntityId());
                }
                break;
            }
            default: {
                if (pictureId != null) {
                    return this.loadById(pictureId);
                }
            }
        }

        return this.sqlPictureRepository.findOne(pictureId);
    }

    private Picture loadById(Long pictureId) {
        return Optional.ofNullable(this.pictureDAO.getById(pictureId))
                       .orElse(this.sqlPictureRepository.findOne(pictureId));
    }
}
