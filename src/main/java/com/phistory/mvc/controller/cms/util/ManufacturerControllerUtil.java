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
import com.tcp.data.command.SearchCommand;
import com.tcp.data.dao.impl.ManufacturerDao;
import com.tcp.data.model.Manufacturer;

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
	 * Handle the save or edition of a Manufacturer
	 * 
	 * @param command
	 * @return the newly saved edited car if everything went well, null otherwise
	 * @throws Exception
	 */
	public Manufacturer saveOrEditManufacturer(ManufacturerFormEditCommand command, Model model)
    {
        if(command.getManufacturerForm() != null)
        {
            Manufacturer manufacturer = manufacturerFormCreator.createEntityFromForm(command.getManufacturerForm());
            manufacturerDao.saveOrEdit(manufacturer);
            
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
    public void deleteManufacturer(ManufacturerFormEditCommand command)
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
	 * @param paginationDto
	 * @return
	 */
	public static SearchCommand createSearchCommand(ManufacturersPaginationDto manufacturersPaginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("name", Boolean.TRUE);
		
		manufacturersPaginationDto.calculatePageFirstResult(manufacturersPaginationDto.getManufacturersPerPage());
		
		return new SearchCommand(Manufacturer.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 manufacturersPaginationDto.getFirstResult(),
								 manufacturersPaginationDto.getManufacturersPerPage());
	}
}
