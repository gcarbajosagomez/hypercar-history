package com.phistory.mvc.springframework.view.filler;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;

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
		model.addAttribute("picturesURL",	PICTURES_URL);
	}
}
