package com.phistory.mvc.controller;

import com.phistory.data.model.Picture;
import com.phistory.mvc.command.PictureLoadAction;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.util.PictureControllerUtil;
import com.phistory.mvc.propertyEditor.PictureLoadActionPropertyEditor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;
import static com.phistory.mvc.controller.BaseControllerData.PICTURE_LOAD_ACTION_ACTION;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Picture URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = PICTURES_URL + "/{" + PICTURE_LOAD_ACTION_ACTION + "}",
                method = HEAD)
public class PictureController extends BaseController {
    @Inject
    private PictureControllerUtil pictureControllerUtil;


    @RequestMapping(method = GET)
    public void handleDefault(HttpServletResponse response,
                              @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command) {
        try {
            Picture picture = this.pictureControllerUtil.loadPicture(command);
            this.pictureControllerUtil.printPictureToResponse(picture, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION)
    public PictureLoadCommand createCommand(@PathVariable(PICTURE_LOAD_ACTION_ACTION) PictureLoadAction loadAction,
                                            @RequestParam(value = ID, required = false) Long pictureId) {

        PictureLoadCommand command = new PictureLoadCommand();
        try {
            command = new PictureLoadCommand(loadAction, pictureId);
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
