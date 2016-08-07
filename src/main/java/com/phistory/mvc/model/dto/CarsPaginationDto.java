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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CarsPaginationDto extends PaginationDto
{
    private Integer carsPerPage = ITEMS_PER_PAGE_DEFAULT_VALUE;

	public CarsPaginationDto(Integer pagNum, Integer carsPerPage) {
		super(pagNum);
		this.carsPerPage = carsPerPage;
	}

	public void setCpp(Integer carsPerPage) {
        this.carsPerPage = carsPerPage;
    }
}
