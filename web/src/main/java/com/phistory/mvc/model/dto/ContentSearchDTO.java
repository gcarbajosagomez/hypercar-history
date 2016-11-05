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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContentSearchDTO extends CarsPaginationDTO
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
                                    this.getCarsPerPage(),
                                    this.contentToSearch);
	}
}
