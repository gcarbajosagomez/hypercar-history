package com.phistory.mvc.model.dto;

import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO class that transports car pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CarsPaginationDto extends PaginationDto
{
	private static final Integer CARS_PER_PAGE_DEFAULT_VALUE = 8;
	
	@JsonProperty(value = CARS_PER_PAGE)
	private Integer carsPerPage = CARS_PER_PAGE_DEFAULT_VALUE;

	public CarsPaginationDto(Integer pagNum, Integer carsPerPage) {
		super(pagNum);
		this.carsPerPage = carsPerPage;
	}	
}
