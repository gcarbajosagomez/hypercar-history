package com.phistory.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to handle CookiesPolicy URLs
 * 
 * @author Gonzalo
 *
 */
@Controller
public class CookiesPolicyController extends BaseController
{	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = COOKIES_POLICY_URL + HTML_SUFFIX,
				    method = RequestMethod.GET)
	public ModelAndView handleDefault()
	{
		try
		{
			return new ModelAndView();
		}
		catch (Exception e)
		{
			logger.error(e.toString(), e);
			
			return new ModelAndView(ERROR);
		}		
	}
}
