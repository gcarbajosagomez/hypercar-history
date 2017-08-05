package com.hhistory.mvc.cms.service.crud.impl;

import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.mvc.cms.command.CarInternetContentEditFormCommandAdapter;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.form.CarInternetContentForm;
import com.hhistory.mvc.cms.form.CarInternetContentFormAdapter;
import com.hhistory.mvc.cms.form.EditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import com.hhistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.hhistory.mvc.controller.util.DateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hhistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;
import static com.hhistory.mvc.cms.controller.CMSBaseController.ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY;
import static com.hhistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
@Component(CAR_INTERNET_CONTENT_CRUD_SERVICE)
@Slf4j
public class CarInternetContentCrudService extends BaseCrudService {

    public static final String CAR_INTERNET_CONTENT_CRUD_SERVICE = "carInternetContentCrudService";

    private EntityFormFactory carInternetContentFormFactory;
    private CrudRepository    sqlCarInternetContentRepository;
    private MessageSource     messageSource;
    private DateProvider      dateProvider;

    @Inject
    public CarInternetContentCrudService(@Named(CAR_INTERNET_CONTENT_REPOSITORY) CrudRepository sqlCarInternetContentRepository,
                                         CarInternetContentFormFactory carInternetContentFormFactory,
                                         MessageSource messageSource,
                                         DateProvider dateProvider) {
        super(messageSource);
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.sqlCarInternetContentRepository = sqlCarInternetContentRepository;
        this.messageSource = messageSource;
        this.dateProvider = dateProvider;
    }

    @Override
    public CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand, BindingResult result) {
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        if (!result.hasErrors()) {
            List<CarInternetContent> savedCarInternetContents = new ArrayList<>();

            List<CarInternetContentForm> editForms =
                    ((CarInternetContentEditFormCommandAdapter) editFormCommand)
                    .getEditForms()
                    .stream()
                    .map(carInternetContentForm -> {
                        try {
                            CarInternetContent carInternetContent = (CarInternetContent)
                                    this.carInternetContentFormFactory.buildEntityFromForm(carInternetContentForm.adapt());
                            if (Objects.isNull(carInternetContent.getAddedDate())) {
                                carInternetContent.setAddedDate(this.dateProvider.getCurrentTime());
                            }

                            log.info("Saving or editing carInternetContent: {}", carInternetContent.toString());
                            carInternetContent =
                                    (CarInternetContent) this.sqlCarInternetContentRepository.save(carInternetContent);

                            savedCarInternetContents.add(carInternetContent);

                            return ((CarInternetContentFormAdapter) this.carInternetContentFormFactory
                                    .buildFormFromEntity(carInternetContent))
                                    .adapt();
                        } catch (Exception e) {
                            log.error("There was an error while editing carInternetContents",
                                      e);
                            crudOperationDTO.addErrorMessage(e.toString());
                        }
                        return carInternetContentForm;
                    })
                    .collect(Collectors.toList());

            ((CarInternetContentEditFormCommandAdapter) editFormCommand).setEditForms(editForms);
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }
        return crudOperationDTO;
    }

    @Override
    public CrudOperationDTO deleteEntity(EditFormCommand editFormCommand, BindingResult result) {

        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        EditForm editForm = editFormCommand.getEditForm();

        if (Objects.nonNull(editForm)) {
            CarInternetContent carInternetContent =
                    (CarInternetContent) this.carInternetContentFormFactory.buildEntityFromForm(editForm);

            try {
                log.info("Deleting carInternetContent: {}", carInternetContent.toString());
                this.sqlCarInternetContentRepository.delete(carInternetContent);
                crudOperationDTO.setEntity(carInternetContent);
            } catch (Exception e) {
                log.error("There was an error while deleting carInternetContent {} ",
                          carInternetContent.toString(),
                          e);
                crudOperationDTO.addErrorMessage(e.toString());
            }

            String successMessage = this.messageSource.getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                                  new Object[] {carInternetContent.toString()},
                                                                  LocaleContextHolder.getLocale());

            crudOperationDTO.setSuccessMessage(successMessage);
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }
}
