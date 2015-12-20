package com.phistory.mvc.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO class that transports content search data 
 * 
 * @author Gonzalo
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
