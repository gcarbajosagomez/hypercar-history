package com.phistory.mvc.controller.cms;

import com.phistory.data.model.car.Car;
import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.springframework.view.CarEditModelFiller;
import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.controller.cms.util.CMSCarControllerUtil;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Map;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.cms.CmsBaseController.*;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
	private ModelFiller carEditModelFiller;
	@Inject
    private CMSCarControllerUtil carControllerUtil;
	
	@RequestMapping(method = GET)
	public ModelAndView handleCarsList(Model model,
			   						   PaginationDTO PaginationDTO)
	{		
		return this.carController.handleCarsList(model, PaginationDTO);
	}
	
	@RequestMapping(value = {"/" + PAGINATION_URL},
		    		method = GET)
	@ResponseBody
	public void handlePagination(Model model, PaginationDTO PaginationDTO)
	{			
		this.carController.handlePagination(model, PaginationDTO);
	}	
	
    @RequestMapping(value = EDIT_URL,
				    method = GET)
    public ModelAndView handleNewCar(Model model, @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand)
    {
    	try
    	{
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
				    method = POST)
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
    			String successMessage = getMessageSource().getMessage(ENTITY_SAVED_SUCCESSFULLY_RESULT_MESSAGE,
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
    			String errorMessage = getMessageSource().getMessage(ENTITY_CONTAINED_ERRORS_RESULT_MESSAGE, null, LocaleContextHolder.getLocale());
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
        ((CarEditModelFiller) this.carEditModelFiller).fillCarEditModel(model, carFormEditCommand);
    	this.pictureModelFiller.fillModel(model);  
    }
}
