package com.phistory.mvc.springframework.view;

import com.phistory.data.dao.impl.ManufacturerDao;
import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.phistory.mvc.command.PictureLoadAction.LOAD_MANUFACTURER_LOGO;
import static com.phistory.mvc.controller.BaseControllerData.MANUFACTURER_ID;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;
import static com.phistory.mvc.controller.cms.CmsBaseController.*;

/**
 * Fills a Spring Framework Model with manufacturer related information
 * 
 * @author gonzalo
 *
 */
@Component
public class ManufacturerModelFiller implements ModelFiller
{
	@Inject
	private ManufacturerDao manufacturerDao;

	@Override
	public void fillModel(Model model)
	{
		model.addAttribute(MANUFACTURERS, 	  			   	manufacturerDao.getAll());
		model.addAttribute(MANUFACTURER_ID,   			   	MANUFACTURER_ID);
		model.addAttribute("loadManufacturerLogoAction", 	LOAD_MANUFACTURER_LOGO.getName());
	}
	
	/**
	 * Fill the model with paginated manufacturer data
	 * 
	 * @param model
	 * @param manufacturersPaginationDto
	 */
	public void fillPaginatedModel(Model model, ManufacturersPaginationDto manufacturersPaginationDto)
	{
		model.addAttribute(MANUFACTURERS_PER_PAGE_DATA,   manufacturersPaginationDto.getManufacturersPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    		  manufacturersPaginationDto.getPagNum());
		model.addAttribute(MANUFACTURERS_PER_PAGE, 		  MANUFACTURERS_PER_PAGE);
		
		fillModel(model);
	}
}
