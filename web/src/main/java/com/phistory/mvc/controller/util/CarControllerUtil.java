package com.phistory.mvc.controller.util;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.model.dto.CarsPaginationDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Set of utilities for {@link CarController}
 * 
 * @author gonzalo
 *
 */
@Component
public class CarControllerUtil
{		
	/**
	 * Create a search command to search for cars
	 * 
	 * @param carsPaginationDTO
	 * @return
	 */
	public SearchCommand createSearchCommand(CarsPaginationDTO carsPaginationDTO)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put(Car.PRODUCTION_START_DATE_PROPERTY_NAME, Boolean.TRUE);
		
		int paginationFirstResult = carsPaginationDTO.calculatePageFirstResult(carsPaginationDTO.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 null,
								 paginationFirstResult,
								 carsPaginationDTO.getCarsPerPage());
	}
}
