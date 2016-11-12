package com.phistory.mvc.controller.cms.util;

import com.phistory.data.dao.sql.impl.SQLCarDAO;
import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.model.Picture;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.form.creator.CarInternetContentFormCreator;
import com.phistory.mvc.controller.cms.CMSCarController;
import com.phistory.mvc.controller.cms.CMSCarEditController;
import com.phistory.mvc.controller.util.DateProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.data.model.Picture.PictureType.PICTURE;
import static com.phistory.data.model.Picture.PictureType.PREVIEW_PICTURE;

/**
 * Set of utilities for {@link CMSCarController} and {@link CMSCarEditController}
 *
 * @author gonzalo
 */
@Component
public class CMSCarControllerUtil {

    @Inject
    private SQLCarDAO carDAO;
    @Inject
    private SQLCarInternetContentDAO carInternetContentDAO;
    @Inject
    private CarFormCreator carFormCreator;
    @Inject
    private CarInternetContentFormCreator carInternetContentFormCreator;
    @Inject
    private CMSPictureControllerUtil cmsPictureControllerUtil;
    @Inject
    private DateProvider dateProvider;

    /**
     * Handle the save or edition of a Car
     *
     * @param command
     * @return the newly saved edited car if everything went well, null otherwise
     * @throws Exception
     */
    public Car saveOrEditCar(CarFormEditCommand command) throws Exception {
        if (command.getCarForm() != null) {
            Car car = this.carFormCreator.createEntityFromForm(command.getCarForm());
            this.carDAO.saveOrEdit(car);

            if (command.getCarForm().getPictureFiles() != null) {
                for (MultipartFile file : command.getCarForm().getPictureFiles()) {
                    Picture picture = new Picture(null,
                                                  car,
                                                  null,
                                                  PICTURE);
                    try {
                        this.cmsPictureControllerUtil.saveOrEditPicture(new PictureEditCommand(picture, file));
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }

            if (command.getCarForm().getPreviewPictureEditCommand().getPictureFile() != null) {
                Picture previewPicture = command.getCarForm().getPreviewPictureEditCommand().getPicture();

                if (previewPicture == null) {
                    previewPicture = new Picture(null,
                                                 car,
                                                 null,
                                                 PREVIEW_PICTURE);
                }

                command.getCarForm().getPreviewPictureEditCommand().setPicture(previewPicture);
                this.cmsPictureControllerUtil.saveOrEditPicture(command.getCarForm().getPreviewPictureEditCommand());
            }

            if (command.getCarForm().getId() == null) {
                //After the car has been saved, we need to recreate the carForm with all the newly assigned ids
                command.setCarForm(this.carFormCreator.createFormFromEntity(car));
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
    private void saveCarInternetEditCommand(CarInternetContentEditCommand carInternetContentEditCommand) throws Exception {
        List<CarInternetContent> persistedCarInternetContents = this.saveOrEditCarInternetContents(carInternetContentEditCommand);
        List<CarInternetContentForm> updatedCarInternetContentForms =
                persistedCarInternetContents.stream()
                                            .map(this.carInternetContentFormCreator::createFormFromEntity)
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
    public List<CarInternetContent> saveOrEditCarInternetContents(CarInternetContentEditCommand carInternetContentEditCommand) throws Exception {
        List<CarInternetContent> savedCarInternetContents = new ArrayList<>();

        for (CarInternetContentForm carInternetContentForm : carInternetContentEditCommand.getCarInternetContentForms()) {
            try {
                CarInternetContent carInternetContent = this.carInternetContentFormCreator.createEntityFromForm(carInternetContentForm);
                carInternetContent.setAddedDate(this.dateProvider.getCurrentTime());

                if (StringUtils.hasText(carInternetContent.getLink())) {
                    this.carInternetContentDAO.saveOrEdit(carInternetContent);
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
            Car car = carFormCreator.createEntityFromForm(command.getCarForm());
            this.carDAO.delete(car);
        }
    }
}
