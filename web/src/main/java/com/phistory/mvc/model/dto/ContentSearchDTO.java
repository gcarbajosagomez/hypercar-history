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
public class ContentSearchDTO extends PaginationDTO
{	
	private String contentToSearch;

	public ContentSearchDTO(Integer pagNum, Integer carsPerPage, String contentToSearch)
	{
		super(pagNum, carsPerPage);
		this.contentToSearch = contentToSearch;
	}

	public void setCts(String contentToSearch) {
		this.contentToSearch = contentToSearch;
	}

	public ContentSearchDTO clone() {
		return new ContentSearchDTO(this.getPagNum(),
                                    this.getItemsPerPage(),
                                    this.contentToSearch);
	}
}
