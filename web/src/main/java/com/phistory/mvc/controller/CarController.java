package com.phistory.mvc.controller;

import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.controller.util.CarInternetContentUtils;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.CarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
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
import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
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
	private CarControllerUtil carControllerUtil;
	private ModelFiller carModelFiller;
	private CarListModelFiller inMemoryCarsListModelFiller;
	private ModelFiller pictureModelFiller;
	private CarInternetContentUtils carInternetContentUtils;
	private InMemoryPictureDAO inMemoryInMemoryPictureDAO;

	@Inject
	public CarController(CarControllerUtil carControllerUtil,
						 ModelFiller carModelFiller,
						 CarListModelFiller inMemoryCarsListModelFiller,
						 ModelFiller pictureModelFiller,
						 CarInternetContentUtils carInternetContentUtils,
						 InMemoryPictureDAO inMemoryInMemoryPictureDAO) {
		this.carControllerUtil = carControllerUtil;
		this.carModelFiller = carModelFiller;
		this.inMemoryCarsListModelFiller = inMemoryCarsListModelFiller;
		this.pictureModelFiller = pictureModelFiller;
		this.carInternetContentUtils = carInternetContentUtils;
		this.inMemoryInMemoryPictureDAO = inMemoryInMemoryPictureDAO;
	}

	@RequestMapping
	public ModelAndView handleCarsList(Model model,
									   PaginationDTO paginationDTO)
	{		
		try
		{
            this.carControllerUtil.fillCarListModel(this.inMemoryCarsListModelFiller, model, paginationDTO);
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

			model.addAttribute(CAR,                 super.getInMemoryCarDAO().getById(carId));
			model.addAttribute(PICTURE_IDS,         this.inMemoryInMemoryPictureDAO.getPictureIdsByCarId(carId));
			model.addAttribute(UNITS_OF_MEASURE,    unitsOfMeasure);

            List<CarInternetContent> carInternetContents = super.getInMemoryCarInternetContentDAO().getByCarId(carId);
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
	public Map<String, Object> handlePagination(Model model, PaginationDTO paginationDTO)
	{
        this.inMemoryCarsListModelFiller.fillPaginatedModel(model, paginationDTO);

		Map<String, Object> modelMap = model.asMap();
		Map<String, Object> data = new HashMap<>();
    	data.put(CARS,                  modelMap.get(CARS));
    	data.put(CARS_PER_PAGE_DATA,    modelMap.get(CARS_PER_PAGE_DATA));
    	data.put(PAG_NUM_DATA,          modelMap.get(PAG_NUM_DATA));

        //the model cannot be returned, because Spring tries to render the cars/pagination view otherwise
		return data;
	}
}
