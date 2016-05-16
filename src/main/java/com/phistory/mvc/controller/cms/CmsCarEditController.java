package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;

/**
 *
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@Slf4j
@RequestMapping(value = CMS_CONTEXT + CARS + "/{" + ID + "}")
public class CmsCarEditController extends CmsBaseController
{
    @Inject
    private CarControllerUtil carControllerUtil;
    @Inject
    private CarFormCreator carFormCreator;
    @Inject
	private ModelFiller carModelFiller;
	@Inject
	private CarEditModelFIller carEditModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
    
    @RequestMapping(value = EDIT_URL,
		    		method = RequestMethod.GET)
    public ModelAndView handleEditCarDefault(Model model,
    										 @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command)
    {
    	try
    	{
    		carModelFiller.fillModel(model);
    		carEditModelFiller.fillCarEditModel(model, command);
    		pictureModelFiller.fillModel(model);
    	}
    	catch (Exception ex)
    	{
    		log.error(ex.toString(), ex);
    		model.addAttribute(EXCEPTION_MESSAGE, ex.toString());
    	}

    	return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
    				method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView handleEditCar(Model model,
    								  @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command,
    								  BindingResult result)
    {
    	try
    	{
    		if (!result.hasErrors())
    		{        	
        		Car car = carControllerUtil.saveOrEditCar(command);  
        		String successMessage = getMessageSource().getMessage("entityEditedSuccessfully",
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

	@RequestMapping(value = DELETE_URL,
					method = RequestMethod.DELETE)
	public ModelAndView handleDeleteCar(Model model,
										@ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command,
										BindingResult result)
	{
		if (!result.hasErrors())
		{
			try
			{
				carControllerUtil.deleteCar(command);
				String successMessage = getMessageSource().getMessage("entityDeletedSuccessfully",
						  											  new Object[]{command.getCarForm().getManufacturer().getFriendlyName() + command.getCarForm().getModel()},
						  											  Locale.getDefault());

				model.addAttribute(SUCCESS_MESSAGE, successMessage);
				model.addAttribute(CAR_EDIT_FORM_COMMAND, new CarFormEditCommand());
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
		}
		
		return new ModelAndView(CAR_EDIT_VIEW_NAME);
	}

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public CarFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long carId)
    {
        Car car = getCarDao().getById(carId);
        CarForm carForm = carFormCreator.createFormFromEntity(car);  
        CarFormEditCommand command = new CarFormEditCommand(carForm);
        
        return command;
    } 
}
