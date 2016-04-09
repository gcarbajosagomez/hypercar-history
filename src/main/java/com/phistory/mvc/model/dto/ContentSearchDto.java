package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports content search data 
 * 
 * @author Gonzalo
 *
 */
@Data
@NoArgsConstructor
public class ContentSearchDto extends PaginationDto
{	
	private String contentToSearch;
	private Integer carsPerPage;

	public ContentSearchDto(Integer pagNum, Integer carsPerPage, String contentToSearch)
	{
		super(pagNum);
		this.carsPerPage = carsPerPage;
		this.contentToSearch = contentToSearch;
	}
}
