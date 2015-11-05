package com.phistory.mvc.controller.cms;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.form.ManufacturerForm;
import com.phistory.mvc.cms.form.creator.ManufacturerFormCreator;
import com.tcp.data.model.Manufacturer;

/**
 *
 * @author Gonzalo
 */
@Controller
public class CmsManufacturerController extends CmsBaseController
{
    private final String MANUFACTURER_EDIT_FORM_COMMAND = "MEFC"; 
    @Inject
    private ManufacturerFormCreator manufacturerFormCreator;

    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_LIST_URL + HTML_SUFFIX,
				    method = RequestMethod.GET)
    public ModelAndView handleEditDefault(Model model,
			  							  @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
			  							  @RequestParam(defaultValue = "8", value = ITEMS_PER_PAGE, required = true) int itemsPerPage)
    {    	
    	model.addAttribute(ITEMS_PER_PAGE_DATA, itemsPerPage);
        fillModel(model);
        
        return new ModelAndView();
    }

    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_EDIT_URL + HTML_SUFFIX,
					method = RequestMethod.GET)
    public ModelAndView handleListDefault(Model model)
    {
        
        return new ModelAndView();
    }
    
    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_EDIT_URL + HTML_SUFFIX,
    			    method = RequestMethod.POST)
    public ModelAndView handleSaveOrEditManufacturer(Model model,
    											     @Valid @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
    											     BindingResult result)
    {
        if (!result.hasErrors())
        {    
        	try
        	{
        		saveOrEditManufacturer(command, model);
        	}
        	catch (Exception ex)
        	{
        		model.addAttribute("exception", ex.toString());
        	}
        }
        
        fillModel(model);
        
        return new ModelAndView();
	}

	@RequestMapping(value = CMS_CONTEXT + MANUFACTURER_DELETE_URL + HTML_SUFFIX,
					method = RequestMethod.POST)
	public ModelAndView handleDeleteCar(HttpServletRequest request,
									    Model model,
									    @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
									    BindingResult result) {
		if (!result.hasErrors())
		{
			try
			{
				deleteManufacturer(command);
			}
			catch (Exception ex)
			{
				model.addAttribute("exception", ex.toString());
			}
		}

		fillModel(model);
		
		return new ModelAndView("manufacturerEdit");
	}    

    @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND)
    public ManufacturerFormEditCommand createCarEditFormCommand(@RequestParam(value = MANUFACTURER_ID, required = false) Long manufacturerId)
    {
    	ManufacturerFormEditCommand command = new ManufacturerFormEditCommand();

        if (manufacturerId != null)
        {
            Manufacturer manufacturer = getManufacturerDao().getById(manufacturerId);
            ManufacturerForm manufacturerForm = manufacturerFormCreator.createFormFromEntity(manufacturer);
            command.setManufacturerForm(manufacturerForm);
        }
        
        return command;
    }
    
    private void saveOrEditManufacturer(ManufacturerFormEditCommand command, Model model)
    {
        if(command.getManufacturerForm() != null)
        {
            Manufacturer manufacturer = manufacturerFormCreator.createEntityFromForm(command.getManufacturerForm());
            getManufacturerDao().saveOrEdit(manufacturer);
            
            String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
					  											  new Object[]{command.getManufacturerForm().getName()},
					  											  Locale.getDefault());

            model.addAttribute("successMessage", successMessage);
        }
    }
    
    private void deleteManufacturer(ManufacturerFormEditCommand command)
    {
        if (command.getManufacturerForm() != null)
        {
            Manufacturer manufacturer = manufacturerFormCreator.createEntityFromForm(command.getManufacturerForm());            
            getManufacturerDao().delete(manufacturer);
        }
    }

    private void fillModel(Model model)
    {
        model.addAttribute("manufacturers", getManufacturerDao().getAll());
    }
}
