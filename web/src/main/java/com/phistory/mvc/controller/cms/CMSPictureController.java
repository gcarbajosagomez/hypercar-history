package com.phistory.mvc.controller.cms;

import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.util.PictureControllerUtil;
import com.phistory.data.model.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.phistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;
import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * @author Gonzalo
 */
@Slf4j
@Secured(USER_ROLE)
@Controller
@RequestMapping(value = CMS_CONTEXT + PICTURES_URL + "/{" + ID + "}")
public class CMSPictureController extends CMSBaseController {

    @Inject
    private PictureControllerUtil pictureControllerUtil;

    @RequestMapping(value = DELETE_URL,
                    method = DELETE)
    @ResponseBody
    public String handleDeletePicture(@ModelAttribute(value = PICTURE_EDIT_FORM_COMMAND) PictureLoadCommand command) {
        try {
            command.setAction(LOAD_CAR_PICTURE);
            Picture picture = this.pictureControllerUtil.loadPictureFromDB(command);
            super.getSQLPictureDAO().delete(picture);

            String successMessage = super.getMessageSource()
                                         .getMessage(ENTITY_DELETED_SUCCESSFULLY_RESULT_MESSAGE,
                                                     new Object[]{"Picture"},
                                                     LocaleContextHolder.getLocale());

            return SUCCESS_MESSAGE + " : " + successMessage;
        } catch (Exception e) {
            log.error("There was an error while deleting picture; {} ", command.getEntityId(), e);
            return EXCEPTION_MESSAGE + " : " + e.toString();
        }
    }

    @ModelAttribute(value = PICTURE_EDIT_FORM_COMMAND)
    public PictureLoadCommand createCommand(@PathVariable(ID) Long entityId) {
        return new PictureLoadCommand(null, entityId);
    }
}
