package com.phistory.mvc.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;

/**
 * Controller to handle Cars URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
public class CarController extends BaseController
{
	@Inject
	private CarControllerUtil carControllerUtil;
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private CarsListModelFiller carsListModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	
	@RequestMapping(value = CARS_URL + HTML_SUFFIX,
					method = RequestMethod.GET)
	public ModelAndView handleCarsList(Model model,
									   @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
									   @RequestParam(defaultValue = "8", value = CARS_PER_PAGE, required = true) int carsPerPage)
	{		
		try
		{		
			CarsPaginationDto carsPaginationDto = new CarsPaginationDto(pagNum, carsPerPage);
			
			carsListModelFiller.fillPaginatedModel(model, carsPaginationDto);
			carModelFiller.fillModel(model);
			pictureModelFiller.fillModel(model);
			
			return new ModelAndView();
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR);
		}		
	}
	
	@RequestMapping(value = CAR_DETAILS_URL + HTML_SUFFIX,
		    		method = RequestMethod.GET)
	public ModelAndView handleCarDetails(Model model,
							  		  	 @RequestParam(value = CAR_ID, required = true) Long carId,
							  		  	 @CookieValue(value=UNITS_OF_MEASURE_COOKIE_NAME, defaultValue=UNITS_OF_MEASURE_METRIC, required=false) String unitsOfMeasure)
	{
		try
		{	
			pictureModelFiller.fillModel(model);
			carModelFiller.fillModel(model);
			model.addAttribute("car", getCarDao().getById(carId));		
			model.addAttribute("pictureIds", getPictureDao().getIdsByCarId(carId));
			model.addAttribute(UNITS_OF_MEASURE, unitsOfMeasure);
	
			return new ModelAndView();
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
	
			return new ModelAndView(ERROR);
		}
	}
	
	@RequestMapping(value = CARS_URL + HTML_SUFFIX,
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON,
    				produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, Object> handlePagination(@RequestBody(required = true) CarsPaginationDto carsPaginationDto)
	{			
		return carControllerUtil.createPaginationData(carsPaginationDto);
	}	
}
