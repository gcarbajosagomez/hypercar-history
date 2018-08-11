package com.hhistory.mvc.controller;

import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.command.PictureLoadAction;
import com.hhistory.mvc.command.PictureLoadCommand;
import com.hhistory.mvc.service.PictureService;
import com.hhistory.mvc.propertyEditor.PictureLoadActionPropertyEditor;
import com.hhistory.mvc.service.URILoggingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.hhistory.mvc.controller.BaseControllerData.*;
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
@AllArgsConstructor(onConstructor = @__(@Inject))
public class PictureController {

    private PictureService    pictureService;
    private URILoggingService uriLoggingService;

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
