package com.phistory.mvc.controller.cms;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.form.ManufacturerForm;
import com.phistory.mvc.cms.form.creator.ManufacturerFormCreator;
import com.phistory.mvc.controller.cms.util.ManufacturerControllerUtil;
import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import com.phistory.mvc.springframework.view.ManufacturerModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.Manufacturer;

/**
 *
 * @author Gonzalo
 */
@Controller
@Slf4j
@RequestMapping(value = "/cms/manufacturers")
public class CmsManufacturerController extends CmsBaseController
{
    private final String MANUFACTURER_EDIT_FORM_COMMAND = "MEFC"; 
    @Inject
    private ManufacturerControllerUtil manufacturerControllerUtil;
    @Inject
    private ManufacturerFormCreator manufacturerFormCreator;
    @Inject
    private ManufacturerModelFiller manufacturerModelFiller;
    @Inject
	private ModelFiller pictureModelFiller;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleListManufacturers(Model model,
			  							  	   @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
			  							  	   @RequestParam(defaultValue = "8", value = MANUFACTURERS_PER_PAGE, required = true) int manufacturersPerPage)
    {    	
    	ManufacturersPaginationDto manufacturersPaginationDto = new ManufacturersPaginationDto(pagNum, manufacturersPerPage);
    	
    	manufacturerModelFiller.fillPaginatedModel(model, manufacturersPaginationDto);
		pictureModelFiller.fillModel(model);
        
        return new ModelAndView();
    }
    
    @RequestMapping(method = RequestMethod.POST,
		    		consumes = MediaType.APPLICATION_JSON,
		    		produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> handlePagination(@RequestBody(required = true) ManufacturersPaginationDto manufacturersPaginationDto)
    {			
    	return manufacturerControllerUtil.createPaginationData(manufacturersPaginationDto);
    }

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
    public ModelAndView handleSaveOrEditManufacturer(Model model,
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

        		model.addAttribute("successMessage", successMessage);
        	}
        	catch (Exception ex)
        	{
        		model.addAttribute("exception", ex.toString());
        	}
        }
        
        manufacturerModelFiller.fillModel(model);
        pictureModelFiller.fillModel(model);
        
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}

	@RequestMapping(value = DELETE_URL,
					method = RequestMethod.POST)
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
				model.addAttribute("exception", e.toString());
				manufacturerModelFiller.fillModel(model);
				
				return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
			}
		}
		
		return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
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
}
