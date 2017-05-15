package com.phistory.mvc.cms.service.crud.impl;

import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;
import javax.inject.Named;

import static com.phistory.data.dao.sql.SqlEngineRepository.ENGINE_REPOSITORY;
import static com.phistory.mvc.cms.controller.CMSBaseController.ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY;
import static com.phistory.mvc.cms.service.crud.impl.EngineCrudService.ENGINE_CRUD_SERVICE;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
@Component(ENGINE_CRUD_SERVICE)
@Slf4j
public class EngineCrudService extends BaseCrudService {

    public static final String ENGINE_CRUD_SERVICE = "engineCrudService";

    private CrudRepository    sqlEngineRepository;
    private EntityFormFactory engineFormFactory;
    private MessageSource     messageSource;

    @Inject
    public EngineCrudService(@Named(ENGINE_REPOSITORY) CrudRepository sqlEngineRepository,
                             EntityFormFactory engineFormFactory,
                             MessageSource messageSource) {
        super(messageSource);
        this.sqlEngineRepository = sqlEngineRepository;
        this.engineFormFactory = engineFormFactory;
        this.messageSource = messageSource;
    }

    @Override
    public CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand,
                                             BindingResult result) {
        Engine engine = (Engine) this.engineFormFactory.buildEntityFromForm(editFormCommand.getEditForm());
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        try {
            if (!result.hasErrors()) {
                log.info("Saving or editing engine: {}", engine.toString());
                engine = (Engine) this.sqlEngineRepository.save(engine);

                String successMessage = super.getSaveOrEditSuccessMessage(engine);
                crudOperationDTO.setEntity(engine);
                crudOperationDTO.setSuccessMessage(successMessage);
            } else {
                super.addBindingResultErrors(result, crudOperationDTO);
            }
        } catch (Exception e) {
            log.error("There was an error while saving or editing engine: {}",
                      engine.toString());

            crudOperationDTO.addErrorMessage(e.toString());
        }

        return crudOperationDTO;
    }

    @Override
    public CrudOperationDTO deleteEntity(EditFormCommand editFormCommand,
                                         BindingResult result) {

        EditForm editForm = editFormCommand.getEditForm();
        Engine engine = (Engine) this.engineFormFactory.buildEntityFromForm(editForm);
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        if (!result.hasErrors()) {
            try {
                log.info("Deleting engine: {}", engine.toString());
                this.sqlEngineRepository.delete(engine);
                crudOperationDTO.setEntity(engine);

                String successMessage = this.messageSource.getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                                      new Object[] {engine.toString()},
                                                                      LocaleContextHolder.getLocale());

                crudOperationDTO.setSuccessMessage(successMessage);
            } catch (Exception e) {
                log.error("There was a problem while deleting engine {}",
                          engine.toString(),
                          e);

                crudOperationDTO.addErrorMessage(e.toString());
            }
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }
}
