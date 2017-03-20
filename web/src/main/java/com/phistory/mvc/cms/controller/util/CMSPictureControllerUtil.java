package com.phistory.mvc.cms.controller.util;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.controller.CMSPictureController;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * Set of utilities for {@link CMSPictureController}
 * <p>
 * Created by gonzalo on 11/5/16.
 */
@Component
public class CMSPictureControllerUtil {

    private SqlPictureDAO sqlPictureDAO;

    @Inject
    public CMSPictureControllerUtil(SqlPictureDAO SqlPictureDAO) {
        this.sqlPictureDAO = SqlPictureDAO;
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
    public void saveNewPicture(PictureEditCommand pictureEditCommand) throws Exception {
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
    public void updatePictureGalleryPosition(Picture picture) throws Exception {
        PictureDataCommand pictureDataCommand = new PictureDataCommand(null, picture);
        this.sqlPictureDAO.updateGalleryPosition(pictureDataCommand.getPicture());
    }
}
