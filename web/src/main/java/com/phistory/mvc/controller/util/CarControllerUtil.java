package com.phistory.mvc.controller.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.phistory.data.model.car.CarInternetContent;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.car.Car;

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
	 * @param carsPaginationDto
	 * @return
	 */
	public SearchCommand createSearchCommand(CarsPaginationDto carsPaginationDto)
	{
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put(Car.PRODUCTION_START_DATE_PROPERTY_NAME, Boolean.TRUE);
		
		int paginationFirstResult = carsPaginationDto.calculatePageFirstResult(carsPaginationDto.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 null,
								 null,
								 orderByMap,
								 null,
								 paginationFirstResult,
								 carsPaginationDto.getCarsPerPage());
	}

	/**
	 * Get the {@link Car} whose {@link Car#id} matches the {@code id} supplied
     *
	 * @param cars
	 * @param id
     * @return The {@link Car} found if any, null otherwise
     */
	public Car loadCarById(List<Car> cars, Long id) {
        return cars.parallelStream()
                   .filter(car -> car.getId().equals(id))
                   .findFirst()
                   .orElse(null);
    }

	/**
	 * Get a {@link List<Car>} paginated and ordered by {@link Car#productionStartDate} desc
     *
	 * @param cars
	 * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
	public List<Car> loadCarsBySearchCommand(List<Car> cars, CarsPaginationDto paginationDTO) {
        return cars.parallelStream()
                   .skip(paginationDTO.getFirstResult())
                   .limit(paginationDTO.getCarsPerPage())
                   .sorted((car1, car2) -> {
                       Calendar productionDate1 = car1.getProductionStartDate();
                       Calendar productionDate2 = car2.getProductionStartDate();
                       return productionDate1.compareTo(productionDate2);
                   })
                   .collect(Collectors.toList());
    }

	/**
	 * Get a {@link List<CarInternetContent>} whose {@link CarInternetContent#car#id} matches the supplied {@code carId}
     *
	 * @param internetContents
	 * @param carId
     * @return The resulting {@link List<CarInternetContent>}
     */
	public List<CarInternetContent> getCarInternetContentsByCarId(List<CarInternetContent> internetContents,
                                                                  Long carId) {
        return internetContents.parallelStream()
                               .filter(internetContent -> internetContent.getCar().getId().equals(carId))
                               .collect(Collectors.toList());
    }
}
