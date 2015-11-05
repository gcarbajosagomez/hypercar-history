package com.phistory.mvc.model.dto;

/**
 * DTO class that transports pagination data 
 * 
 * @author Gonzalo
 *
 */
public class PaginationDto
{
	private Integer pagNum;
	private Integer itemsPerPage;
	private int firstResult;
	
	public PaginationDto() {
		super();
	}

	public PaginationDto(Integer pagNum, Integer itemsPerPage) {
		super();
		this.pagNum = pagNum;
		this.itemsPerPage = itemsPerPage;
	}
	
	public void calculatePageFirstResult()
	{
		firstResult = 0;
		
		int pagNum = getPagNum();
		
    	if(getItemsPerPage() != null && pagNum > 1)
    	{
    		firstResult = (pagNum - 1) * getItemsPerPage();
    	}
    	else
    	{
    		firstResult = 0;
    	}
	}

	public Integer getPagNum() {
		return pagNum;
	}

	public void setPagNum(Integer pagNum) {
		this.pagNum = pagNum;
	}

	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getFirstResult() {
		return firstResult;
	}
}