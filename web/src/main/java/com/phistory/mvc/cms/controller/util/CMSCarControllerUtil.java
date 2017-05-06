package com.phistory.mvc.cms.controller.util;

import com.phistory.data.dao.sql.SqlCarRepository;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.controller.CMSCarController;
import com.phistory.mvc.cms.controller.CMSCarEditController;
import com.phistory.mvc.cms.form.CarEditForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.controller.util.DateProvider;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

import static com.phistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;
import static com.phistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;
import static com.phistory.mvc.cms.command.EntityManagementQueryType.*;

/**
 * Set of utilities for {@link CMSCarController} and {@link CMSCarEditController}
 *
 * @author gonzalo
 */
@Component
@NoArgsConstructor
@Transactional
@Slf4j
public class CMSCarControllerUtil {

    private SqlCarRepository         sqlCarRepository;
    private CrudRepository           sqlCarInternetContentRepository;
    private EntityFormFactory        carFormFactory;
    private EntityFormFactory        carInternetContentFormFactory;
    private CMSPictureControllerUtil cmsPictureControllerUtil;
    private DateProvider             dateProvider;
    private EntityManagementService  entityManagementService;

    @Inject
    public CMSCarControllerUtil(SqlCarRepository sqlCarRepository,
                                @Named(CAR_INTERNET_CONTENT_REPOSITORY) CrudRepository sqlCarInternetContentRepository,
                                EntityFormFactory carFormFactory,
                                EntityFormFactory carInternetContentFormFactory,
                                CMSPictureControllerUtil cmsPictureControllerUtil,
                                DateProvider dateProvider,
                                EntityManagementService entityManagementService) {
        this.sqlCarRepository = sqlCarRepository;
        this.sqlCarInternetContentRepository = sqlCarInternetContentRepository;
        this.carFormFactory = carFormFactory;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.cmsPictureControllerUtil = cmsPictureControllerUtil;
        this.dateProvider = dateProvider;
        this.entityManagementService = entityManagementService;
    }

    /**
     * Handle the save or edition of a Car
     *
     * @param command
     * @return the newly saved edited car if everything went well, null otherwise
     * @throws Exception
     */
    @Transactional
    public Optional<Car> saveOrEditCar(EditFormCommand command) throws Exception {
        CarEditForm carEditForm = (CarEditForm) command.getEditForm();

        if (Objects.nonNull(carEditForm)) {
            Car car = (Car) this.carFormFactory.buildEntityFromForm(carEditForm);
            log.info("Saving or editing car: {}", car.toString());
            car = (Car) this.sqlCarRepository.save(car);

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
                            this.cmsPictureControllerUtil.saveOrUpdatePicture(pictureEditCommand);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }

                pictureFileEditCommands = this.orderPictureCommandsByGalleryPosition(pictureFileEditCommands);
                carEditForm.setPictureFileEditCommands(pictureFileEditCommands);
            }

            if (carEditForm.getId() == null) {
                //After a new car has been saved, we need to recreate the carEditForm with all the newly assigned ids
                command.setEditForm(this.carFormFactory.buildFormFromEntity(car));
            }

            return Optional.of(car);
        }

        return Optional.empty();
    }

    private List<PictureEditCommand> orderPictureCommandsByGalleryPosition(List<PictureEditCommand> pictureEditCommands) {
        return pictureEditCommands.stream()
                                  .map(PictureEditCommand::getPicture)
                                  .filter(Objects::nonNull)
                                  .sorted(Comparator.comparing(picture -> picture.getGalleryPosition()))
                                  .map(picture -> new PictureEditCommand(picture, null))
                                  .collect(Collectors.toList());
    }

    /**
     * Persist the supplied {@link CarInternetContentEditFormCommand} and update itself with the persisted results
     *
     * @param carInternetContentEditFormCommand
     * @throws Exception
     */
    public CarInternetContentEditFormCommand saveCarInternetEditCommand(CarInternetContentEditFormCommand carInternetContentEditFormCommand) throws Exception {
        List<CarInternetContent> persistedCarInternetContents =
                this.saveOrEditCarInternetContents(carInternetContentEditFormCommand);
        List<CarInternetContentForm> updatedCarInternetContentForms =
                persistedCarInternetContents.stream()
                                            .map(carInternetContent -> (CarInternetContentForm)
                                                    this.carInternetContentFormFactory.buildFormFromEntity(carInternetContent))
                                            .collect(Collectors.toList());
        carInternetContentEditFormCommand.setEditForms(updatedCarInternetContentForms);
        return carInternetContentEditFormCommand;
    }

    /**
     * Handle the save or edition of the {@link CarInternetContent}s contained in the supplied {@link CarInternetContentEditFormCommand#editForms}
     *
     * @param carInternetContentEditFormCommand
     * @return the newly saved edited {@link List<CarInternetContent>} if everything went well, an empty {@link List} otherwise
     * @throws Exception
     */
    public List<CarInternetContent> saveOrEditCarInternetContents(
            CarInternetContentEditFormCommand carInternetContentEditFormCommand)
            throws Exception {
        List<CarInternetContent> savedCarInternetContents = new ArrayList<>();

        for (EditForm carInternetContentForm : carInternetContentEditFormCommand.getEditForms()) {
            try {
                CarInternetContent carInternetContent =
                        (CarInternetContent) this.carInternetContentFormFactory.buildEntityFromForm(carInternetContentForm);
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

        return savedCarInternetContents;
    }

    /**
     * Handle the deletion of a car
     *
     * @param command
     * @throws Exception
     */
    public void deleteCar(EditFormCommand command) throws Exception {
        EditForm form = command.getEditForm();
        if (Objects.nonNull(form)) {
            Car car = (Car) this.carFormFactory.buildEntityFromForm(form);
            log.info("Deleting car: {}", car.toString());
            this.sqlCarRepository.delete(car.getId());
        }
    }

    public void reloadCarAndPictureDBEntities(Long carId) {
        EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
        entityManagementLoadCommand.setCarId(carId);

        entityManagementLoadCommand.setQueryType(RELOAD_CARS);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);

        entityManagementLoadCommand.setQueryType(RELOAD_PICTURES);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);

        entityManagementLoadCommand.setQueryType(RELOAD_CAR_INTERNET_CONTENTS);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
    }
}
