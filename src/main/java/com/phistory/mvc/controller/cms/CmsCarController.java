package com.phistory.mvc.controller.cms;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;

@Component
@Slf4j
@RequestMapping(value = "/cms/cars")
public class CmsCarController extends CmsBaseController {
	
	@Inject
	private CarsListModelFiller carsListModelFiller;
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	@Inject
	private CarEditModelFIller carEditModelFiller;
	@Inject
    private CarControllerUtil carControllerUtil;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleListCars(Model model,
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

    		return new ModelAndView(ERROR_VIEW_NAME);
    	}		
	}
	
	@RequestMapping(method = RequestMethod.POST,
		    		consumes = MediaType.APPLICATION_JSON,
		    		produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, Object> handlePagination(@RequestBody(required = true) CarsPaginationDto carsPaginationDto)
	{			
		return carControllerUtil.createPaginationData(carsPaginationDto);
	}
	
    @RequestMapping(value = EDIT_URL,
				    method = RequestMethod.GET)
    public ModelAndView handleNewCar(Model model)
    {
    	try
    	{
    		CarFormEditCommand carFormEditCommand = new CarFormEditCommand();
    		model.addAttribute(CAR_EDIT_FORM_COMMAND, carFormEditCommand);
    		carModelFiller.fillModel(model);
    		carEditModelFiller.fillCarEditModel(model, carFormEditCommand);
    		pictureModelFiller.fillModel(model);
    	}
        catch (Exception e)
        {
        	log.error(e.toString(), e);
        	model.addAttribute("exceptionMessage", e.toString());
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }
}
