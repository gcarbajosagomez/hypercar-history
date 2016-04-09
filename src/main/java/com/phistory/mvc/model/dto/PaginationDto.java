package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DTO class that transports pagination data 
 * 
 * @author Gonzalo
 *
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PaginationDto
{
	@NonNull
	private Integer pagNum;
	private int firstResult;
	
	/**
	 * Calculate the index of the first result based on the number of the current page and items per page
	 * 
	 * @param itemsPerPage
	 */
	public void calculatePageFirstResult(Integer itemsPerPage)
	{
		firstResult = 0;
		
		int pagNum = getPagNum();
		
    	if(itemsPerPage != null && pagNum > 1)
    	{
    		firstResult = (pagNum - 1) * itemsPerPage;
    	}
    	else
    	{
    		firstResult = 0;
    	}
	}
}