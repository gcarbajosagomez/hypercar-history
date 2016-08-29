package com.phistory.mvc.controller.cms.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.form.creator.ManufacturerFormCreator;
import com.phistory.mvc.controller.cms.CmsBaseController;
import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.impl.ManufacturerDao;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.Car;

/**
 * Set of utilities for the ManufacturerController class
 * 
 * @author gonzalo
 *
 */
@Component 
public class ManufacturerControllerUtil extends CmsBaseController
{
	@Inject()
	private ManufacturerDao manufacturerDao;
	@Inject()
	private ManufacturerFormCreator manufacturerFormCreator;
	
	/**
	 * Handle the save or edition of a {@link Manufacturer}
	 * 
	 * @param command
	 * @return the newly saved or edited {@link Car} if everything went well, null otherwise
	 * @throws Exception
	 */
	public Manufacturer saveOrEditManufacturer(ManufacturerFormEditCommand command, Model model) throws Exception
    {
        if(command.getManufacturerForm() != null)
        {
            Manufacturer manufacturer = manufacturerFormCreator.createEntityFromForm(command.getManufacturerForm());
            manufacturerDao.saveOrEdit(manufacturer);
            
            if(command.getManufacturerForm().getId() == null)
            {
            	//After the car has been saved, we need to recreate the {@link ManufacturerForm} with all the newly assigned ids
            	command.setManufacturerForm(manufacturerFormCreator.createFormFromEntity(manufacturer));   
            }
            
            return manufacturer;
        }
        
        return null;
    }
	
	/**
	 * Handle the deletion of a manufacturer
	 * 
	 * @param command
	 * @throws Exception
	 */
    public void deleteManufacturer(ManufacturerFormEditCommand command) throws Exception
    {
        if (command.getManufacturerForm() != null)
        {
            Manufacturer manufacturer = manufacturerFormCreator.createEntityFromForm(command.getManufacturerForm());            
            manufacturerDao.delete(manufacturer);
        }
    }
    
    /**
	 * Create the data needed to handle manufacturer pagination
	 * 
	 * @param carsPaginationDto
	 * @return
	 */
	public Map<String, Object> createPaginationData(ManufacturersPaginationDto carsPaginationDto)
    {			
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put(MANUFACTURERS, manufacturerDao.getByCriteria(createSearchCommand(carsPaginationDto)));
    	data.put(MANUFACTURERS_PER_PAGE_DATA, carsPaginationDto.getManufacturersPerPage());
    	data.put(PAG_NUM_DATA, carsPaginationDto.getPagNum());		

    	return data;
    }
	
	/**
	 * Create a search command to search for manufacturers
	 * 
	 * @param manufacturersPaginationDto
	 * @return
	 */
	public static SearchCommand createSearchCommand(ManufacturersPaginationDto manufacturersPaginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("name", Boolean.TRUE);

		int paginationFirstResult = manufacturersPaginationDto.calculatePageFirstResult(manufacturersPaginationDto.getManufacturersPerPage());
		
		return new SearchCommand(Manufacturer.class,
								 null,
								 null,
								 null,
								 orderByMap,
				  				 null,
								 paginationFirstResult,
								 manufacturersPaginationDto.getManufacturersPerPage());
	}
}
