package com.phistory.mvc.controller;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.impl.SQLCarDAO;
import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.dao.sql.impl.SQLContentSearchDAO;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
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
	private SQLCarDAO                     sqlCarDAO;
    @Inject
	@Getter
	private InMemoryCarDAO                inMemoryCarDAO;
	@Inject
	@Getter
	private SQLPictureDAO                 sqlPictureDAO;
	@Inject
	@Getter
	private InMemoryPictureDAO            inMemoryPictureDAO;
	@Inject
	@Getter
	private SQLContentSearchDAO           sqlContentSearchDAO;
	@Inject
	@Getter
	private SQLCarInternetContentDAO      sqlCarInternetContentDAO;
	@Inject
	@Getter
	private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;
	@Inject
	@Getter
	private InMemoryManufacturerDAO       inMemoryManufacturerDAO;
	@Inject
	private ModelFiller                   baseModelFiller;
	@Getter
	@Inject
	private ResourceBundleMessageSource   messageSource;
	
	@ModelAttribute
    public void fillBaseModel(Model model,
							  Device device,
    						  HttpServletRequest request,
							  @RequestParam(value = DO_NOT_TRACK_REQUEST_PARAM, required = false) boolean dnt)
	{
		String requestURI = this.extractRequestUriFromRequest(request);
		log.info("Handling " + request.getMethod() + " request to URI " + requestURI);
		
		model.addAttribute("requestURI",  		requestURI);
		model.addAttribute("requestIsDesktop",  device.isNormal());
		model.addAttribute("deviceMake",  		device.getDevicePlatform().name());
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
		String requestQueryString = (request.getQueryString() != null && !request.getQueryString().isEmpty()) ? "?" + request.getQueryString() : "";
		
		requestedUri.append(request.getRequestURI().toString());
		requestedUri.append(requestQueryString);
		
		return requestedUri.toString();
	}
}
