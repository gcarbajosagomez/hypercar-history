package com.phistory.mvc.springframework.view.filler;

import org.springframework.ui.Model;

/**
 * Fills a Spring Framework Model with information
 * 
 * @author gonzalo
 *
 */
public interface ModelFiller
{
	/**
	 * Fill the model with information
	 * 
	 * @param model
	 */
	 void fillModel(Model model);
}
