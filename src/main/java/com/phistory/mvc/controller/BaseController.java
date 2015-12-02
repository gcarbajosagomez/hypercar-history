package com.phistory.mvc.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.dao.impl.CarDao;
import com.tcp.data.dao.impl.ContentSearchDao;
import com.tcp.data.dao.impl.PictureDao;

/**
 * Base controller that contains common data and functionality 
 * 
 * @author Gonzalo
 *
 */
@Slf4j
public class BaseController extends BaseControllerData
{	
	@Inject
	@Getter
	private CarDao carDao;
	@Inject
	@Getter
	private PictureDao pictureDao;
	@Inject
	@Getter
	private ContentSearchDao contentSearchDao;	
	@Inject
	private ModelFiller baseModelFiller;
	
	@ModelAttribute()
    public void fillBaseModel(Model model,
    						  HttpServletRequest request)
	{
		String requestUri = extractRequestUriFromRequest(request);
		log.info("Handling " + request.getMethod() + " request to URI " + requestUri);
		
		model.addAttribute("requestURI",  requestUri);		
		
		baseModelFiller.fillModel(model);	
	}
	
	/**
	 * Extract the requested URI from the servlet request
	 * 
	 * @param request
	 * @return A string containing the requested URI if everything went well, an empty String otherwise
	 */
	private String extractRequestUriFromRequest(HttpServletRequest request)
	{
		String requestQueryString = (request.getQueryString() != null && !request.getQueryString().isEmpty()) ? "?" + request.getQueryString() : "";
		
		return request.getRequestURI().toString() + requestQueryString;
	}	
}
