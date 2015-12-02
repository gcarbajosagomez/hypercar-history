package com.phistory.mvc.controller;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Controller
public class CookiesPolicyController extends BaseController
{		
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
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR);
		}		
	}
}
