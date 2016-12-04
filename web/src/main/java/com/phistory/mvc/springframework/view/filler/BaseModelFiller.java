package com.phistory.mvc.springframework.view.filler;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.TECHNOLOGY_STACK_URL;
import static com.phistory.mvc.controller.BaseControllerData.*;

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
		model.addAttribute("technologyStackURL", 	TECHNOLOGY_STACK_URL);
		model.addAttribute("siteURL", 				"http://www.paganihistory.com");
		model.addAttribute(ID, 				   		ID);
		model.addAttribute("carsPerPage", 		   	CARS_PER_PAGE);
		model.addAttribute("pagNum", 			   	PAG_NUM);
		model.addAttribute("contentToSearch", 	   	CONTENT_TO_SEARCH);
		model.addAttribute("languageCookieName",	LANGUAGE_COOKIE_NAME);
		model.addAttribute("cmsContext", 			CMS_CONTEXT);
		model.addAttribute("languageQueryString",	LANGUAGE_DATA);
		model.addAttribute("doNotTrackParam",		DO_NOT_TRACK_REQUEST_PARAM);
	}
}
