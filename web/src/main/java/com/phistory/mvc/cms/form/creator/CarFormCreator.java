package com.phistory.mvc.cms.form.creator;

import javax.inject.Inject;

import com.phistory.mvc.cms.command.CarBodyMaterial;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.data.dao.impl.PictureDao;
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
public class CarFormCreator implements EntityFormCreator<Car, CarForm>
{
    public static final String CAR_BODY_MATERIAL_STRING_SEPARATOR = "-";

    @Inject
    private PictureDao pictureDao;
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
    public CarForm createFormFromEntity(Car car)
    {
        try
        {
        	List<CarBodyMaterial> bodyMaterials = new ArrayList<>();
            String bodyMaterialsString = car.getBodyMaterials();

            if (StringUtils.hasText(bodyMaterialsString)) {
                if (bodyMaterialsString.contains(CAR_BODY_MATERIAL_STRING_SEPARATOR)) {
                    bodyMaterials = Stream.of(bodyMaterialsString.split(CAR_BODY_MATERIAL_STRING_SEPARATOR))
                                          .map(CarBodyMaterial::map)
                                          .collect(Collectors.toList());
                } else {
                    bodyMaterials.add(CarBodyMaterial.map(bodyMaterialsString));
                }
            }

            CarForm carForm = new CarForm(car.getId(),
            							  car.getManufacturer(),
            							  car.getModel(),
            							  car.getEngineLayout(),
            							  this.engineFormCreator.createFormFromEntity(car.getEngine()),
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
            
            if (car.getId() != null)
            {
            	try
            	{
            		PictureEditCommand pictureEditCommand = new PictureEditCommand(new Picture(), null);
            		Picture carPreview = pictureDao.getCarPreview(car.getId());
            		
            		if (carPreview != null)
            		{
            			pictureEditCommand.setPicture(carPreview);
            		}
            		
            		carForm.setPreviewPictureEditCommand(pictureEditCommand);
            	}
            	catch(Exception e)
            	{
            		log.error(e.toString(), e);
            	}
            }
            return carForm;
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return new CarForm();
    }

    /**
     * Create a new {@link Car} out of the data contained in a {@link CarForm}
     */
    @Override
    public Car createEntityFromForm(CarForm carForm)
    {
        try
        {
            List<String> carBodyMaterialsStrings = carForm.getBodyMaterials().stream()
                                                                             .map(carBodyMaterial -> {
                                                                                 if (carBodyMaterial != null) {
                                                                                     return carBodyMaterial.getName();
                                                                                 }
                                                                                 return null;
                                                                             })
                                                                             .filter(Objects::nonNull)
                                                                             .collect(Collectors.toList());

            String carBodyMaterials = null;
            if (!carBodyMaterialsStrings.isEmpty()) {
                carBodyMaterials = String.join(CAR_BODY_MATERIAL_STRING_SEPARATOR, carBodyMaterialsStrings);
            }

            Car car = new Car(carForm.getId(),
            				  carForm.getManufacturer(),
            				  carForm.getModel(),
            				  carForm.getEngineLayout(),
							  this.engineFormCreator.createEntityFromForm(carForm.getEngineForm()),
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
        }
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return new Car();
    } 
}
