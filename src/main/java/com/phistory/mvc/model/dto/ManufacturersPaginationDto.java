package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports manufacturer pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@NoArgsConstructor
public class ManufacturersPaginationDto extends PaginationDto
{
	private Integer manufacturersPerPage;

	public ManufacturersPaginationDto(Integer pagNum, Integer manufacturersPerPage) {
		super(pagNum);
		this.manufacturersPerPage = manufacturersPerPage;
	}
}
