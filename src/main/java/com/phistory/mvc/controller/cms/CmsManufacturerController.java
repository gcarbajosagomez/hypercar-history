package com.phistory.mvc.controller.cms;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

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

    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_LIST_URL + HTML_SUFFIX,
				    method = RequestMethod.GET)
    public ModelAndView handleEditDefault(Model model,
			  							  @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
			  							  @RequestParam(defaultValue = "8", value = MANUFACTURERS_PER_PAGE, required = true) int manufacturersPerPage)
    {    	
    	ManufacturersPaginationDto manufacturersPaginationDto = new ManufacturersPaginationDto(pagNum, manufacturersPerPage);
    	
    	manufacturerModelFiller.fillPaginatedModel(model, manufacturersPaginationDto);
		pictureModelFiller.fillModel(model);
        
        return new ModelAndView();
    }
    
    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_LIST_URL + HTML_SUFFIX,
		    		method = RequestMethod.POST,
		    		consumes = MediaType.APPLICATION_JSON,
		    		produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> handlePagination(@RequestBody(required = true) ManufacturersPaginationDto manufacturersPaginationDto)
    {			
    	return manufacturerControllerUtil.createPaginationData(manufacturersPaginationDto);
    }

    @RequestMapping(value = CMS_CONTEXT + MANUFACTURER_EDIT_URL + HTML_SUFFIX,
					method = RequestMethod.GET)
    public ModelAndView handleListDefault(Model model)
    {
    	manufacturerModelFiller.fillModel(model);
		pictureModelFiller.fillModel(model);
    	
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
				manufacturerControllerUtil.deleteManufacturer(command);
			}
			catch (Exception ex)
			{
				model.addAttribute("exception", ex.toString());
			}
		}

		manufacturerModelFiller.fillModel(model);
		
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
}
