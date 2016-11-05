package com.phistory.mvc.springframework.view;

import com.phistory.data.dao.sql.impl.ManufacturerDAO;
import com.phistory.mvc.model.dto.ManufacturersPaginationDTO;
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
	private ManufacturerDAO manufacturerDAO;

	@Override
	public void fillModel(Model model)
	{
		model.addAttribute(MANUFACTURERS, 	  			   	manufacturerDAO.getAll());
		model.addAttribute(MANUFACTURER_ID,   			   	MANUFACTURER_ID);
		model.addAttribute("loadManufacturerLogoAction", 	LOAD_MANUFACTURER_LOGO.getName());
	}
	
	/**
	 * Fill the model with paginated manufacturer data
	 * 
	 * @param model
	 * @param manufacturersPaginationDTO
	 */
	public void fillPaginatedModel(Model model, ManufacturersPaginationDTO manufacturersPaginationDTO)
	{
		model.addAttribute(MANUFACTURERS_PER_PAGE_DATA,   manufacturersPaginationDTO.getManufacturersPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    		  manufacturersPaginationDTO.getPagNum());
		model.addAttribute(MANUFACTURERS_PER_PAGE, 		  MANUFACTURERS_PER_PAGE);
		
		fillModel(model);
	}
}
