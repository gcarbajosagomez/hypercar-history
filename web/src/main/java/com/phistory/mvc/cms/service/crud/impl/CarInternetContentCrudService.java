package com.phistory.mvc.cms.service.crud.impl;

import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommandAdapter;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.CarEditForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.phistory.mvc.controller.util.DateProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.phistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;
import static com.phistory.mvc.cms.controller.CMSBaseController.ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY;
import static com.phistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;

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
            try {
                List<CarInternetContent> savedCarInternetContents = new ArrayList<>();

                for (CarInternetContentForm carInternetContentForm :
                        ((CarInternetContentEditFormCommandAdapter) editFormCommand).getEditForms()) {
                    try {
                        CarInternetContent carInternetContent = (CarInternetContent)
                                this.carInternetContentFormFactory.buildEntityFromForm(carInternetContentForm.adapt());
                        if (Objects.isNull(carInternetContent.getAddedDate())) {
                            carInternetContent.setAddedDate(this.dateProvider.getCurrentTime());
                        }

                        log.info("Saving or editing carInternetContent: {}", carInternetContent.toString());
                        carInternetContent = (CarInternetContent) this.sqlCarInternetContentRepository.save(carInternetContent);
                        savedCarInternetContents.add(carInternetContent);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            } catch (Exception e) {
                log.error("There was an error while editing carInternetContents",
                          e);
                crudOperationDTO.addErrorMessage(e.toString());
            }
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
