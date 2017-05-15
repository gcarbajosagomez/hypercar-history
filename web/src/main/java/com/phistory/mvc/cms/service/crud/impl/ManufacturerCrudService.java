package com.phistory.mvc.cms.service.crud.impl;

import com.phistory.data.model.Manufacturer;
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

import static com.phistory.data.dao.sql.SqlManufacturerRepository.MANUFACTURER_REPOSITORY;
import static com.phistory.mvc.cms.controller.CMSBaseController.ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY;
import static com.phistory.mvc.cms.service.crud.impl.ManufacturerCrudService.MANUFACTURER_CRUD_SERVICE;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
@Component(MANUFACTURER_CRUD_SERVICE)
@Slf4j
public class ManufacturerCrudService extends BaseCrudService {

    public static final String MANUFACTURER_CRUD_SERVICE = "manufacturerCrudService";

    private CrudRepository    sqlManufacturerRepository;
    private EntityFormFactory manufacturerFormFactory;
    private MessageSource     messageSource;

    @Inject
    public ManufacturerCrudService(@Named(MANUFACTURER_REPOSITORY) CrudRepository sqlManufacturerRepository,
                                   EntityFormFactory manufacturerFormFactory,
                                   MessageSource messageSource) {
        super(messageSource);
        this.sqlManufacturerRepository = sqlManufacturerRepository;
        this.manufacturerFormFactory = manufacturerFormFactory;
        this.messageSource = messageSource;
    }

    @Override
    public CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand, BindingResult result) {
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        EditForm editForm = editFormCommand.getEditForm();
        Manufacturer manufacturer =
                (Manufacturer) this.manufacturerFormFactory.buildEntityFromForm(editForm);

        if (!result.hasErrors()) {
            log.info("Saving or editing manufacturer: {}", manufacturer.toString());
            manufacturer = (Manufacturer) this.sqlManufacturerRepository.save(manufacturer);

            String successMessage = super.getSaveOrEditSuccessMessage(manufacturer);
            crudOperationDTO.setEntity(manufacturer);
            crudOperationDTO.setSuccessMessage(successMessage);

            if (editForm.getId() == null) {
                //After the manufacturer has been saved, we need to recreate the ManufacturerEditForm with the newly assigned id
                editFormCommand.setEditForm(this.manufacturerFormFactory.buildFormFromEntity(manufacturer));
            }
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }

    @Override
    public CrudOperationDTO deleteEntity(EditFormCommand editFormCommand, BindingResult result) {

        Manufacturer manufacturer =
                (Manufacturer) this.manufacturerFormFactory.buildEntityFromForm(editFormCommand.getEditForm());
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        if (!result.hasErrors()) {
            try {
                log.info("Deleting manufacturer: {}", manufacturer.toString());
                this.sqlManufacturerRepository.delete(manufacturer);
                crudOperationDTO.setEntity(manufacturer);

                String successMessage = this.messageSource.getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                                      new Object[] {manufacturer.toString()},
                                                                      LocaleContextHolder.getLocale());

                crudOperationDTO.setSuccessMessage(successMessage);
            } catch (Exception e) {
                log.error("There was a problem while deleting manufacturer {}",
                          manufacturer.toString(),
                          e);
                crudOperationDTO.addErrorMessage(e.toString());
            }
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }
}
