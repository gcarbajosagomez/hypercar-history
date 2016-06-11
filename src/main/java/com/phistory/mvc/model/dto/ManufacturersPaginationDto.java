package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO class that transports manufacturer pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ManufacturersPaginationDto extends PaginationDto
{
	private Integer manufacturersPerPage;

	public ManufacturersPaginationDto(Integer pagNum, Integer manufacturersPerPage) {
		super(pagNum);
		this.manufacturersPerPage = manufacturersPerPage;
	}
}
