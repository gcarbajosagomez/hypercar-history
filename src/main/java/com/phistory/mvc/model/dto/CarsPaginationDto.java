package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports car pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class CarsPaginationDto extends PaginationDto
{
	private Integer carsPerPage;

	public CarsPaginationDto(Integer pagNum, Integer carsPerPage) {
		super(pagNum);
		this.carsPerPage = carsPerPage;
	}
}
