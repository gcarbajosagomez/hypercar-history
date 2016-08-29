package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.TECHNOLOGY_STACK_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to handle requests to Technology Stack URLs
 * 
 * @author Gonzalo
 *
 */
@Controller
@RequestMapping(value = TECHNOLOGY_STACK_URL,
				method = {GET, HEAD})
public class TechnologyStackController
{
	@RequestMapping
	public ModelAndView handleDefault()
	{
		return new ModelAndView();
	}
}
