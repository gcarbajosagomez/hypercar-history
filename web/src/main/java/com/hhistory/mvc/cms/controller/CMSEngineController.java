package com.hhistory.mvc.cms.controller;

import com.hhistory.mvc.cms.command.EngineEditFormCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.service.crud.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.cms.service.crud.impl.EngineCrudService.ENGINE_CRUD_SERVICE;
import static com.hhistory.mvc.controller.BaseControllerData.ENGINE_URL;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Gonzalo Carbajosa on 14/04/17.
 */
@Secured(USER_ROLE)
@RestController
@RequestMapping(CMS_CONTEXT + ENGINE_URL)
@Slf4j
public class CMSEngineController extends CMSBaseController {

    private CrudService engineCrudService;

    @Inject
    public CMSEngineController(@Named(ENGINE_CRUD_SERVICE) CrudService engineCrudService) {
        this.engineCrudService = engineCrudService;
    }

    @PostMapping(value = SAVE_URL,
            produces = APPLICATION_JSON_VALUE)
    public CrudOperationDTO handleSaveNewEngine(@Valid @RequestBody EngineEditFormCommand engineEditFormCommand,
                                                BindingResult result) {

        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        try {
            crudOperationDTO = this.engineCrudService.saveOrEditEntity(engineEditFormCommand,
                                                                       result);
        } catch (Exception e) {
            log.error("There was an error while saving new engine", e);
        } finally {
            return crudOperationDTO;
        }
    }
}
