package com.phistory.mvc.cms.controller.util;

import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EngineEditFormCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.phistory.data.dao.sql.SqlEngineRepository.ENGINE_REPOSITORY;

/**
 * Set of utilities for the EngineController class
 *
 * @author gonzalo
 */
@Component
@Slf4j
public class CMSEngineControllerUtil {

    private CrudRepository    sqlEngineRepository;
    private EntityFormFactory engineFormFactory;
    private MessageSource     messageSource;

    @Inject
    public CMSEngineControllerUtil(@Named(ENGINE_REPOSITORY) CrudRepository sqlEngineRepository,
                                   EntityFormFactory engineFormFactory,
                                   MessageSource messageSource) {
        this.sqlEngineRepository = sqlEngineRepository;
        this.engineFormFactory = engineFormFactory;
        this.messageSource = messageSource;
    }

    public CrudOperationDTO saveOrEditEngine(EngineEditFormCommand engineEditFormCommand,
                                             BindingResult result,
                                             String successMessageSourceKey) {

        Engine engine = (Engine) this.engineFormFactory.buildEntityFromForm(engineEditFormCommand.getEngineEditForm());
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        crudOperationDTO.setEntity(engine);

        try {
            if (!result.hasErrors()) {
                log.info("Saving or editing engine: {}", engine.toString());
                engine = (Engine) this.sqlEngineRepository.save(engine);

                String successMessage =
                        this.messageSource.getMessage(successMessageSourceKey,
                                                      new Object[] {engine.toString()},
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
            crudOperationDTO.setErrorMessages(Arrays.asList(e.toString()));
        }

        return crudOperationDTO;
    }

    /**
     * Handle the deletion of an engine.
     *
     * @param engineEditFormCommand
     * @throws Exception
     */
    public void deleteEngine(EngineEditFormCommand engineEditFormCommand) throws Exception {
        EditForm form = engineEditFormCommand.getEngineEditForm();
        if (Objects.nonNull(form)) {
            Engine engine = (Engine) this.engineFormFactory.buildEntityFromForm(form);
            log.info("Deleting engine: {}", engine.toString());
            this.sqlEngineRepository.delete(engine);
        }
    }
}
