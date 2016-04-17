package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.cms.CmsBaseController.*;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value = {"cms", CMS_CONTEXT + LOGIN_URL})
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
				setLoggedIn(true);
				request.sendRedirect(CARS_URL);
			}
			
			if (error != null)
			{
				model.addAttribute(LOGIN_ERROR, "Invalid username and password!");
			}

			if (logout != null)
			{
				model.addAttribute(LOGOUT, "You've been logged out successfully.");
				setLoggedIn(false);
			}
			
			return new ModelAndView(CMS_CONTEXT + LOGIN_URL);
		}
		catch(Exception e)
		{
			setLoggedIn(false);
			log.error(e.toString(), e);
				
			return new ModelAndView(ERROR_VIEW_NAME);
		}	
	}
}
