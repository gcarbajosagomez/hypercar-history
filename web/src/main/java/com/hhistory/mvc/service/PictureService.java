package com.hhistory.mvc.service;

import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.command.PictureLoadCommand;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gonzalo Carbajosa on 11/05/17.
 */
public interface PictureService {

    /**
     * Print the binary information of a {@link Picture} to a HTTP response
     *
     * @param picture
     * @param response
     * @return
     */
    HttpServletResponse printPictureToResponse(Picture picture, HttpServletResponse response);

    /**
     * Load a {@link Picture} from the in-memory storage depending on the action being performed
     *
     * @param command
     * @return
     */
    Picture loadPicture(PictureLoadCommand command);
}
