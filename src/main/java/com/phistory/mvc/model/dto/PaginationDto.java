package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@NoArgsConstructor
public class PaginationDto
{
    public static final Integer ITEMS_PER_PAGE_DEFAULT_VALUE = 8;
	private static final Integer PAG_NUM_DEFAULT_VALUE = 1;

	private Integer pagNum = PAG_NUM_DEFAULT_VALUE;
	private int firstResult;

	public PaginationDto(Integer pagNum) {
		super();
		this.pagNum = pagNum;
	}

	public void setPn(Integer pagNum) {
		this.pagNum = pagNum;
	}

	/**
	 * Calculate the index of the first result based on the number of the current page and items per page
	 * 
	 * @param itemsPerPage
	 */
	public void calculatePageFirstResult(Integer itemsPerPage)
	{
		firstResult = 0;
		
		int pagNum = this.getPagNum();
		
    	if(itemsPerPage != null && this.pagNum > 1)
    	{
    		firstResult = (this.pagNum - 1) * itemsPerPage;
    	}
    	else
    	{
    		firstResult = 0;
    	}
	}
}