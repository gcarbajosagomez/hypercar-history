package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
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
import java.util.Arrays;
import java.util.Objects;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_CAR;
import static com.phistory.mvc.cms.controller.CMSBaseController.CAR_INTERNET_CONTENTS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * Controller to handle requests to "{@value CAR_INTERNET_CONTENTS_URL}" URLs
 * <p>
 * Created by gonzalo on 9/9/16.
 */
@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = CMS_CONTEXT + CAR_INTERNET_CONTENTS_URL + "/{" + ID + "}")
public class CMSCarInternetContentEditController extends CMSBaseController {

    private EntityManagementService       entityManagementService;
    private CarInternetContentFormFactory carInternetContentFormFactory;

    @Inject
    public CMSCarInternetContentEditController(EntityManagementService entityManagementService,
                                               CarInternetContentFormFactory carInternetContentFormFactory) {
        this.entityManagementService = entityManagementService;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
    }

    @RequestMapping(value = DELETE_URL,
            method = DELETE)
    @ResponseBody
    public String handleDeleteInternetContent(
            @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand carInternetContentEditCommand) {
        CarInternetContent carInternetContent = this.carInternetContentFormFactory.buildEntityFromForm(carInternetContentEditCommand.getEditForm());

        if (Objects.nonNull(carInternetContent)) {
            try {
                log.info("Deleting carInternetContent: {}", carInternetContent.toString());
                super.getSqlCarInternetContentRepository().delete(carInternetContent);

                EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
                entityManagementLoadCommand.setCarInternetContentId(carInternetContent.getCar().getId());
                entityManagementLoadCommand.setQueryType(REMOVE_CAR);
                this.entityManagementService.reloadEntities(entityManagementLoadCommand);

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {"Car internet content"},
                                                         LocaleContextHolder.getLocale());

                return SUCCESS_MESSAGE + " : " + successMessage;
            } catch (Exception e) {
                log.error("There was an error while deleting picture; %s ",
                          carInternetContent.getId(),
                          e);
                return EXCEPTION_MESSAGE + " : " + e.toString();
            }
        }
        return  EXCEPTION_MESSAGE + " : No content found";
    }

    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentCommand(@PathVariable(ID) Long contentId) throws Exception {
        CarInternetContent carInternetContent = super.getSqlCarInternetContentRepository()
                                                     .findOne(contentId);

        CarInternetContentForm carInternetContentForm = this.carInternetContentFormFactory.buildFormFromEntity(carInternetContent);
        return new CarInternetContentEditFormCommand(Arrays.asList(carInternetContentForm));
    }
}
