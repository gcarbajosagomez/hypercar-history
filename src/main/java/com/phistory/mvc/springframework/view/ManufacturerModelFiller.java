package com.phistory.mvc.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.LOAD_MANUFACTURER_LOGO_ACTION;
import static com.phistory.mvc.controller.BaseControllerData.MANUFACTURER_ID;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;
import static com.phistory.mvc.controller.cms.CmsBaseController.MANUFACTURERS;
import static com.phistory.mvc.controller.cms.CmsBaseController.MANUFACTURERS_PER_PAGE;
import static com.phistory.mvc.controller.cms.CmsBaseController.MANUFACTURERS_PER_PAGE_DATA;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import com.tcp.data.dao.impl.ManufacturerDao;

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
		model.addAttribute(MANUFACTURERS, 	  			   manufacturerDao.getAll());
		model.addAttribute(MANUFACTURER_ID,   			   MANUFACTURER_ID);
		model.addAttribute("loadManufacturerLogoAction",   LOAD_MANUFACTURER_LOGO_ACTION);
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
