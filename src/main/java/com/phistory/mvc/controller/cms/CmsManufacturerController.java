package com.phistory.mvc.controller.cms;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
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
import com.phistory.mvc.controller.cms.util.ManufacturerControllerUtil;
import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import com.phistory.mvc.springframework.view.ManufacturerModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.Manufacturer;

@Controller
@Slf4j
@RequestMapping(value = "/cms/manufacturers")
public class CmsManufacturerController extends CmsBaseController {
	
	@Inject
    private ManufacturerModelFiller manufacturerModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	@Inject
    private ManufacturerControllerUtil manufacturerControllerUtil;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleListManufacturers(Model model,
			  							  	   @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
			  							  	   @RequestParam(defaultValue = "8", value = MANUFACTURERS_PER_PAGE, required = true) int manufacturersPerPage)
    {    
		try
    	{
			ManufacturersPaginationDto manufacturersPaginationDto = new ManufacturersPaginationDto(pagNum, manufacturersPerPage);
    	
			manufacturerModelFiller.fillPaginatedModel(model, manufacturersPaginationDto);
			pictureModelFiller.fillModel(model);
			
			return new ModelAndView();
    	}
		catch(Exception e)
		{
			log.error(e.toString(), e);

			return new ModelAndView(ERROR_VIEW_NAME);
		}	
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
    public ModelAndView handleNewManufacturer(Model model)
    {
    	try
    	{
    		ManufacturerFormEditCommand manufacturerFormEditCommand = new ManufacturerFormEditCommand();
    		model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, manufacturerFormEditCommand);
    		manufacturerModelFiller.fillModel(model);
    		pictureModelFiller.fillModel(model);
    	}
    	catch (Exception e)
    	{
    		log.error(e.toString(), e);
    		model.addAttribute(EXCEPTION_MESSAGE, e.toString());
    	}
    		return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
    
    @RequestMapping(value = EDIT_URL,
					method = RequestMethod.POST)
    public ModelAndView handleSaveNewManufacturer(Model model,
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
}
