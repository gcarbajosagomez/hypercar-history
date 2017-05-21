package com.phistory.mvc.controller;

import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.command.PictureLoadAction;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.service.PictureService;
import com.phistory.mvc.service.impl.PictureServiceImpl;
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

    private PictureService    pictureService;
    private URILoggingService uriLoggingService;

    @Inject
    public PictureController(PictureService pictureService,
                             URILoggingService uriLoggingService) {
        this.pictureService = pictureService;
        this.uriLoggingService = uriLoggingService;
    }

    @GetMapping
    public void handleDefault(HttpServletResponse response,
                              HttpServletRequest request,
                              @ModelAttribute(PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command) {

        this.uriLoggingService.logURI(request);
        Picture picture = this.pictureService.loadPicture(command);
        this.pictureService.printPictureToResponse(picture, response);

    }

    @ModelAttribute(PICTURE_LOAD_COMMAND_ACTION)
    public PictureLoadCommand createCommand(@PathVariable(PICTURE_LOAD_ACTION) PictureLoadAction loadAction,
                                            @RequestParam(value = ID, required = false) Long entityId) {

        return new PictureLoadCommand(loadAction, entityId);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(PictureLoadAction.class, new PictureLoadActionPropertyEditor());
    }
}
