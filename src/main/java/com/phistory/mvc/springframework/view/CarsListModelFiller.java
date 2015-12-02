package com.phistory.mvc.springframework.view;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.BaseControllerData;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
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
	 * @param carsPaginationDto
	 */
	public void fillPaginatedModel(Model model, CarsPaginationDto carsPaginationDto)
	{
		model.addAttribute(CARS, 				carDao.getByCriteria(CarControllerUtil.createSearchCommand(carsPaginationDto)));
		model.addAttribute(CARS_PER_PAGE_DATA, 	carsPaginationDto.getCarsPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    carsPaginationDto.getPagNum());
		
		fillModel(model);
	}
}
