package com.phistory.mvc.controller.cms.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.form.creator.CarInternetContentFormCreator;
import com.phistory.mvc.controller.cms.CmsCarController;
import com.phistory.mvc.controller.cms.CmsCarEditController;
import com.phistory.mvc.controller.util.DateProvider;
import com.phistory.data.dao.impl.CarDao;
import com.phistory.data.dao.impl.CarInternetContentDAO;
import com.phistory.data.model.Picture;
import com.phistory.data.model.Picture.PictureType;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import org.springframework.util.StringUtils;

/**
 * Set of utilities for {@link CmsCarController} and {@link CmsCarEditController}
 * 
 * @author gonzalo
 *
 */
@Component 
public class CMSCarControllerUtil
{		
	@Inject
	private CarDao carDao;
	@Inject
	private CarInternetContentDAO carInternetContentDAO;
	@Inject
	private CarFormCreator carFormCreator;
	@Inject
	private CarInternetContentFormCreator carInternetContentFormCreator;
	@Inject
	private PictureControllerUtil pictureControllerUtil;
	@Inject
	private DateProvider dateProvider;
	
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
            Car car = this.carFormCreator.createEntityFromForm(command.getCarForm());            
            this.carDao.saveOrEdit(car);      

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
            			this.pictureControllerUtil.saveOrEditPicture(new PictureEditCommand(picture, file));
					}
            		catch (Exception e)
            		{
						try
						{
							throw new Exception(e);
						}
						catch (Exception e1) {}
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
            	
            	this.pictureControllerUtil.saveOrEditPicture(command.getCarForm().getPreviewPictureEditCommand());          
            }
            
            if(command.getCarForm().getId() == null)
            {
            	//After the car has been saved, we need to recreate the carForm with all the newly assigned ids
            	command.setCarForm(this.carFormCreator.createFormFromEntity(car));   
            }
            
            return car;                   
        }
        
        return null;
    }
	
	/**
	 * Handle the save or edition of the {@link CarInternetContent}s contained in the supplied {@link CarInternetContentEditCommand#carInternetContentForms}
	 * 
	 * @param carInternetContentEditCommand
	 * @return the newly saved edited {@link List<CarInternetContent>} if everything went well, an empty {@link List} otherwise
	 * @throws Exception
	 */
	public List<CarInternetContent> saveOrEditCarInternetContents(CarInternetContentEditCommand carInternetContentEditCommand) throws Exception
	{
		List<CarInternetContent> savedCarInternetContents = new ArrayList<>();
		
		carInternetContentEditCommand.getCarInternetContentForms().forEach(carInternetContentForm ->
		{
			try
			{
				CarInternetContent carInternetContent = this.carInternetContentFormCreator.createEntityFromForm(carInternetContentForm);
				carInternetContent.setAddedDate(this.dateProvider.getCurrentTime());
                if (StringUtils.hasText(carInternetContent.getLink())) {
                    this.carInternetContentDAO.saveOrEdit(carInternetContent);
                    savedCarInternetContents.add(carInternetContent);
                }
			} 
			catch (Exception e)
			{
				try
				{
					throw e;
				}
				catch (Exception e1) {}
			}			
		});
		
		return savedCarInternetContents;
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
            this.carDao.delete(car);
        }
    }
}
