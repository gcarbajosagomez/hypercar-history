package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;

@Secured(value = USER_ROLE)
@Controller
@RequestMapping(value = CMS_CONTEXT + CARS)
@Slf4j
public class CmsCarController extends CmsBaseController
{
	@Inject
    private CarController carController;
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	@Inject
	private CarEditModelFIller carEditModelFiller;
	@Inject
    private CarControllerUtil carControllerUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleCarsList(Model model,
									   @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
									   @RequestParam(defaultValue = "8", value = CARS_PER_PAGE, required = true) int carsPerPage)
	{		
		return this.carController.handleCarsList(model, pagNum, carsPerPage);
	}
	
	@RequestMapping(value = {"/" + PAGINATION_URL},
		    		method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> handlePagination(CarsPaginationDto carsPaginationDto)
	{			
		return this.carController.handlePagination(carsPaginationDto);
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
    		
    		return new ModelAndView(CAR_EDIT_VIEW_NAME);
    	}
        catch (Exception e)
        {
        	log.error(e.toString(), e);
        	model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        	
        	return new ModelAndView(ERROR_VIEW_NAME);
        }      
    }
    
    @RequestMapping(value = SAVE_URL,
				    method = RequestMethod.POST)
    public ModelAndView handleSaveNewCar(Model model,
							  		  	 @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command,
							  		  	 BindingResult result)
    {
    	try
    	{
    		if (!result.hasErrors())
    		{        	
    			Car car = carControllerUtil.saveOrEditCar(command);  
    			String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
				  											  		  new Object[]{car.getFriendlyName()},
				  											  		  Locale.getDefault());     
    			model.addAttribute(SUCCESS_MESSAGE, successMessage);
    		}
    		else
    		{
    			String errorMessage = getMessageSource().getMessage("entityContainedErrors", null, Locale.getDefault());     
    			model.addAttribute(EXCEPTION_MESSAGE, errorMessage);
    		}		
    	}
    	catch (Exception e)
    	{
    		log.error(e.toString(), e);
    		model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        }
    	finally
    	{
    		carModelFiller.fillModel(model);
    		carEditModelFiller.fillCarEditModel(model, command);
    		pictureModelFiller.fillModel(model);  
    	}
		
		return new ModelAndView(CAR_EDIT_VIEW_NAME);     	
    }
}
