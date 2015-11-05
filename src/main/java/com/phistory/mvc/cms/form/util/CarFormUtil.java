package com.phistory.mvc.cms.form.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Picture;
import com.tcp.data.model.car.Car;


/**
 *
 * @author Gonzalo
 */
@Component
public class CarFormUtil
{
    private static final Logger logger = LoggerFactory.getLogger(CarFormUtil.class);    
    private static PictureDao pictureDao;
    
    @Autowired
    public CarFormUtil(PictureDao pictureDao)
	{
		CarFormUtil.pictureDao = pictureDao;     	
    }

    public static CarForm createFormFromObject(Car car)
    {
        try
        {
        	
            CarForm carForm = new CarForm(car.getId(),
            							  car.getManufacturer(),
            							  car.getModel(),
            							  car.getEngineLayout(),
            							  EngineFormUtil.createFormFromObject(car.getEngine()),
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
            							  BrakeSetFormUtil.createFormFromObject(car.getBrakeSet()),
            							  TransmissionFormUtil.createFormFromObject(car.getTransmission()),
            							  car.getFuelTankCapacity(),
            							  TyreSetFormUtil.createFormFromObject(car.getTyreSet()),
            							  null, 
            							  car.getDriveWheelType());
            
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
            		logger.error(e.toString(), e);
            	}
            }
            return carForm;
            
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new CarForm();
    }

    public static Car createObjectFromForm(CarForm form)
    {
        try
        {
            Car car = new Car(form.getId(),
            				  form.getManufacturer(),
            				  form.getModel(),
            				  form.getEngineLayout(),
            				  EngineFormUtil.createObjectFromForm(form.getEngineForm()),
            				  form.getBodyShape(),
            				  form.getSeatsConfig(),
            				  form.getTopSpeed(),
            				  form.getAcceleration(),
            				  form.getFuelConsuption(),
            				  form.getProductionStartDate(),
            				  form.getProductionEndDate(),
            				  form.getWeight(),
            				  form.getLength(),
            				  form.getWidth(),
            				  form.getHeight(),
            				  BrakeSetFormUtil.createObjectFromForm(form.getBrakeSetForm()),
            				  TransmissionFormUtil.createObjectFromForm(form.getTransmissionForm()),
            				  form.getFuelTankCapacity(),
            				  TyreSetFormUtil.createObjectFromForm(form.getTyreSetForm()),
            				  form.getDriveWheel());
            
            car.getTransmission().setCar(car);
            car.getBrakeSet().setCar(car);
            car.getTyreSet().setCar(car);

            return car;
            
        }
        catch (Exception ex)
        {
            logger.error(ex.toString(), ex);
        }
        
        return new Car();
    } 
}
