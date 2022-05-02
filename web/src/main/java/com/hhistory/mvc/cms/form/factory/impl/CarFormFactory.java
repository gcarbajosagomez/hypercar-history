package com.hhistory.mvc.cms.form.factory.impl;

import com.hhistory.data.dao.sql.SqlEngineRepository;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.brake.BrakeSet;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.engine.Engine;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.transmission.Transmission;
import com.hhistory.data.model.tyre.TyreSet;
import com.hhistory.mvc.cms.command.CarMaterial;
import com.hhistory.mvc.cms.command.PictureEditCommand;
import com.hhistory.mvc.cms.form.*;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Creates new {@link Car}s out of the data contained in {@link CarEditForm}s and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Inject))
public class CarFormFactory implements EntityFormFactory<Car, CarEditForm> {
    public static final String CAR_MATERIAL_STRING_SEPARATOR = "-";

    private SqlPictureRepository sqlPictureRepository;
    private SqlEngineRepository  sqlEngineRepository;
    private EntityFormFactory    brakeSetFormFactory;
    private EntityFormFactory    engineFormFactory;
    private EntityFormFactory    transmissionFormFactory;
    private EntityFormFactory    tyreSetFormFactory;

    @Override
    public CarEditForm buildFormFromEntity(Car car) {
        if (Objects.nonNull(car)) {
            try {
                List<CarMaterial> chassisMaterials = this.parseMaterialsStringToList(car.getChassisMaterials());
                List<CarMaterial> bodyMaterials = this.parseMaterialsStringToList(car.getBodyMaterials());

                List<Picture> pictures = this.sqlPictureRepository.getByCarId(car.getId());
                List<PictureEditCommand> pictureFileEditCommands = new ArrayList<>();
                pictures.forEach(picture -> pictureFileEditCommands.add(new PictureEditCommand(picture, null)));

                return new CarEditForm(car.getId(),
                                       car.getVisible(),
                                       car.getManufacturer(),
                                       car.getModel(),
                                       car.getEngineLayout(),
                                       car.getEngineDisposition(),
                                       this.engineFormFactory.buildFormFromEntity(car.getEngine()),
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
                                       car.getWheelbase(),
                                       this.brakeSetFormFactory.buildFormFromEntity(car.getBrakeSet()),
                                       this.transmissionFormFactory.buildFormFromEntity(car.getTransmission()),
                                       car.getFuelTankCapacity(),
                                       this.tyreSetFormFactory.buildFormFromEntity(car.getTyreSet()),
                                       pictureFileEditCommands,
                                       car.getDriveWheelType(),
                                       car.getRoadLegal(),
                                       car.getDescriptionES(),
                                       car.getDescriptionEN());
            } catch (Exception e) {
                log.error(e.toString(), e);
            }
        }

        return new CarEditForm();
    }

    @Override
    public Car buildEntityFromForm(CarEditForm carEditForm) {
        try {
            String carChassisMaterials = this.parseMaterialsListToString(carEditForm.getChassisMaterials());
            String carBodyMaterials = this.parseMaterialsListToString(carEditForm.getBodyMaterials());

            Engine engine;
            EditForm engineEditForm = carEditForm.getEngineEditForm();
            Long engineId = engineEditForm.getId();
            engine = Optional.ofNullable(engineId)
                             .flatMap(sqlEngineRepository::findById)
                             .orElse((Engine) engineFormFactory.buildEntityFromForm(engineEditForm));

            BrakeSet brakeSet = (BrakeSet) this.brakeSetFormFactory.buildEntityFromForm(carEditForm.getBrakeSetEditForm());
            EditForm transmissionEditForm = carEditForm.getTransmissionEditForm();
            Transmission transmission = (Transmission) this.transmissionFormFactory.buildEntityFromForm(transmissionEditForm);
            TyreSet tyreSet = (TyreSet) this.tyreSetFormFactory.buildEntityFromForm(carEditForm.getTyreSetEditForm());

            Car car = new Car(carEditForm.getId(),
                              carEditForm.getVisible(),
                              carEditForm.getManufacturer(),
                              carEditForm.getModel(),
                              carEditForm.getEngineLayout(),
                              carEditForm.getEngineDisposition(),
                              engine,
                              carChassisMaterials,
                              carBodyMaterials,
                              carEditForm.getBodyShape(),
                              carEditForm.getSeatsConfig(),
                              carEditForm.getTopSpeed(),
                              carEditForm.getAcceleration(),
                              carEditForm.getFuelConsumption(),
                              carEditForm.getProductionType(),
                              carEditForm.getProductionStartDate(),
                              carEditForm.getProductionEndDate(),
                              carEditForm.getWeight(),
                              carEditForm.getLength(),
                              carEditForm.getWidth(),
                              carEditForm.getHeight(),
                              carEditForm.getWheelbase(),
                              brakeSet,
                              transmission,
                              carEditForm.getFuelTankCapacity(),
                              tyreSet,
                              carEditForm.getDriveWheel(),
                              carEditForm.getRoadLegal(),
                              carEditForm.getDescriptionES(),
                              carEditForm.getDescriptionEN());

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
                                                              .toList();

        String carChassisMaterials = null;
        if (!carChassisMaterialsStrings.isEmpty()) {
            carChassisMaterials = String.join(CAR_MATERIAL_STRING_SEPARATOR, carChassisMaterialsStrings);
        }
        return carChassisMaterials;
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
                                      .toList();
            } else {
                materialsList.add(CarMaterial.map(materialsString));
            }
        }
        return materialsList;
    }
}
