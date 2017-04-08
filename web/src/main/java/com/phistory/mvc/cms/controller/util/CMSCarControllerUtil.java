package com.phistory.mvc.cms.controller.util;

import com.phistory.data.dao.sql.SqlCarInternetContentRepository;
import com.phistory.data.dao.sql.SqlCarRepository;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.controller.CMSCarController;
import com.phistory.mvc.cms.controller.CMSCarEditController;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.controller.util.DateProvider;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.*;

/**
 * Set of utilities for {@link CMSCarController} and {@link CMSCarEditController}
 *
 * @author gonzalo
 */
@Component
@NoArgsConstructor
@Transactional
public class CMSCarControllerUtil {

    private SqlCarRepository                sqlCarRepository;
    private SqlCarInternetContentRepository sqlCarInternetContentRepository;
    private EntityFormFactory               carFormFactory;
    private EntityFormFactory               carInternetContentFormFactory;
    private CMSPictureControllerUtil        cmsPictureControllerUtil;
    private DateProvider                    dateProvider;
    private EntityManagementService         entityManagementService;

    @Inject
    public CMSCarControllerUtil(SqlCarRepository sqlCarRepository,
                                SqlCarInternetContentRepository sqlCarInternetContentRepository,
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
    public Car saveOrEditCar(CarFormEditCommand command) throws Exception {
        CarForm carForm = command.getCarForm();
        if (carForm != null) {
            List<PictureEditCommand> pictureFileEditCommands = carForm.getPictureFileEditCommands();
            Car car = (Car) this.carFormFactory.createEntityFromForm(carForm);
            this.sqlCarRepository.save(car);

            if (pictureFileEditCommands != null) {
                for (int i = 0; i < pictureFileEditCommands.size(); i++) {
                    PictureEditCommand pictureEditCommand = pictureFileEditCommands.get(i);
                    Picture picture = pictureEditCommand.getPicture();
                    if (picture != null) {
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

                pictureFileEditCommands =
                        pictureFileEditCommands.stream()
                                               .filter(pictureEditCommand -> Objects.nonNull(pictureEditCommand.getPicture()))
                                               .sorted(Comparator.comparing(pictureEditCommand -> pictureEditCommand.getPicture()
                                                                                                                    .getGalleryPosition()))
                                               .collect(Collectors.toList());

                carForm.setPictureFileEditCommands(pictureFileEditCommands);
            }

            if (carForm.getId() == null) {
                //After a new car has been saved, we need to recreate the carForm with all the newly assigned ids
                command.setCarForm((CarForm) this.carFormFactory.createFormFromEntity(car));
            }

            return car;
        }

        return null;
    }

    /**
     * Persist the supplied {@link CarInternetContentEditCommand} and update itself with the persisted results
     *
     * @param carInternetContentEditCommand
     * @throws Exception
     */
    public void saveCarInternetEditCommand(CarInternetContentEditCommand carInternetContentEditCommand) throws Exception {
        List<CarInternetContent> persistedCarInternetContents = this.saveOrEditCarInternetContents(carInternetContentEditCommand);
        List<CarInternetContentForm> updatedCarInternetContentForms =
                persistedCarInternetContents.stream()
                                            .map(carInternetContent -> (CarInternetContentForm)
                                                    this.carInternetContentFormFactory.createFormFromEntity(carInternetContent))
                                            .collect(Collectors.toList());
        carInternetContentEditCommand.setCarInternetContentForms(updatedCarInternetContentForms);
    }

    /**
     * Handle the save or edition of the {@link CarInternetContent}s contained in the supplied {@link CarInternetContentEditCommand#carInternetContentForms}
     *
     * @param carInternetContentEditCommand
     * @return the newly saved edited {@link List<CarInternetContent>} if everything went well, an empty {@link List} otherwise
     * @throws Exception
     */
    public List<CarInternetContent> saveOrEditCarInternetContents(CarInternetContentEditCommand carInternetContentEditCommand)
            throws Exception {
        List<CarInternetContent> savedCarInternetContents = new ArrayList<>();

        for (CarInternetContentForm carInternetContentForm : carInternetContentEditCommand.getCarInternetContentForms()) {
            try {
                CarInternetContent carInternetContent = (CarInternetContent)
                        this.carInternetContentFormFactory.createEntityFromForm(carInternetContentForm);
                carInternetContent.setAddedDate(this.dateProvider.getCurrentTime());

                if (StringUtils.hasText(carInternetContent.getLink())) {
                    this.sqlCarInternetContentRepository.save(carInternetContent);
                    savedCarInternetContents.add(carInternetContent);
                }
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
    public void deleteCar(CarFormEditCommand command) throws Exception {
        if (command.getCarForm() != null) {
            Car car = (Car) carFormFactory.createEntityFromForm(command.getCarForm());
            this.sqlCarRepository.delete(car);
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
