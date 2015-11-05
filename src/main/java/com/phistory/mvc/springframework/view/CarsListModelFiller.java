package com.phistory.mvc.springframework.view;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.BaseControllerData;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.PaginationDto;
import com.tcp.data.dao.impl.CarDao;

/**
 * Fills a Spring Framework Model with cars list related information
 * 
 * @author gonzalo
 *
 */
@Component
public class CarsListModelFiller extends BaseControllerData implements ModelFiller
{
	@Inject
	private CarDao carDao;
	
	@Override
	public void fillModel(Model model)
	{		
		model.addAttribute(MODELS, 	carDao.getDistinctModelsWithId());		
	}
	
	/**
	 * Fill the model with paginated car data
	 * 
	 * @param model
	 * @param paginationDto
	 */
	public void fillPaginatedModel(Model model, PaginationDto paginationDto)
	{
		model.addAttribute(CARS, 					carDao.getByCriteria(CarControllerUtil.createSearchCommand(paginationDto)));
		model.addAttribute(ITEMS_PER_PAGE_DATA, 	paginationDto.getItemsPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    	paginationDto.getPagNum());
		
		fillModel(model);
	}
}
