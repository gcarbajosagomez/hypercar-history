package com.phistory.mvc.cms.form.creator;

import javax.inject.Inject;

import com.phistory.mvc.cms.command.CarMaterial;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.Picture;
import com.phistory.data.model.car.Car;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates new {@link Car}s out of the data contained in {@link CarForm}s and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class CarFormCreator implements EntityFormCreator<Car, CarForm> {
    public static final String CAR_MATERIAL_STRING_SEPARATOR = "-";

    @Inject
    private SQLPictureDAO SQLPictureDAO;
    @Inject
    private BrakeSetFormCreator brakeSetFormCreator;
    @Inject
    private EngineFormCreator engineFormCreator;
    @Inject
    private TransmissionFormCreator transmissionFormCreator;
    @Inject
    private TyreSetFormCreator tyreSetFormCreator;

    /**
     * Create a new {@link CarForm} out of the data contained in a {@link Car}
     */
    @Override
    public CarForm createFormFromEntity(Car car) {
        try {
            List<CarMaterial> chassisMaterials = this.parseMaterialsStringToList(car.getChassisMaterials());
            List<CarMaterial> bodyMaterials = this.parseMaterialsStringToList(car.getBodyMaterials());

            CarForm carForm = new CarForm(  car.getId(),
                                            car.getManufacturer(),
                                            car.getModel(),
                                            car.getEngineLayout(),
                                            this.engineFormCreator.createFormFromEntity(car.getEngine()),
                                            chassisMaterials,
                                            bodyMaterials,
                                            car.getBodyShape(),
                                            car.getCarSeatsConfig(),
                                            car.getTopSpeed(),
                                            car.getAcceleration(),
                                            car.getFuelConsumption(),
                                            car.getProductionType(),
                                            car.getProductionStartDate(),
                                            car.getProductionEndDate(),
                                            car.getWeight(),
                                            car.getLength(),
                                            car.getWidth(),
                                            car.getHeight(),
                                            this.brakeSetFormCreator.createFormFromEntity(car.getBrakeSet()),
                                            this.transmissionFormCreator.createFormFromEntity(car.getTransmission()),
                                            car.getFuelTankCapacity(),
                                            this.tyreSetFormCreator.createFormFromEntity(car.getTyreSet()),
                                            null,
                                            null,
                                            car.getDriveWheelType(),
                                            car.getRoadLegal(),
                                            car.getDescriptionES(),
                                            car.getDescriptionEN());

            if (car.getId() != null) {
                try {
                    PictureEditCommand pictureEditCommand = new PictureEditCommand(new Picture(), null);
                    Picture carPreview = SQLPictureDAO.getCarPreview(car.getId());

                    if (carPreview != null) {
                        pictureEditCommand.setPicture(carPreview);
                    }

                    carForm.setPreviewPictureEditCommand(pictureEditCommand);
                } catch (Exception e) {
                    log.error(e.toString(), e);
                }
            }
            return carForm;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new CarForm();
    }

    /**
     * Create a new {@link Car} out of the data contained in a {@link CarForm}
     */
    @Override
    public Car createEntityFromForm(CarForm carForm) {
        try {
            String carChassisMaterials = this.parseMaterialsListToString(carForm.getChassisMaterials());
            String carBodyMaterials = this.parseMaterialsListToString(carForm.getBodyMaterials());

            Car car = new Car(  carForm.getId(),
                                carForm.getManufacturer(),
                                carForm.getModel(),
                                carForm.getEngineLayout(),
                                this.engineFormCreator.createEntityFromForm(carForm.getEngineForm()),
                                carChassisMaterials,
                                carBodyMaterials,
                                carForm.getBodyShape(),
                                carForm.getSeatsConfig(),
                                carForm.getTopSpeed(),
                                carForm.getAcceleration(),
                                carForm.getFuelConsumption(),
                                carForm.getProductionType(),
                                carForm.getProductionStartDate(),
                                carForm.getProductionEndDate(),
                                carForm.getWeight(),
                                carForm.getLength(),
                                carForm.getWidth(),
                                carForm.getHeight(),
                                this.brakeSetFormCreator.createEntityFromForm(carForm.getBrakeSetForm()),
                                this.transmissionFormCreator.createEntityFromForm(carForm.getTransmissionForm()),
                                carForm.getFuelTankCapacity(),
                                this.tyreSetFormCreator.createEntityFromForm(carForm.getTyreSetForm()),
                                carForm.getDriveWheel(),
                                carForm.getRoadLegal(),
                                carForm.getDescriptionES(),
                                carForm.getDescriptionEN());

            car.getTransmission().setCar(car);
            car.getBrakeSet().setCar(car);
            car.getTyreSet().setCar(car);

            return car;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new Car();
    }

    /**
     * Parse a materials {@link String} (material1-material2-materialn) into a {@link List<CarMaterial>}
     *
     * @param materialsString
     * @return
     */
    private List<CarMaterial> parseMaterialsStringToList(String materialsString) {
        List<CarMaterial> materialsList = new ArrayList<>();

        if (StringUtils.hasText(materialsString)) {
            if (materialsString.contains(CAR_MATERIAL_STRING_SEPARATOR)) {
                materialsList = Stream.of(materialsString.split(CAR_MATERIAL_STRING_SEPARATOR))
                        .map(CarMaterial::map)
                        .collect(Collectors.toList());
            } else {
                materialsList.add(CarMaterial.map(materialsString));
            }
        }
        return materialsList;
    }

    /**
     * Parse a {@link List<CarMaterial>} into a materials {@link String} (material1-material2-materialn)
     *
     * @param materialList
     * @return
     */
    private String parseMaterialsListToString(List<CarMaterial> materialList) {
        List<String> carChassisMaterialsStrings = materialList.stream()
                .map(carBodyMaterial -> {
                    if (carBodyMaterial != null) {
                        return carBodyMaterial.getName();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String carChassisMaterials = null;
        if (!carChassisMaterialsStrings.isEmpty()) {
            carChassisMaterials = String.join(CAR_MATERIAL_STRING_SEPARATOR, carChassisMaterialsStrings);
        }
        return carChassisMaterials;
    }
}
