package com.phistory.mvc.controller.cms;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.form.ManufacturerForm;
import com.phistory.mvc.cms.form.creator.ManufacturerFormCreator;
import com.phistory.mvc.controller.cms.util.ManufacturerControllerUtil;
import com.phistory.mvc.springframework.view.ManufacturerModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.Manufacturer;

import static com.phistory.mvc.controller.BaseControllerData.*;
import static com.phistory.mvc.controller.cms.CmsBaseController.*;

/**
 *
 * @author Gonzalo
 */
@Controller
@Slf4j
@RequestMapping(value = CMS_CONTEXT + MANUFACTURERS + "{" + ID + "}")
public class CmsManufacturerEditController extends CmsBaseController
{
    @Inject
    private ManufacturerControllerUtil manufacturerControllerUtil;
    @Inject
    private ManufacturerFormCreator manufacturerFormCreator;
    @Inject
    private ManufacturerModelFiller manufacturerModelFiller;
    @Inject
	private ModelFiller pictureModelFiller;

    @RequestMapping(value = EDIT_URL,
    				method = RequestMethod.GET)
    public ModelAndView handleEditManufacturer(Model model, @PathVariable(ID) Long manufacturerId)
    {
    	manufacturerModelFiller.fillModel(model);
		pictureModelFiller.fillModel(model);
    	
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
    
    @RequestMapping(value = EDIT_URL,
					method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView handleEditManufacturer(Model model,
    										   @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
    										   BindingResult result)
    {
    	if (!result.hasErrors())
    	{    
    		try
    		{
    			Manufacturer manufacturer = manufacturerControllerUtil.saveOrEditManufacturer(command, model);
		
    			String successMessage = getMessageSource().getMessage("entityEditedSuccessfully",
				  											  		  new Object[]{manufacturer.getFriendlyName()},
				  											  		  Locale.getDefault());
    			model.addAttribute(SUCCESS_MESSAGE, successMessage);
    		}
    		catch (Exception e)
    		{
    			model.addAttribute(EXCEPTION_MESSAGE, e.toString());
    		}
    		finally
    		{
    			manufacturerModelFiller.fillModel(model);
    	    	pictureModelFiller.fillModel(model);
    		}
    	}
    	
    	return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}

	@RequestMapping(value = DELETE_URL,
					method = RequestMethod.DELETE)
	public ModelAndView handleDeleteManufacturer(Model model,
									    		 @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
									    		 BindingResult result)
	{
		if (command.getManufacturerForm() != null && command.getManufacturerForm().getId() != null)
		{
			try
			{
				manufacturerControllerUtil.deleteManufacturer(command);
				String successMessage = getMessageSource().getMessage("entityDeletedSuccessfully",
						  											  new Object[]{command.getManufacturerForm().getName()},
						  											  Locale.getDefault());

				model.addAttribute(SUCCESS_MESSAGE, successMessage);
				model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, new ManufacturerFormEditCommand());
			}
			catch (Exception e)
			{
				log.error(e.toString(), e);
				model.addAttribute(EXCEPTION_MESSAGE, e.toString());
			}
    		finally
    		{
    			manufacturerModelFiller.fillModel(model);
    	    	pictureModelFiller.fillModel(model);
    		}
		}
		
		return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}    

    @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND)
    public ManufacturerFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long manufacturerId)
    {
    	Manufacturer manufacturer = getManufacturerDao().getById(manufacturerId);
        ManufacturerForm manufacturerForm = new ManufacturerForm();
		try 
		{
			manufacturerForm = manufacturerFormCreator.createFormFromEntity(manufacturer);
		}
		catch (Exception e) {
			log.error(e.toString(), e);
		}
        ManufacturerFormEditCommand command = new ManufacturerFormEditCommand(manufacturerForm);
                
        return command;
    }
}
