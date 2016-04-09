package com.phistory.mvc.springframework.view;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.BaseControllerData;

/**
 * Fills a Spring Framework Model with picture related information
 * 
 * @author gonzalo
 *
 */
@Component
public class PictureModelFiller extends BaseControllerData implements ModelFiller
{
	@Override
	public void fillModel(Model model) 
	{		
		model.addAttribute(PICTURE_ID, 	  	PICTURE_ID);
		model.addAttribute("picturesURL",	PICTURES_URL);
	}
}
