package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.cms.CmsBaseController.CARS_URL;
import static com.tcp.data.model.car.CarInternetContentType.REVIEW_ARTICLE;
import static com.tcp.data.model.car.CarInternetContentType.VIDEO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.controller.util.CarInternetContentUtils;
import com.phistory.mvc.controller.utils.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.CarInternetContent;

/**
 * Controller to handle Cars URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = CARS_URL,
				method = {GET, HEAD})
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
	@Inject
	private CarInternetContentUtils carInternetContentUtils;
	
	@RequestMapping
	public ModelAndView handleCarsList(Model model,
									   CarsPaginationDto carsPaginationDto)
	{		
		try
		{		
			this.carsListModelFiller.fillPaginatedModel(model, carsPaginationDto);
			this.carModelFiller.fillModel(model);
			this.pictureModelFiller.fillModel(model);
			
			return new ModelAndView();
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR_VIEW_NAME);
		}		
	}
	
	@RequestMapping(value = "/" + "{" + ID + "}")
	public ModelAndView handleCarDetails(Model model,
							  		  	 @PathVariable(ID) Long carId,
							  		  	 @CookieValue(value = UNITS_OF_MEASURE_COOKIE_NAME,
							  		  	 			  defaultValue = UNITS_OF_MEASURE_METRIC,
							  		  	 			  required=false) String unitsOfMeasure)
	{
		try
		{	
			this.pictureModelFiller.fillModel(model);
			this.carModelFiller.fillModel(model);
			model.addAttribute("car", super.getCarDao().getById(carId));
			model.addAttribute(PICTURE_IDS, super.getPictureDao().getIdsByCarId(carId));
			model.addAttribute(UNITS_OF_MEASURE, unitsOfMeasure);
			List<CarInternetContent> carInternetContents = super.getCarInternetContentDAO().getByCarId(carId);
			List<CarInternetContent> videos = carInternetContents.stream()
					   											 .filter(content -> content.getType().equals(VIDEO))
					   											 .collect(Collectors.toList());
					   											 
			model.addAttribute(YOUTUBE_VIDEO_IDS, this.carInternetContentUtils.extractYoutubeVideoIds(videos));
			
			model.addAttribute(CAR_INTERNET_CONTENT_REVIEW_ARTICLES, carInternetContents.stream()
																						.filter(content -> content.getType().equals(REVIEW_ARTICLE))
																						.collect(Collectors.toList()));
			return new ModelAndView(CAR_DETAILS);
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
	
			return new ModelAndView(ERROR_VIEW_NAME);
		}
	}
	
	@RequestMapping(value = "/" + PAGINATION_URL)
	@ResponseBody
	public Map<String, Object> handlePagination(CarsPaginationDto carsPaginationDto)
	{		
		Map<String, Object> data = new HashMap<String, Object>();
    	data.put(CARS, super.getCarDao().getByCriteria(this.carControllerUtil.createSearchCommand(carsPaginationDto)));
    	data.put(CARS_PER_PAGE_DATA, carsPaginationDto.getCarsPerPage());
    	data.put(PAG_NUM_DATA, carsPaginationDto.getPagNum());		
    	
		return data;
	}	
}
