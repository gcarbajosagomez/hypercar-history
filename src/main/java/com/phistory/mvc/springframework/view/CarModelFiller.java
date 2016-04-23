package com.phistory.mvc.springframework.view;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.BaseControllerData;

/**
 * Fills a Spring Framework Model with car related information
 * 
 * @author gonzalo
 *
 */
@Component
public class CarModelFiller extends BaseControllerData implements ModelFiller
{	
	@Override
	public void fillModel(Model model)
	{
		model.addAttribute(CAR_ID, 			   	        CAR_ID);	
		model.addAttribute("loadCarPreviewAction",      LOAD_CAR_PREVIEW_ACTION);
		model.addAttribute("loadCarPictureAction",      LOAD_CAR_PICTURE_ACTION);		
		model.addAttribute("unitsOfMeasureCookieName",  UNITS_OF_MEASURE_COOKIE_NAME);
		model.addAttribute("unitsOfMeasureMetric", 	    UNITS_OF_MEASURE_METRIC);
		model.addAttribute("unitsOfMeasureImperial",    UNITS_OF_MEASURE_IMPERIAL);		
		model.addAttribute("paginationURL",    			PAGINATION_URL);		
	}
}
