package com.phistory.mvc.model.dto;

/**
 * DTO class that transports content search data 
 * 
 * @author Gonzalo
 *
 */
public class ContentSearchDto extends PaginationDto
{	
	private String contentToSearch;	

	public ContentSearchDto() {
		super();
	}

	public ContentSearchDto(Integer pagNum, Integer itemsPerPage, String contentToSearch)
	{
		super(pagNum, itemsPerPage);
		this.contentToSearch = contentToSearch;
	}
	
	public String getContentToSearch() {
		return contentToSearch;
	}

	public void setContentToSearch(String contentToSearch) {
		this.contentToSearch = contentToSearch;
	}
}
