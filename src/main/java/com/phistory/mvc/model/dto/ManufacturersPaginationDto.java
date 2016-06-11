package com.phistory.mvc.model.dto;

import lombok.Data;

/**
 * DTO class that transports manufacturer pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
public class ManufacturersPaginationDto extends PaginationDto
{
	private Integer manufacturersPerPage;

	public ManufacturersPaginationDto(Integer pagNum, Integer manufacturersPerPage) {
		super(pagNum);
		this.manufacturersPerPage = manufacturersPerPage;
	}
}
