package com.phistory.mvc.cms.form.creator;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Picture;
import com.tcp.data.model.car.Car;

/**
 * Creates new Cars out of the data contained in CarForms and vice versa
 * 
 * @author Gonzalo
 */
@Slf4j
@Component
public class CarFormCreator implements EntityFormCreator<Car, CarForm>
{
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
     * Create a new CarForm out of the data contained in a Car
     */
    @Override
    public CarForm createFormFromEntity(Car car)
    {
        try
        {
        	
            CarForm carForm = new CarForm(car.getId(),
            							  car.getManufacturer(),
            							  car.getModel(),
            							  car.getEngineLayout(),
            							  engineFormCreator.createFormFromEntity(car.getEngine()),
            							  car.getBodyShape(),
            							  car.getCarSeatsConfig(),
            							  car.getTopSpeed(),
            							  car.getAcceleration(),
            							  car.getFuelConsumption(),
            							  car.getProductionStartDate(),
            							  car.getProductionEndDate(),
            							  car.getWeight(),
            							  car.getLength(),
            							  car.getWidth(),
            							  car.getHeight(),
            							  brakeSetFormCreator.createFormFromEntity(car.getBrakeSet()),
            							  transmissionFormCreator.createFormFromEntity(car.getTransmission()),
            							  car.getFuelTankCapacity(),
            							  tyreSetFormCreator.createFormFromEntity(car.getTyreSet()),
            							  null, 
            							  null,
            							  car.getDriveWheelType(),
            							  car.getRoadLegal());
            
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
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new CarForm();
    }

    /**
     * Create a new Car out of the data contained in a CarForm
     */
    @Override
    public Car createEntityFromForm(CarForm carForm)
    {
        try
        {
            Car car = new Car(carForm.getId(),
            				  carForm.getManufacturer(),
            				  carForm.getModel(),
            				  carForm.getEngineLayout(),
            				  engineFormCreator.createEntityFromForm(carForm.getEngineForm()),
            				  carForm.getBodyShape(),
            				  carForm.getSeatsConfig(),
            				  carForm.getTopSpeed(),
            				  carForm.getAcceleration(),
            				  carForm.getFuelConsuption(),
            				  carForm.getProductionStartDate(),
            				  carForm.getProductionEndDate(),
            				  carForm.getWeight(),
            				  carForm.getLength(),
            				  carForm.getWidth(),
            				  carForm.getHeight(),
            				  brakeSetFormCreator.createEntityFromForm(carForm.getBrakeSetForm()),
            				  transmissionFormCreator.createEntityFromForm(carForm.getTransmissionForm()),
            				  carForm.getFuelTankCapacity(),
            				  tyreSetFormCreator.createEntityFromForm(carForm.getTyreSetForm()),
            				  carForm.getDriveWheel(),
            				  carForm.getRoadLegal());
            
            car.getTransmission().setCar(car);
            car.getBrakeSet().setCar(car);
            car.getTyreSet().setCar(car);

            return car;
            
        }
        catch (Exception ex)
        {
            log.error(ex.toString(), ex);
        }
        
        return new Car();
    } 
}
