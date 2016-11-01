package com.phistory.mvc.springframework.view;

import com.phistory.data.dao.impl.CarDao;
import com.phistory.mvc.controller.InMemoryEntityStorage;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.phistory.mvc.controller.BaseControllerData.*;

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
	@Inject
	private InMemoryEntityStorage inMemoryEntityStorage;
	
	@Override
	public void fillModel(Model model)
	{		
		model.addAttribute(MODELS, this.inMemoryEntityStorage.getCars());
	}
	
	/**
	 * Fill the model with paginated car data
	 * 
	 * @param model
	 * @param carsPaginationDto
	 */
	public void fillPaginatedModel(Model model, CarsPaginationDto carsPaginationDto)
	{
		model.addAttribute(CARS, 				this.inMemoryEntityStorage.loadCarsBySearchCommand(carsPaginationDto));
		model.addAttribute(CARS_PER_PAGE_DATA, 	carsPaginationDto.getCarsPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    carsPaginationDto.getPagNum());
		
		fillModel(model);
	}
}
