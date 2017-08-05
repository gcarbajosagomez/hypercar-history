package com.hhistory.mvc.cms.service.crud.impl;

import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.command.PictureEditCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.form.CarEditForm;
import com.hhistory.mvc.cms.form.EditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hhistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;
import static com.hhistory.mvc.cms.controller.CMSBaseController.*;
import static com.hhistory.mvc.cms.service.crud.impl.CarCrudService.CAR_CRUD_SERVICE;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
@Component(CAR_CRUD_SERVICE)
@Slf4j
public class CarCrudService extends BaseCrudService {

    public static final String CAR_CRUD_SERVICE = "carCrudService";

    private CrudRepository     sqlCarRepository;
    private EntityFormFactory  carFormFactory;
    private PictureCrudService pictureCrudService;
    private MessageSource      messageSource;

    @Inject
    public CarCrudService(@Named(CAR_REPOSITORY) CrudRepository sqlCarRepository,
                          EntityFormFactory carFormFactory,
                          PictureCrudService pictureCrudService,
                          MessageSource messageSource) {
        super(messageSource);
        this.sqlCarRepository = sqlCarRepository;
        this.carFormFactory = carFormFactory;
        this.pictureCrudService = pictureCrudService;
        this.messageSource = messageSource;
    }

    @Override
    public CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand,
                                             BindingResult result) {

        CarEditForm carEditForm = (CarEditForm) editFormCommand.getEditForm();
        Car car = (Car) this.carFormFactory.buildEntityFromForm(carEditForm);
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();

        if (!result.hasErrors()) {
            log.info("Saving or editing car: {}", car.toString());
            try {
                car = (Car) this.sqlCarRepository.save(car);

                String successMessage = super.getSaveOrEditSuccessMessage(car);
                crudOperationDTO.setEntity(car);
                crudOperationDTO.setSuccessMessage(successMessage);
            } catch (Exception e) {
                log.error("There was a problem while saving new car {}",
                          car.toString(),
                          e);
                crudOperationDTO.addErrorMessage(e.toString());
            }

            List<PictureEditCommand> pictureFileEditCommands = carEditForm.getPictureFileEditCommands();
            if (Objects.nonNull(pictureFileEditCommands)) {

                for (int i = 0; i < pictureFileEditCommands.size(); i++) {
                    PictureEditCommand pictureEditCommand = pictureFileEditCommands.get(i);
                    Picture picture = pictureEditCommand.getPicture();

                    if (Objects.nonNull(picture)) {
                        picture.setCar(car);

                        if (picture.getGalleryPosition() == null) {
                            picture.setGalleryPosition(i);
                        }

                        try {
                            this.pictureCrudService.saveOrUpdatePicture(pictureEditCommand);
                        } catch (Exception e) {
                            crudOperationDTO.addErrorMessage(e.toString());
                        }
                    }
                }

                pictureFileEditCommands = this.orderPictureCommandsByGalleryPosition(pictureFileEditCommands);
                carEditForm.setPictureFileEditCommands(pictureFileEditCommands);
            }

            if (carEditForm.getId() == null) {
                //After a new car has been saved, we need to recreate the carEditForm with all the newly assigned ids
                editFormCommand.setEditForm(this.carFormFactory.buildFormFromEntity(car));
            }
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }

    private List<PictureEditCommand> orderPictureCommandsByGalleryPosition(List<PictureEditCommand> pictureEditCommands) {
        return pictureEditCommands.stream()
                                  .map(PictureEditCommand::getPicture)
                                  .filter(Objects::nonNull)
                                  .sorted(Comparator.comparing(picture -> picture.getGalleryPosition()))
                                  .map(picture -> new PictureEditCommand(picture, null))
                                  .collect(Collectors.toList());
    }

    @Override
    public CrudOperationDTO deleteEntity(EditFormCommand editFormCommand, BindingResult result) {
        EditForm form = editFormCommand.getEditForm();
        Car car = (Car) this.carFormFactory.buildEntityFromForm(form);
        CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
        crudOperationDTO.setEntity(car);

        if (!result.hasErrors()) {
            try {
                log.info("Deleting car: {}", car.toString());
                this.sqlCarRepository.delete(car.getId());

                String successMessage = this.messageSource.getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                                      new Object[] {car.toString()},
                                                                      LocaleContextHolder.getLocale());

                crudOperationDTO.setSuccessMessage(successMessage);
            } catch (Exception e) {
                log.error("There was a problem while deleting car {}",
                          car.toString(),
                          e);
                crudOperationDTO.addErrorMessage(e.toString());
            }
        } else {
            super.addBindingResultErrors(result, crudOperationDTO);
        }

        return crudOperationDTO;
    }
}
