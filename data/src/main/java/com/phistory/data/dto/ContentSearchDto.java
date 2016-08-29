package com.phistory.data.dto;

import java.util.List;

import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentSearchDto
{
	private List<Object> results;
	private int          totalResults;
}
