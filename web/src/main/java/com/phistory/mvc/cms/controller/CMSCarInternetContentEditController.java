package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.cms.service.crud.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Optional;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_CAR_INTERNET_CONTENTS;
import static com.phistory.mvc.cms.controller.CMSBaseController.CAR_INTERNET_CONTENTS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

/**
 * Controller to handle requests to "{@link CMSBaseController#CAR_INTERNET_CONTENTS_URL}" URLs
 * <p>
 * Created by gonzalo on 9/9/16.
 */
@Secured(USER_ROLE)
@Slf4j
@RestController
@RequestMapping(CMS_CONTEXT + CAR_INTERNET_CONTENTS_URL + "/{" + ID + "}")
public class CMSCarInternetContentEditController extends CMSBaseController {

    private EntityManagementService       entityManagementService;
    private CarInternetContentFormFactory carInternetContentFormFactory;
    private CrudService                   carInternetContentCrudService;

    @Inject
    public CMSCarInternetContentEditController(
            @Named(CAR_INTERNET_CONTENT_CRUD_SERVICE) CrudService carInternetContentCrudService,
            EntityManagementService entityManagementService,
            CarInternetContentFormFactory carInternetContentFormFactory) {

        this.entityManagementService = entityManagementService;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.carInternetContentCrudService = carInternetContentCrudService;
    }

    @DeleteMapping(value = DELETE_URL)
    public CrudOperationDTO handleDeleteInternetContent(
            @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand carInternetContentEditCommand,
            BindingResult bindingResult) {

        CrudOperationDTO crudOperationDTO = this.carInternetContentCrudService.deleteEntity(carInternetContentEditCommand.adapt(),
                                                                                            bindingResult);

        CarInternetContent carInternetContent = (CarInternetContent) crudOperationDTO.getEntity();
        Optional.ofNullable(carInternetContent)
                .map(CarInternetContent::getId)
                .ifPresent(carInternetContentId -> {
                    try {
                        EntityManagementLoadCommand entityManagementLoadCommand =
                                EntityManagementLoadCommand.builder()
                                                           .queryType(REMOVE_CAR_INTERNET_CONTENTS)
                                                           .carInternetContentId(carInternetContentId)
                                                           .build();

                        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
                    } catch (Exception e) {
                        log.error("There was an error while deleting carInternetContent {} ",
                                  carInternetContent.toString(),
                                  e);
                    }
                });

        return crudOperationDTO;
    }

    @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentCommand(@PathVariable(ID) Long contentId) throws Exception {
        CarInternetContent carInternetContent = super.getSqlCarInternetContentRepository()
                                                     .findOne(contentId);

        CarInternetContentForm carInternetContentForm =
                this.carInternetContentFormFactory.buildFormFromEntity(carInternetContent)
                                                  .adapt();
        return new CarInternetContentEditFormCommand(Arrays.asList(carInternetContentForm));
    }
}
