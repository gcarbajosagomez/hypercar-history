package com.phistory.mvc.controller;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.inmemory.PictureDAO;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.controller.util.CarInternetContentUtils;
import com.phistory.mvc.model.dto.CarsPaginationDTO;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.phistory.data.model.car.CarInternetContentType.REVIEW_ARTICLE;
import static com.phistory.data.model.car.CarInternetContentType.VIDEO;
import static com.phistory.mvc.controller.cms.CmsBaseController.CARS_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

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
    private static final int MINUTES_TO_LOAD_CARS_AFTER = 5;

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
	@Inject
	private PictureDAO inMemoryPictureDAO;

	@RequestMapping
	public ModelAndView handleCarsList(Model model,
									   CarsPaginationDTO carsPaginationDTO)
	{		
		try
		{
            this.carsListModelFiller.fillPaginatedModel(model,
					carsPaginationDTO);
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
							  		  	 			  required = false) String unitsOfMeasure)
	{
		try
		{
			this.pictureModelFiller.fillModel(model);
			this.carModelFiller.fillModel(model);

			model.addAttribute(CAR,                 super.getInMemoryCarDAO().loadCarById(carId));
			model.addAttribute(PICTURE_IDS,         this.inMemoryPictureDAO.getPictureIdsByCarId(carId));
			model.addAttribute(UNITS_OF_MEASURE,    unitsOfMeasure);

            List<CarInternetContent> carInternetContents = super.getInMemoryCarInternetContentDAO().getCarInternetContentsByCarId(carId);
			List<CarInternetContent> videos = carInternetContents.stream()
					   											 .filter(content -> content.getType().equals(VIDEO))
					   											 .collect(Collectors.toList());
					   											 
			model.addAttribute(YOUTUBE_VIDEO_IDS,                       this.carInternetContentUtils.extractYoutubeVideoIds(videos));
			model.addAttribute(CAR_INTERNET_CONTENT_REVIEW_ARTICLES,    carInternetContents.stream()
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
	public Map<String, Object> handlePagination(CarsPaginationDTO carsPaginationDTO)
	{		
		Map<String, Object> data = new HashMap<>();
		SearchCommand searchCommand = this.carControllerUtil.createSearchCommand(carsPaginationDTO);
    	data.put(CARS,                  super.getCarDAO().getByCriteria(searchCommand));
    	data.put(CARS_PER_PAGE_DATA,    carsPaginationDTO.getCarsPerPage());
    	data.put(PAG_NUM_DATA,          carsPaginationDTO.getPagNum());
    	
		return data;
	}
}
