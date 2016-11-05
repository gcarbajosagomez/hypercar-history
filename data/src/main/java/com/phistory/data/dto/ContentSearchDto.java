package com.phistory.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContentSearchDto
{
	private List<Object> results;
	private int          totalResults;
}
