package com.phistory.mvc.controller.cms.util;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.controller.cms.CMSPictureController;
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

    private SQLPictureDAO SQLPictureDAO;

    @Inject
    public CMSPictureControllerUtil(SQLPictureDAO SQLPictureDAO) {
        this.SQLPictureDAO = SQLPictureDAO;
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

        if (pictureFile != null && pictureFile.getSize() > 0) {
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
        this.SQLPictureDAO.saveOrEdit(pictureDataCommand);
    }

    /**
     * Update the supplied {@link Picture#galleryPosition}
     *
     * @param picture
     * @throws Exception
     */
    public void updatePictureGalleryPosition(Picture picture) throws Exception {
        PictureDataCommand pictureDataCommand = new PictureDataCommand(null, picture);
        this.SQLPictureDAO.updateGalleryPosition(pictureDataCommand.getPicture());
    }
}
