package com.phistory.mvc.springframework.view;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.controller.BaseControllerData;

/**
 * Fills a Spring Framework Model with the basic information for the web context
 * 
 * @author gonzalo
 *
 */
@Component
public class BaseModelFiller extends BaseControllerData implements ModelFiller
{
	@Override
	public void fillModel(Model model)
	{
		model.addAttribute("paganiHistoryWeb", 	   	PAGANI_HISTORY_WEB);
		model.addAttribute("indexURL", 			   	INDEX_URL);
		model.addAttribute("cookiesPolicyURL", 	   	COOKIES_POLICY_URL);
		model.addAttribute("carsURL", 			   	CARS_URL);	
		model.addAttribute("enginesURL", 		   	ENGINES_URL);	
		model.addAttribute("modelsSearchURL", 	   	MODELS_SEARCH_URL);
		model.addAttribute(ID, 				   		ID);
		model.addAttribute(CARS_PER_PAGE, 		   	CARS_PER_PAGE);
		model.addAttribute(PAG_NUM, 			   	PAG_NUM);
		model.addAttribute(CONTENT_TO_SEARCH, 	   	CONTENT_TO_SEARCH);	
		model.addAttribute("languageCookieName",	LANGUAGE_COOKIE_NAME);
	}
}
