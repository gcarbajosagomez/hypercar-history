package com.phistory.data.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentSearchDto
{
	private List<Object> results;
	private int totalResults;	
}
