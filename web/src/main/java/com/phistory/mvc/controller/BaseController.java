package com.phistory.mvc.controller;

import com.phistory.data.dao.sql.impl.CarDAO;
import com.phistory.data.dao.sql.impl.CarInternetContentDAO;
import com.phistory.data.dao.sql.impl.ContentSearchDAO;
import com.phistory.data.dao.sql.impl.PictureDAO;
import com.phistory.mvc.springframework.view.ModelFiller;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mobile.device.Device;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

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
	private CarDAO carDAO;
    @Inject
	@Getter
	private com.phistory.data.dao.inmemory.CarDAO inMemoryCarDAO;
	@Inject
	@Getter
	private PictureDAO pictureDAO;
	@Inject
	@Getter
	private com.phistory.data.dao.inmemory.PictureDAO inMemoryPictureDAO;
	@Inject
	@Getter
	private ContentSearchDAO contentSearchDAO;
	@Inject
	@Getter
	private CarInternetContentDAO carInternetContentDAO;
	@Inject
	@Getter
	private com.phistory.data.dao.inmemory.CarInternetContentDAO inMemoryCarInternetContentDAO;
	@Inject
	private ModelFiller baseModelFiller;
	@Getter
	@Inject
	private ResourceBundleMessageSource messageSource;
	
	@ModelAttribute
    public void fillBaseModel(Model model,
							  Device device,
    						  HttpServletRequest request,
							  @RequestParam(value = DO_NOT_TRACK_REQUEST_PARAM, required = false) boolean dnt)
	{
		String requestURI = this.extractRequestUriFromRequest(request);
		log.info("Handling " + request.getMethod() + " request to URI " + requestURI);
		
		model.addAttribute("requestURI",  		requestURI);
		model.addAttribute("requestIsCarsList", requestURI.contains(CARS_URL));
		model.addAttribute("requestIsDesktop",  device.isNormal());
		model.addAttribute("doNotTrack",        dnt);

		this.baseModelFiller.fillModel(model);
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
		
//		TODO: implement a two-time HTTP request body mechanism, since the way it was implemented, the body of the request can only be 
		//read once, hence failing to reach the controllers as it came empty
		//hint: http://stackoverflow.com/questions/34804205/how-can-i-read-request-body-multiple-times-in-spring-handlermethodargumentresol/34806876#34806876
//		if (request.getMethod().equals(POST.name())) 
//		{
//			Optional<String> requestUriParamsOptional = this.hTTPUtil.extractRequestPayloadParamsFromRequest(request);
//			if (requestUriParamsOptional.isPresent())
//			{
//				requestQueryString = "?" + requestUriParamsOptional.get();
//			}
//		}
//		else
//		{            
//			requestQueryString = (request.getQueryString() != null && !request.getQueryString().isEmpty()) ? "?" + request.getQueryString() : "";
//		}
		
		requestQueryString = (request.getQueryString() != null && !request.getQueryString().isEmpty()) ? "?" + request.getQueryString() : "";
		
		requestedUri.append(request.getRequestURI().toString());
		requestedUri.append(requestQueryString);
		
		return requestedUri.toString();
	}
}
