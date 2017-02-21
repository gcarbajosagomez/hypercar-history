package com.phistory.mvc.controller;

import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.command.PictureLoadAction;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.util.PictureControllerUtil;
import com.phistory.mvc.propertyEditor.PictureLoadActionPropertyEditor;
import com.phistory.mvc.service.URILoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.phistory.mvc.controller.BaseControllerData.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Picture URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = PICTURES_URL + "/{" + PICTURE_LOAD_ACTION + "}",
        method = HEAD)
public class PictureController {
    @Inject
    private PictureControllerUtil pictureControllerUtil;
    @Inject
    private URILoggingService     uriLoggingService;


    @RequestMapping(method = GET)
    public void handleDefault(HttpServletResponse response,
                              HttpServletRequest request,
                              @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command) {
        try {
            this.uriLoggingService.logURI(request);
            Picture picture = this.pictureControllerUtil.loadPicture(command);
            this.pictureControllerUtil.printPictureToResponse(picture, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION)
    public PictureLoadCommand createCommand(@PathVariable(PICTURE_LOAD_ACTION) PictureLoadAction loadAction,
                                            @RequestParam(value = ID, required = false) Long entityId) {

        PictureLoadCommand command = new PictureLoadCommand();
        try {
            command = new PictureLoadCommand(loadAction, entityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return command;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(PictureLoadAction.class, new PictureLoadActionPropertyEditor());
    }
}
