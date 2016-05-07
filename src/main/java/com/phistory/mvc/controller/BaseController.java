package com.phistory.mvc.controller;

import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phistory.mvc.controller.util.HTTPUtil;
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
@RequestMapping(method = HEAD)
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
	@Inject
	private HTTPUtil hTTPUtil;
	
	@ModelAttribute
    public void fillBaseModel(Model model,
    						  HttpServletRequest request)
	{
		String requestUri = extractRequestUriFromRequest(request);
		log.info("Handling " + request.getMethod() + " request to URI " + requestUri);
		
		model.addAttribute("requestURI",  requestUri);		
		
		baseModelFiller.fillModel(model);	
	}
	
	/**
	 * Extract the requested URI from the {@link HttpServletRequest}
	 * 
	 * @param request
	 * @return A string containing the requested URI if everything went well, an empty String otherwise
	 */
	private String extractRequestUriFromRequest(HttpServletRequest request)
	{
		StringBuilder requestedUri = new StringBuilder();
		String requestQueryString = null;
		
		if (request.getMethod().equals(POST.name())) 
		{
			Optional<String> requestUriParamsOptional = this.hTTPUtil.extractRequestPayloadParamsFromRequest(request);
			if (requestUriParamsOptional.isPresent())
			{
				requestQueryString = "?" + requestUriParamsOptional.get();
			}
		}
		else
		{            
			requestQueryString = (request.getQueryString() != null && !request.getQueryString().isEmpty()) ? "?" + request.getQueryString() : "";
		}
		
		requestedUri.append(request.getRequestURI().toString());
		requestedUri.append(requestQueryString);
		
		return requestedUri.toString();
	}
}
