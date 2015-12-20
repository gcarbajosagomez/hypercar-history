package com.phistory.mvc.controller.cms;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value = {"/cms", "/cms/login"})
public class CmsLoginController extends CmsBaseController
{	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleLogin(HttpServletResponse request,
									Model model,
									@RequestParam(value = "success", required = false) String success,
									@RequestParam(value = "error", required = false) String error,
									@RequestParam(value = "logout", required = false) String logout)
	{	
		try
		{		
			if (success != null)
			{				
				setLoggedIn(true);
				request.sendRedirect(CARS_URL);
			}
			
			if (error != null)
			{
				model.addAttribute("error", "Invalid username and password!");
			}

			if (logout != null)
			{
				model.addAttribute("logout", "You've been logged out successfully.");
				setLoggedIn(false);
			}
			
			return new ModelAndView(CMS_CONTEXT + LOGIN_URL);
		}
		catch(Exception e)
		{
			setLoggedIn(false);
			log.error(e.toString(), e);
				
			return new ModelAndView(ERROR);
		}	
	}
}
