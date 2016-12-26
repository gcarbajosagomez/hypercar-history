package com.phistory.mvc.controller;

import com.phistory.data.command.CarQueryCommand;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.controller.util.CarInternetContentUtils;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.CarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value = CARS_URL + "/" + "{" + BaseControllerData.CAR_MODEL_NAME + ":.+}",
				method = {GET, HEAD})
public class CarDetailsController extends BaseController
{
	private ModelFiller carModelFiller;
	private ModelFiller pictureModelFiller;
	private CarInternetContentUtils carInternetContentUtils;
	private InMemoryPictureDAO inMemoryInMemoryPictureDAO;

	@Inject
	public CarDetailsController(ModelFiller carModelFiller,
                                ModelFiller pictureModelFiller,
                                CarInternetContentUtils carInternetContentUtils,
                                InMemoryPictureDAO inMemoryInMemoryPictureDAO) {
		this.carModelFiller = carModelFiller;
		this.pictureModelFiller = pictureModelFiller;
		this.carInternetContentUtils = carInternetContentUtils;
		this.inMemoryInMemoryPictureDAO = inMemoryInMemoryPictureDAO;
	}
	
	@RequestMapping
	public ModelAndView handleCarDetails(Model model,
										 @ModelAttribute(CAR_QUERY_COMMAND) CarQueryCommand carQueryCommand,
							  		  	 @CookieValue(value = UNITS_OF_MEASURE_COOKIE_NAME,
							  		  	 			  defaultValue = UNITS_OF_MEASURE_METRIC,
							  		  	 			  required = false) String unitsOfMeasure)
	{
		try
		{
			Car car = super.getInMemoryCarDAO().getByQueryCommand(carQueryCommand);

			Long carId = car.getId();
			this.pictureModelFiller.fillModel(model);
			this.carModelFiller.fillModel(model);

			model.addAttribute(CAR,                 car);
			model.addAttribute(PICTURE_IDS,         this.inMemoryInMemoryPictureDAO.getIdsByCarId(carId));
			model.addAttribute(UNITS_OF_MEASURE,    unitsOfMeasure);

			List<CarInternetContent> videos = super.getInMemoryCarInternetContentDAO().getVideosByCarId(carId);
			model.addAttribute(YOUTUBE_VIDEO_IDS,                       this.carInternetContentUtils.extractYoutubeVideoIds(videos));
			model.addAttribute(CAR_INTERNET_CONTENT_REVIEW_ARTICLES,    super.getInMemoryCarInternetContentDAO().getReviewArticlesByCarId(carId));
			return new ModelAndView(CAR_DETAILS);
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
	
			return new ModelAndView(ERROR_VIEW_NAME);
		}
	}

	@ModelAttribute(CAR_QUERY_COMMAND)
	public CarQueryCommand getCarQueryCommand(@PathVariable(CAR_MODEL_NAME) String modelName,
											  @RequestParam(name = ENGINE_ID, required = false) Long engineId) {
		CarQueryCommand carQueryCommand = new CarQueryCommand();
		carQueryCommand.setModelName(modelName);
		carQueryCommand.setEngineId(engineId);
		return carQueryCommand;
	}
}