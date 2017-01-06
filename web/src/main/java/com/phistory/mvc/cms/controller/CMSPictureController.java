package com.phistory.mvc.cms.controller;

import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.util.PictureControllerUtil;
import com.phistory.mvc.cms.service.EntityManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_PICTURE;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;
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

    private PictureControllerUtil pictureControllerUtil;
    private EntityManagementService entityManagementService;

    @Inject
    public CMSPictureController(PictureControllerUtil pictureControllerUtil,
                                EntityManagementService entityManagementService) {
        this.pictureControllerUtil = pictureControllerUtil;
        this.entityManagementService = entityManagementService;
    }

    @RequestMapping(value = DELETE_URL,
                    method = DELETE)
    @ResponseBody
    public String handleDeletePicture(@ModelAttribute(value = PICTURE_EDIT_FORM_COMMAND) PictureLoadCommand command) {
        try {
            command.setAction(LOAD_CAR_PICTURE);
            Picture picture = this.pictureControllerUtil.loadPictureFromDB(command);
            super.getSqlPictureDAO().delete(picture);
            this.reloadInMemoryPictures(picture.getId());

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

    private void reloadInMemoryPictures(Long pictureId) {
        EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand(REMOVE_PICTURE,
                                                                                                  null,
                                                                                                  pictureId,
                                                                                                  null,
                                                                                                  null);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
    }

    @ModelAttribute(value = PICTURE_EDIT_FORM_COMMAND)
    public PictureLoadCommand createCommand(@PathVariable(ID) Long entityId) {
        return new PictureLoadCommand(null, entityId);
    }
}
