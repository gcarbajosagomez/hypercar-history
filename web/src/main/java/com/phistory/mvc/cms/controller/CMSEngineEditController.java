package com.phistory.mvc.cms.controller;

import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EngineEditFormCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.EngineEditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.EngineFormFactory;
import com.phistory.mvc.cms.service.crud.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import java.util.Objects;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.service.crud.impl.EngineCrudService.ENGINE_CRUD_SERVICE;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Slf4j
@RestController
@RequestMapping(CMS_CONTEXT + ENGINE_URL + "/{" + ID + "}")
public class CMSEngineEditController extends CMSBaseController {

    private static final String ENGINE_EDIT_FORM_COMMAND = "EEFC";

    private EntityFormFactory engineFormFactory;
    private CrudService       engineCrudService;

    @Inject
    public CMSEngineEditController(@Named(ENGINE_CRUD_SERVICE) CrudService engineCrudService,
                                   EngineFormFactory engineFormFactory) {
        this.engineFormFactory = engineFormFactory;
        this.engineCrudService = engineCrudService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public EditForm handleListEngineById(
            @ModelAttribute(ENGINE_EDIT_FORM_COMMAND) EditFormCommand engineEditFormCommand) {
        return engineEditFormCommand.getEditForm();
    }

    @PutMapping(value = EDIT_URL,
            produces = APPLICATION_JSON_VALUE)
    public CrudOperationDTO handleEditEngine(@Valid @RequestBody EngineEditFormCommand engineEditFormCommand,
                                             BindingResult result) {

        return this.engineCrudService.saveOrEditEntity(engineEditFormCommand,
                                                       result);
    }

    @DeleteMapping(value = DELETE_URL)
    public CrudOperationDTO handleDeleteEngine(
            @Valid @ModelAttribute(ENGINE_EDIT_FORM_COMMAND) EditFormCommand engineFormEditCommand,
            BindingResult result) {

        return this.engineCrudService.deleteEntity(engineFormEditCommand, result);
    }

    @ModelAttribute(ENGINE_EDIT_FORM_COMMAND)
    public EditFormCommand createEditFormCommand(@PathVariable(ID) Long engineId) {
        EditFormCommand command = new EngineEditFormCommand();

        if (Objects.nonNull(engineId)) {
            Engine engine = (Engine) super.getSqlEngineRepository().findOne(engineId);
            EngineEditForm engineEditForm = (EngineEditForm) this.engineFormFactory.buildFormFromEntity(engine);
            command.setEditForm(engineEditForm);
        }

        return command;
    }
}
