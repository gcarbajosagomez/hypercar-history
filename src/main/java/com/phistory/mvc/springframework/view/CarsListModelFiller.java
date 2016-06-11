package com.phistory.mvc.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE_DATA;
import static com.phistory.mvc.controller.BaseControllerData.MODELS;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.utils.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.tcp.data.dao.impl.CarDao;

/**
 * Fills a Spring Framework Model with cars list related information
 * 
 * @author gonzalo
 *
 */
@Component
public class CarsListModelFiller implements ModelFiller
{
	@Inject
	private CarDao carDao;
	@Inject
	private CarControllerUtil carControllerUtil;
	
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
		model.addAttribute(CARS, 				this.carDao.getByCriteria(this.carControllerUtil.createSearchCommand(carsPaginationDto)));
		model.addAttribute(CARS_PER_PAGE_DATA, 	carsPaginationDto.getCarsPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    carsPaginationDto.getPagNum());
		
		fillModel(model);
	}
}
