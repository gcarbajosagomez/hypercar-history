package com.phistory.mvc.controller.cms.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.controller.BaseController;
import com.phistory.mvc.model.dto.PaginationDto;
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
                for (MultipartFile file : command.getCarForm().getPictureFiles())
                {
                	Picture picture = new Picture(null,
                								  car,
                								  null, 
                								  PictureType.PICTURE);
                	
                	pictureControllerUtil.saveOrEditPicture(new PictureEditCommand(picture, file)); 
                }
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
            	//After the car has been saved, we need to recreate the carForm with all the new assigned ids
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
	
	public Map<String, Object> handlePagination(PaginationDto paginationDto)
    {			
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put(CARS, getCarDao().getByCriteria(createSearchCommand(paginationDto)));
    	data.put(ITEMS_PER_PAGE_DATA, paginationDto.getItemsPerPage());
    	data.put(PAG_NUM_DATA, paginationDto.getPagNum());		

    	return data;
    }
	
	/**
	 * Create a search command to search for cars
	 * 
	 * @param paginationDto
	 * @return
	 */
	public static SearchCommand createSearchCommand(PaginationDto paginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("productionStartDate", Boolean.TRUE);
		
	    paginationDto.calculatePageFirstResult();
		
		return new SearchCommand(Car.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 paginationDto.getFirstResult(),
								 paginationDto.getItemsPerPage());
	}
}
