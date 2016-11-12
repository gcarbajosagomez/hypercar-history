package com.phistory.mvc.controller.cms.util;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.Picture;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.controller.cms.CMSPictureController;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * Set of utilities for {@link CMSPictureController}
 *
 * Created by gonzalo on 11/5/16.
 */
@Component
public class CMSPictureControllerUtil {

    @Inject
    private SQLPictureDAO SQLPictureDAO;

    /**
     * Handle the save or edition of a {@link Picture}
     *
     * @param pictureEditCommand
     * @throws Exception
     */
    public void saveOrEditPicture(PictureEditCommand pictureEditCommand) throws Exception {
        MultipartFile pictureFile = pictureEditCommand.getPictureFile();
        Picture picture = pictureEditCommand.getPicture();

        if (pictureFile != null && pictureFile.getSize() > 0) {
            PictureDataCommand pictureDataCommand = new PictureDataCommand(pictureFile, null);
            pictureDataCommand.setPicture(picture);
            this.SQLPictureDAO.saveOrEdit(pictureDataCommand);
            pictureEditCommand.setPicture(this.SQLPictureDAO.getCarPreview(picture.getCar().getId()));
        }
    }
}
