package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.model.brake.BrakeSet;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.engine.Engine;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.transmission.Transmission;
import com.phistory.data.model.tyre.TyreSet;
import com.phistory.mvc.cms.command.CarMaterial;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.*;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
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
public class CarFormFactory implements EntityFormFactory<Car, CarForm> {
    public static final String CAR_MATERIAL_STRING_SEPARATOR = "-";

    private SqlPictureDAO     sqlPictureDAO;
    private EntityFormFactory brakeSetFormFactory;
    private EntityFormFactory engineFormFactory;
    private EntityFormFactory transmissionFormFactory;
    private EntityFormFactory tyreSetFormFactory;

    @Inject
    public CarFormFactory(SqlPictureDAO sqlPictureDAO,
                          EntityFormFactory brakeSetFormFactory,
                          EntityFormFactory engineFormFactory,
                          EntityFormFactory transmissionFormFactory,
                          EntityFormFactory tyreSetFormFactory) {
        this.sqlPictureDAO = sqlPictureDAO;
        this.brakeSetFormFactory = brakeSetFormFactory;
        this.engineFormFactory = engineFormFactory;
        this.transmissionFormFactory = transmissionFormFactory;
        this.tyreSetFormFactory = tyreSetFormFactory;
    }

    /**
     * Create a new {@link CarForm} out of the data contained in a {@link Car}
     */
    @Override
    public CarForm createFormFromEntity(Car car) {
        try {
            List<CarMaterial> chassisMaterials = this.parseMaterialsStringToList(car.getChassisMaterials());
            List<CarMaterial> bodyMaterials = this.parseMaterialsStringToList(car.getBodyMaterials());

            List<Picture> pictures = this.sqlPictureDAO.getByCarId(car.getId());
            List<PictureEditCommand> pictureFileEditCommands = new ArrayList<>();
            pictures.stream()
                    .forEach(picture -> pictureFileEditCommands.add(new PictureEditCommand(picture, null)));

            return new CarForm(car.getId(),
                               car.getVisible(),
                               car.getManufacturer(),
                               car.getModel(),
                               car.getEngineLayout(),
                               car.getEngineDisposition(),
                               (EngineForm) this.engineFormFactory.createFormFromEntity(car.getEngine()),
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
                               (BrakeSetForm) this.brakeSetFormFactory.createFormFromEntity(car.getBrakeSet()),
                               (TransmissionForm) this.transmissionFormFactory.createFormFromEntity(car.getTransmission()),
                               car.getFuelTankCapacity(),
                               (TyreSetForm) this.tyreSetFormFactory.createFormFromEntity(car.getTyreSet()),
                               pictureFileEditCommands,
                               car.getDriveWheelType(),
                               car.getRoadLegal(),
                               car.getDescriptionES(),
                               car.getDescriptionEN());
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

            Car car = new Car(carForm.getId(),
                              carForm.getVisible(),
                              carForm.getManufacturer(),
                              carForm.getModel(),
                              carForm.getEngineLayout(),
                              carForm.getEngineDisposition(),
                              (Engine) this.engineFormFactory.createEntityFromForm(carForm.getEngineForm()),
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
                              (BrakeSet) this.brakeSetFormFactory.createEntityFromForm(carForm.getBrakeSetForm()),
                              (Transmission) this.transmissionFormFactory.createEntityFromForm(carForm.getTransmissionForm()),
                              carForm.getFuelTankCapacity(),
                              (TyreSet) this.tyreSetFormFactory.createEntityFromForm(carForm.getTyreSetForm()),
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
     * Parse a materials {@link String} (material1-material2-materialN) into a {@link List<CarMaterial>}
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
     * Parse a {@link List<CarMaterial>} into a materials {@link String} (material1-material2-materialN)
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
