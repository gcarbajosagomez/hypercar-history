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
@RequestMapping(value = "cookiesPolicy",
				method = RequestMethod.GET)
public class CookiesPolicyController extends BaseController
{		
	public ModelAndView handleDefault()
	{
		try
		{
			return new ModelAndView();
		}
		catch (Exception e)
		{
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR_VIEW_NAME);
		}		
	}
}
