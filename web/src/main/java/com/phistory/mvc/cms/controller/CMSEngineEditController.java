package com.phistory.mvc.cms.controller;

import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.controller.util.CMSEngineControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.EngineForm;
import com.phistory.mvc.cms.form.factory.impl.EngineFormFactory;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = CMS_CONTEXT + ENGINE_URL + "/{" + ID + "}")
public class CMSEngineEditController extends CMSBaseController {

    private static final String ENGINE_EDIT_FORM_COMMAND = "EEFC";

    private EntityFormFactory       engineFormFactory;
    private CMSEngineControllerUtil cmsEngineControllerUtil;

    @Inject
    public CMSEngineEditController(EngineFormFactory engineFormFactory,
                                   CMSEngineControllerUtil cmsEngineControllerUtil) {
        this.engineFormFactory = engineFormFactory;
        this.cmsEngineControllerUtil = cmsEngineControllerUtil;
    }

    @RequestMapping(method = GET,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public EngineForm handleListEngineById(@ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command) {
        return command.getEngineForm();
    }

    @RequestMapping(value = EDIT_URL,
            method = POST,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CrudOperationDTO handleEditEngine(@Valid @RequestBody EngineFormEditCommand command,
                                             BindingResult result) {
        return this.cmsEngineControllerUtil.saveOrEditEngine(command, result, ENTITY_EDITED_SUCCESSFULLY_TEXT_SOURCE_KEY);
    }

    @RequestMapping(value = DELETE_URL,
            method = DELETE)
    @ResponseBody
    public CrudOperationDTO handleDeleteEngine(
            @Valid @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command,
            BindingResult result) {

        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        try {
            if (!result.hasErrors()) {
                this.cmsEngineControllerUtil.deleteEngine(command);
                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {command.getEngineForm().getCode()},
                                                         LocaleContextHolder.getLocale());

                crudOperationDTO.setSuccessMessage(successMessage);
            } else {
                List<String> errorMessages = result.getAllErrors()
                                                   .stream()
                                                   .map(ObjectError::toString)
                                                   .collect(Collectors.toList());

                crudOperationDTO.setErrorMessages(errorMessages);
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            crudOperationDTO.setErrorMessages(Arrays.asList(e.toString()));
        }

        return crudOperationDTO;
    }

    @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND)
    public EngineFormEditCommand createEditFormCommand(@PathVariable(ID) Long engineId) {
        EngineFormEditCommand command = new EngineFormEditCommand();

        if (engineId != null) {
            Engine engine = (Engine) super.getSqlEngineRepository().findOne(engineId);
            EngineForm engineForm = (EngineForm) this.engineFormFactory.createFormFromEntity(engine);
            command.setEngineForm(engineForm);
        }

        return command;
    }
}
