package com.phistory.mvc.springframework.view.filler;

import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE;
import static com.phistory.mvc.controller.BaseControllerData.CARS_URL;
import static com.phistory.mvc.controller.BaseControllerData.CONTENT_TO_SEARCH;
import static com.phistory.mvc.controller.BaseControllerData.COOKIES_POLICY_URL;
import static com.phistory.mvc.controller.BaseControllerData.ENGINES_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_COOKIE_NAME;
import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_DATA;
import static com.phistory.mvc.controller.BaseControllerData.MODELS_SEARCH_URL;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM;
import static com.phistory.mvc.controller.BaseControllerData.DO_NOT_TRACK_REQUEST_PARAM;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CmsBaseController.EDIT_URL;
import static com.phistory.mvc.controller.cms.CmsBaseController.TECHNOLOGY_STACK_URL;

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
