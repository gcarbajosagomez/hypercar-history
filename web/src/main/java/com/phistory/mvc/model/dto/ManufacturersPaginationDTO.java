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
public class ManufacturersPaginationDTO extends PaginationDTO
{
	private Integer manufacturersPerPage = ITEMS_PER_PAGE_DEFAULT_VALUE;;

	public ManufacturersPaginationDTO(Integer pagNum, Integer manufacturersPerPage) {
		super(pagNum);
		this.manufacturersPerPage = manufacturersPerPage;
	}

	public void setMpp(Integer manufacturersPerPage) {
		this.manufacturersPerPage = manufacturersPerPage;
	}
}
