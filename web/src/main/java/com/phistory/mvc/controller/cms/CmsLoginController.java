package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CmsBaseController.LOGIN_URL;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value = {CMS_CONTEXT + LOGIN_URL})
public class CmsLoginController extends CmsBaseController
{	
	@RequestMapping
	public ModelAndView handleLogin(HttpServletResponse request,
									Model model,
									@RequestParam(value = LOGIN_SUCCESS, required = false) String success,
									@RequestParam(value = LOGIN_ERROR, required = false) String error,
									@RequestParam(value = LOGOUT, required = false) String logout)
	{	
		try
		{		
			if (success != null)
			{				
				request.sendRedirect(CARS_URL);
			}			
			else if (error != null)
			{
				model.addAttribute(LOGGED_IN, false);
				model.addAttribute(LOGIN_ERROR, "Invalid username and password!");
			}
			else if (logout != null)
			{
				model.addAttribute(LOGGED_IN, false);
				model.addAttribute(LOGOUT, "You've been logged out successfully.");
			}
			//we haven't logged in yet
			else
			{
				model.addAttribute(LOGGED_IN, false);
			}
			
			return new ModelAndView(CMS_CONTEXT + LOGIN_URL);
		}
		catch(Exception e)
		{
			model.addAttribute(LOGGED_IN, false);
			log.error(e.toString(), e);
				
			return new ModelAndView(ERROR_VIEW_NAME);
		}	
	}
}
