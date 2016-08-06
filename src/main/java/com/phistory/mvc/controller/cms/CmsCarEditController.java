package com.phistory.mvc.controller.cms;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.form.creator.CarInternetContentFormCreator;
import com.phistory.mvc.cms.springframework.view.CarEditModelFiller;
import com.phistory.mvc.controller.cms.util.CMSCarControllerUtil;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;
import com.tcp.data.model.car.CarInternetContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
    private CMSCarControllerUtil carControllerUtil;
    @Inject
    private CarFormCreator carFormCreator;
    @Inject
    private CarInternetContentFormCreator carInternetContentFormCreator;
    @Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller carEditModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
    
    @RequestMapping(value = EDIT_URL,
		    		method = GET)
    public ModelAndView handleEditCarDefault(Model model,
    										 @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand)
    {
    	try
    	{
    		this.fillModel(model, carFormEditCommand);
    	}
    	catch (Exception ex)
    	{
    		log.error(ex.toString(), ex);
    		model.addAttribute(EXCEPTION_MESSAGE, ex.toString());
    	}

    	return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
    				method = {POST, PUT})
    public ModelAndView handleEditCar(Model model,
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

	@RequestMapping(value = DELETE_URL,
					method = DELETE)
	public ModelAndView handleDeleteCar(Model model,
										@ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand,
										BindingResult result)
	{
		if (!result.hasErrors())
		{
			try
			{
				carControllerUtil.deleteCar(carFormEditCommand);
				String successMessage = getMessageSource().getMessage("entityDeletedSuccessfully",
						  											  new Object[]{carFormEditCommand.getCarForm().getManufacturer().getFriendlyName() + " " +
																				   carFormEditCommand.getCarForm().getModel()},
						  											  LocaleContextHolder.getLocale());

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
				this.fillModel(model, carFormEditCommand);
			}
		}
		
		return new ModelAndView(CAR_EDIT_VIEW_NAME);
	}

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public CarFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long carId)
    {
        Car car = getCarDao().getById(carId);
        CarForm carForm = this.carFormCreator.createFormFromEntity(car);  
        CarFormEditCommand command = new CarFormEditCommand(carForm);
        
        return command;
    } 
    
    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditCommand createCarInternetContentEditFormCommand(@PathVariable(ID) Long carId) throws Exception
    {        
        List<CarInternetContent> carInternetContents = super.getCarInternetContentDAO().getByCarId(carId);
        List<CarInternetContentForm> carInternetContentForms = new ArrayList<>();
        carInternetContents.forEach(internetContent -> carInternetContentForms.add(this.carInternetContentFormCreator.createFormFromEntity(internetContent)));
                
        return new CarInternetContentEditCommand(carInternetContentForms);
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
