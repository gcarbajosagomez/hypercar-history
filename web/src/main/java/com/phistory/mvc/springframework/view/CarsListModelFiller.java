package com.phistory.mvc.springframework.view;

import com.phistory.data.dao.inmemory.CarDAO;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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
	private CarDAO inMemoryCarDAO;
	@Inject
	private CarControllerUtil carControllerUtil;
	
	@Override
	public void fillModel(Model model)
	{		
		model.addAttribute(MODELS, this.inMemoryCarDAO.getCars());
	}
	
	/**
	 * Fill the model with paginated car data
	 * 
	 * @param model
	 * @param carsPaginationDto
	 */
	public void fillPaginatedModel(Model model, CarsPaginationDto carsPaginationDto)
	{
		model.addAttribute(CARS, 				this.loadCarsBySearchCommand(carsPaginationDto));
		model.addAttribute(CARS_PER_PAGE_DATA, 	carsPaginationDto.getCarsPerPage());
		model.addAttribute(PAG_NUM_DATA, 	    carsPaginationDto.getPagNum());
		
		this.fillModel(model);
	}

	/**
	 * Get a {@link List < Car >} paginated and ordered by {@link Car#productionStartDate} desc
	 *
	 * @param paginationDTO
	 * @return The resulting {@link List<Car>}
	 */
	public List<Car> loadCarsBySearchCommand(CarsPaginationDto paginationDTO) {
		return this.inMemoryCarDAO.getCars()
                                         .stream()
                                         .skip(paginationDTO.getFirstResult())
                                         .limit(paginationDTO.getCarsPerPage())
                                         .sorted((car1, car2) -> {
                                            Calendar productionDate1 = car1.getProductionStartDate();
                                            Calendar productionDate2 = car2.getProductionStartDate();
                                            return productionDate1.compareTo(productionDate2);
                                         })
                                         .collect(Collectors.toList());
	}
}
