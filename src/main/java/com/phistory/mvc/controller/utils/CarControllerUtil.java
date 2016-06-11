package com.phistory.mvc.controller.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.tcp.data.command.SearchCommand;
import com.tcp.data.model.car.Car;

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
	 * @param paginationDto
	 * @return
	 */
	public SearchCommand createSearchCommand(CarsPaginationDto carsPaginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("productionStartDate", Boolean.TRUE);
		
		carsPaginationDto.calculatePageFirstResult(carsPaginationDto.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 carsPaginationDto.getFirstResult(),
								 carsPaginationDto.getCarsPerPage());
	}
}
