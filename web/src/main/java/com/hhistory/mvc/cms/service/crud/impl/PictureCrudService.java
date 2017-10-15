package com.hhistory.mvc.cms.service.crud.impl;

import com.hhistory.data.command.PictureDataCommand;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.cms.command.PictureEditCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.command.PictureLoadCommand;
import com.hhistory.mvc.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.inject.Named;

import static com.hhistory.data.dao.sql.SqlPictureRepository.PICTURE_REPOSITORY;
import static com.hhistory.mvc.cms.controller.CMSBaseController.ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY;

/**
 * Created by gonzalo on 11/5/16.
 */
@Slf4j
@Component
public class PictureCrudService {

    private CrudRepository          sqlPictureRepository;
    private SqlPictureDAO           sqlPictureDAO;
    private PictureService          pictureService;
    private MessageSource           messageSource;

    @Inject
    public PictureCrudService(@Named(PICTURE_REPOSITORY) CrudRepository sqlPictureRepository,
                              SqlPictureDAO sqlPictureDAO,
                              PictureService pictureService,
                              MessageSource messageSource) {
        this.sqlPictureRepository = sqlPictureRepository;
        this.sqlPictureDAO = sqlPictureDAO;
        this.pictureService = pictureService;
        this.messageSource = messageSource;
    }

    /**
     * Handle the saving or edition of a {@link Picture}
     *
     * @param pictureEditCommand
     * @throws Exception
     */
    public void saveOrUpdatePicture(PictureEditCommand pictureEditCommand) throws Exception {
        MultipartFile pictureFile = pictureEditCommand.getPictureFile();
        Picture picture = pictureEditCommand.getPicture();

        if (pictureFile != null && !pictureFile.isEmpty()) {
            this.saveNewPicture(pictureEditCommand);
        } else if (pictureFile == null || (pictureFile != null && pictureFile.getSize() == 0)) {
            this.updatePictureGalleryPosition(picture);
        }
    }

    /**
     * Save a new {@link Picture}
     *
     * @param pictureEditCommand
     * @throws Exception
     */
    private void saveNewPicture(PictureEditCommand pictureEditCommand) throws Exception {
        MultipartFile pictureFile = pictureEditCommand.getPictureFile();
        Picture picture = pictureEditCommand.getPicture();
        PictureDataCommand pictureDataCommand = new PictureDataCommand(pictureFile, picture);
        this.sqlPictureDAO.saveOrEdit(pictureDataCommand);
    }

    /**
     * Update the supplied {@link Picture#galleryPosition}
     *
     * @param picture
     * @throws Exception
     */
    private void updatePictureGalleryPosition(Picture picture) throws Exception {
        PictureDataCommand pictureDataCommand = new PictureDataCommand(null, picture);
        this.sqlPictureDAO.updateGalleryPosition(pictureDataCommand.getPicture());
    }

    public CrudOperationDTO deletePicture(PictureLoadCommand command) {
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        try {
            Picture picture = this.pictureService.loadPicture(command);
            this.sqlPictureRepository.delete(picture);
            crudOperationDTO.setEntity(picture);

            String successMessage = this.messageSource
                    .getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                new Object[] {picture.toString()},
                                LocaleContextHolder.getLocale());

            crudOperationDTO.setSuccessMessage(successMessage);
        } catch (Exception e) {
            log.error("There was an error while deleting picture {} ",
                      command.getEntityId(),
                      e);
            crudOperationDTO.addErrorMessage(e.toString());
        } finally {
            return crudOperationDTO;
        }
    }
}
