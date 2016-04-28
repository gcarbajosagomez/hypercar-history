package com.phistory.mvc.controller.cms.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.controller.BaseController;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.tcp.data.command.SearchCommand;
import com.tcp.data.dao.impl.CarDao;
import com.tcp.data.model.Picture;
import com.tcp.data.model.Picture.PictureType;
import com.tcp.data.model.car.Car;

/**
 * Set of utilities for the CarController class
 * 
 * @author gonzalo
 *
 */
@Component 
public class CarControllerUtil extends BaseController
{		
	@Inject()
	private CarDao carDao;
	@Inject()
	private CarFormCreator carFormCreator;
	@Inject()
	private PictureControllerUtil pictureControllerUtil;
	
	/**
	 * Handle the save or edition of a Car
	 * 
	 * @param command
	 * @return the newly saved edited car if everything went well, null otherwise
	 * @throws Exception
	 */
	public Car saveOrEditCar(CarFormEditCommand command) throws Exception
    {
        if (command.getCarForm() != null)
        {
            Car car = carFormCreator.createEntityFromForm(command.getCarForm());            
            carDao.saveOrEdit(car);      

            if (command.getCarForm().getPictureFiles() != null)
            {
            	command.getCarForm().getPictureFiles().forEach(file ->
            	{
            		Picture picture = new Picture(null,
							  					  car,
							  					  null, 
							  					  PictureType.PICTURE);
            		try
            		{
						pictureControllerUtil.saveOrEditPicture(new PictureEditCommand(picture, file));
					}
            		catch (Exception e)
            		{
						try
						{
							throw new Exception(e);
						} catch (Exception e1) {}
					} 
            	});
            }
            
            if (command.getCarForm().getPreviewPictureEditCommand().getPictureFile() != null)
            {       
            	Picture previewPicture = command.getCarForm().getPreviewPictureEditCommand().getPicture();
            	
            	if (previewPicture == null)
            	{
            		previewPicture = new Picture(null,
            									 car,
            									 null,
            									 PictureType.PREVIEW_PICTURE);
            	}   	
            	
            	command.getCarForm().getPreviewPictureEditCommand().setPicture(previewPicture);
            	
            	pictureControllerUtil.saveOrEditPicture(command.getCarForm().getPreviewPictureEditCommand());          
            }
            
            if(command.getCarForm().getId() == null)
            {
            	//After the car has been saved, we need to recreate the carForm with all the newly assigned ids
            	command.setCarForm(carFormCreator.createFormFromEntity(car));   
            }
            
            return car;                   
        }
        
        return null;
    }
	
	/**
	 * Handle the deletion of a car
	 * 
	 * @param command
	 * @throws Exception
	 */
	public void deleteCar(CarFormEditCommand command) throws Exception
    {
        if (command.getCarForm() != null)
        {        	
            Car car = carFormCreator.createEntityFromForm(command.getCarForm());            
            carDao.delete(car);
        }
    }
	
	/**
	 * Create the data needed to handle car pagination
	 * 
	 * @param carsPaginationDto
	 * @return
	 */
	public Map<String, Object> createPaginationData(CarsPaginationDto carsPaginationDto)
    {			
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put(CARS, carDao.getByCriteria(createSearchCommand(carsPaginationDto)));
    	data.put(CARS_PER_PAGE_DATA, carsPaginationDto.getCarsPerPage());
    	data.put(PAG_NUM_DATA, carsPaginationDto.getPagNum());		

    	return data;
    }
	
	/**
	 * Create a search command to search for cars
	 * 
	 * @param paginationDto
	 * @return
	 */
	public static SearchCommand createSearchCommand(CarsPaginationDto carsPaginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("productionStartDate", Boolean.TRUE);
		
		carsPaginationDto.calculatePageFirstResult(carsPaginationDto.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 carsPaginationDto.getFirstResult(),
								 carsPaginationDto.getCarsPerPage());
	}
}
