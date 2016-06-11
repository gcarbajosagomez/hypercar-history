package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.controller.cms.util.CMSCarControllerUtil;
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
    private CMSCarControllerUtil carControllerUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleCarsList(Model model,
			   						   CarsPaginationDto carsPaginationDto)
	{		
		return this.carController.handleCarsList(model, carsPaginationDto);
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
    		this.fillModel(model, carFormEditCommand);
    		
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
							  		  	 @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand,
							  		  	 BindingResult carFormEditCommandResult,
							  		  	 @Valid @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditCommand carInternetContentEditCommand,
							  		  	 BindingResult carInternetContentEditCommandResult)
    {
    	try
    	{
    		if (!carFormEditCommandResult.hasErrors())
    		{        	
    			Car car = this.carControllerUtil.saveOrEditCar(carFormEditCommand);  
    			String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
				  											  		  new Object[]{car.getFriendlyName()},
				  											  		  LocaleContextHolder.getLocale());
    			
    			if (!carInternetContentEditCommandResult.hasErrors())
    			{
    				carInternetContentEditCommand.getCarInternetContentForms().forEach(carInternetContentForm -> carInternetContentForm.setCar(car));
    				this.carControllerUtil.saveOrEditCarInternetContents(carInternetContentEditCommand);
    			}
    			model.addAttribute(SUCCESS_MESSAGE, successMessage);
    		}
    		else
    		{
    			String errorMessage = getMessageSource().getMessage("entityContainedErrors", null, LocaleContextHolder.getLocale());     
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
    		this.fillModel(model, carFormEditCommand);
    	}
		
		return new ModelAndView(CAR_EDIT_VIEW_NAME);     	
    }
    
    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditCommand createCarInternetContentEditFormCommand()
    { 
    	return new CarInternetContentEditCommand();
    } 
    
    /**
     * Fill the supplied {@link Model}
     * 
     * @param model
     * @param carFormEditCommand
     */
    private void fillModel(Model model, CarFormEditCommand carFormEditCommand)
    {
    	this.carModelFiller.fillModel(model);
    	this.carEditModelFiller.fillCarEditModel(model, carFormEditCommand);
    	this.pictureModelFiller.fillModel(model);  
    }
}
