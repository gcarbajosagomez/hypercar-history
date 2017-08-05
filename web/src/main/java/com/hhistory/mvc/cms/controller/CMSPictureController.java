package com.hhistory.mvc.cms.controller;

import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.service.EntityManagementService;
import com.hhistory.mvc.cms.service.crud.impl.PictureCrudService;
import com.hhistory.mvc.command.PictureLoadCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.hhistory.mvc.cms.command.EntityManagementQueryType.REMOVE_PICTURE;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.hhistory.mvc.controller.BaseControllerData.ID;
import static com.hhistory.mvc.controller.BaseControllerData.PICTURES_URL;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

/**
 * @author Gonzalo
 */
@Slf4j
@Secured(USER_ROLE)
@RestController
@RequestMapping(CMS_CONTEXT + PICTURES_URL + "/{" + ID + "}")
public class CMSPictureController extends CMSBaseController {

    private PictureCrudService      pictureCrudService;
    private EntityManagementService entityManagementService;

    @Inject
    public CMSPictureController(PictureCrudService pictureCrudService,
                                EntityManagementService entityManagementService) {
        this.pictureCrudService = pictureCrudService;
        this.entityManagementService = entityManagementService;
    }

    @DeleteMapping(value = DELETE_URL)
    public CrudOperationDTO handleDeletePicture(@ModelAttribute(PICTURE_LOAD_COMMAND) PictureLoadCommand command) {
        command.setAction(LOAD_CAR_PICTURE);
        CrudOperationDTO crudOperationDTO = this.pictureCrudService.deletePicture(command);
        Picture picture = (Picture) crudOperationDTO.getEntity();

        try {
            EntityManagementLoadCommand entityManagementLoadCommand = EntityManagementLoadCommand.builder()
                                                                                                 .queryType(REMOVE_PICTURE)
                                                                                                 .pictureId(picture.getId())
                                                                                                 .build();
            this.entityManagementService.reloadEntities(entityManagementLoadCommand);
        } catch (Exception e) {
            log.error("There was an error while deleting picture {} ",
                      picture.toString(),
                      e);
            crudOperationDTO.addErrorMessage(e.toString());
        }
        return crudOperationDTO;
    }

    @ModelAttribute(PICTURE_LOAD_COMMAND)
    public PictureLoadCommand createCommand(@PathVariable(ID) Long entityId) {
        PictureLoadCommand pictureLoadCommand = new PictureLoadCommand();
        pictureLoadCommand.setEntityId(entityId);
        return pictureLoadCommand;
    }
}
