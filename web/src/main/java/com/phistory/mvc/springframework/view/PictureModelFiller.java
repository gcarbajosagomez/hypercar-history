package com.phistory.mvc.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;
import static com.phistory.mvc.controller.BaseControllerData.PICTURE_ID;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Fills a Spring Framework Model with picture related information
 * 
 * @author gonzalo
 *
 */
@Component
public class PictureModelFiller implements ModelFiller
{
	@Override
	public void fillModel(Model model) 
	{		
		model.addAttribute(PICTURE_ID, 	  	PICTURE_ID);
		model.addAttribute("picturesURL",	PICTURES_URL);
	}
}
