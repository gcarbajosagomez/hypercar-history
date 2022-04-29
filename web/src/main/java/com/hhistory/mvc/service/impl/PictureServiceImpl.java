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

    private SqlPictureRepository sqlPictureRepository;
    private PictureDAO           inMemoryPictureDAO;
    private SqlPictureDAO        sqlPictureDAO;

    @Inject
    public PictureServiceImpl(SqlPictureRepository sqlPictureRepository,
                              @Named(SQL_PICTURE_DAO) PictureDAO inMemoryPictureDAO,
                              SqlPictureDAO sqlPictureDAO) {
        this.sqlPictureRepository = sqlPictureRepository;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.sqlPictureDAO = sqlPictureDAO;
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
    public Picture loadPicture(PictureLoadCommand command) {
        Optional<Long> pictureId = Optional.ofNullable(command.getEntityId());
        Optional<Long> carId = Optional.ofNullable(command.getEntityId());

        switch (command.getAction()) {
            case LOAD_CAR_PREVIEW -> {
                return carId.map(this.inMemoryPictureDAO::getCarPreview)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .orElse(null);
            }
            case LOAD_MANUFACTURER_LOGO -> {
                Optional<Long> entityId = Optional.ofNullable(command.getEntityId());
                return entityId.map(this.sqlPictureDAO::getManufacturerLogo)
                               .orElse(null);
            }
            default -> {
                return pictureId.map(this::loadById)
                                .orElse(null);
            }
        }
    }

    private Picture loadById(Long pictureId) {
        return Optional.ofNullable(this.inMemoryPictureDAO.getById(pictureId))
                       .orElse(this.sqlPictureRepository.findOne(pictureId));
    }
}
