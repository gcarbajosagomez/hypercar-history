package com.phistory.mvc.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE;
import static com.phistory.mvc.controller.BaseControllerData.CARS_URL;
import static com.phistory.mvc.controller.BaseControllerData.CONTENT_TO_SEARCH;
import static com.phistory.mvc.controller.BaseControllerData.COOKIES_POLICY_URL;
import static com.phistory.mvc.controller.BaseControllerData.ENGINES_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_COOKIE_NAME;
import static com.phistory.mvc.controller.BaseControllerData.MODELS_SEARCH_URL;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CmsBaseController.EDIT_URL;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Fills a Spring Framework Model with the basic information for the web context
 * 
 * @author gonzalo
 *
 */
@Component
public class BaseModelFiller implements ModelFiller
{
	@Override
	public void fillModel(Model model)
	{
		model.addAttribute("indexURL", 			   	INDEX_URL);
		model.addAttribute("cookiesPolicyURL", 	   	COOKIES_POLICY_URL);
		model.addAttribute("carsURL", 			   	CARS_URL);	
		model.addAttribute("enginesURL", 		   	ENGINES_URL);	
		model.addAttribute("modelsSearchURL", 	   	MODELS_SEARCH_URL);
		model.addAttribute("editURL", 				EDIT_URL);
		model.addAttribute(ID, 				   		ID);
		model.addAttribute("carsPerPage", 		   	CARS_PER_PAGE);
		model.addAttribute(PAG_NUM, 			   	PAG_NUM);
		model.addAttribute(CONTENT_TO_SEARCH, 	   	CONTENT_TO_SEARCH);	
		model.addAttribute("languageCookieName",	LANGUAGE_COOKIE_NAME);
		model.addAttribute("cmsContext", 			CMS_CONTEXT);
	}
}
