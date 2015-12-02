package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports manufacturer pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ManufacturersPaginationDto extends PaginationDto
{
	private Integer manufacturersPerPage;

	public ManufacturersPaginationDto(Integer pagNum, Integer manufacturersPerPage) {
		super(pagNum);
		this.manufacturersPerPage = manufacturersPerPage;
	}
}
