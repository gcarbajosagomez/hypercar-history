package com.phistory.mvc.controller.cms;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
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

/**
 *
 * @author Gonzalo
 */
@Controller
@Slf4j
@RequestMapping(value = "/cms/manufacturers/{id}")
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
    public ModelAndView handleEditManufacturer(Model model)
    {
    	manufacturerModelFiller.fillModel(model);
		pictureModelFiller.fillModel(model);
    	
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
    
    @RequestMapping(value = EDIT_URL,
					method = RequestMethod.POST)
    public ModelAndView handleEditManufacturer(Model model,
    										   @Valid @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
    										   BindingResult result)
    {
        if (!result.hasErrors())
        {    
        	try
        	{
        		Manufacturer manufacturer = manufacturerControllerUtil.saveOrEditManufacturer(command, model);
        		
        		String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
						  											  new Object[]{manufacturer.getFriendlyName()},
						  											  Locale.getDefault());

        		model.addAttribute(SUCCESS_MESSAGE, successMessage);
        	}
        	catch (Exception e)
        	{
        		model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        	}
        }
        
        manufacturerModelFiller.fillModel(model);
        pictureModelFiller.fillModel(model);
        
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}

	@RequestMapping(value = DELETE_URL,
					method = RequestMethod.DELETE)
	public ModelAndView handleDeleteManufacturer(HttpServletResponse response,
												 Model model,
									    		 @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
									    		 BindingResult result) {
		if (!result.hasErrors())
		{
			try
			{
				manufacturerControllerUtil.deleteManufacturer(command);
				response.sendRedirect(EDIT_URL);
				
				return null;
			}
			catch (Exception e)
			{
				log.error(e.toString(), e);
				model.addAttribute(EXCEPTION_MESSAGE, e.toString());
				manufacturerModelFiller.fillModel(model);
				pictureModelFiller.fillModel(model);
				
				return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
			}
		}
		
		return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}    

    @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND)
    public ManufacturerFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long manufacturerId)
    {
    	ManufacturerFormEditCommand command = new ManufacturerFormEditCommand();
    	Manufacturer manufacturer = getManufacturerDao().getById(manufacturerId);
        ManufacturerForm manufacturerForm = manufacturerFormCreator.createFormFromEntity(manufacturer);
        command.setManufacturerForm(manufacturerForm);
                
        return command;
    }
}
