package com.phistory.mvc.controller.cms;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;

@Component
@Slf4j
@RequestMapping(value = "/cms/cars")
public class CmsCarsController extends CmsBaseController {
	
	@Inject
	private CarsListModelFiller carsListModelFiller;
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	@Inject
	private CarEditModelFIller carEditModelFiller;
	
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
        catch (Exception ex)
        {
        	log.error(ex.toString(), ex);
        	model.addAttribute("exceptionMessage", ex.toString());
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }
}
