package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CmsBaseController.MANUFACTURERS;
import static com.phistory.mvc.controller.cms.CmsBaseController.MANUFACTURERS_URL;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static javax.ws.rs.core.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.controller.cms.util.ManufacturerControllerUtil;
import com.phistory.mvc.model.dto.ManufacturersPaginationDto;
import com.phistory.mvc.springframework.view.ManufacturerModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.phistory.data.model.Manufacturer;

@Secured(USER_ROLE)
@Controller
@Slf4j
@RequestMapping(value = CMS_CONTEXT + MANUFACTURERS_URL)
public class CmsManufacturerController extends CmsBaseController
{
	@Inject
    private ManufacturerModelFiller manufacturerModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	@Inject
    private ManufacturerControllerUtil manufacturerControllerUtil;
	
	@RequestMapping(method = GET)
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
    
    @RequestMapping(method = POST,
		    		consumes = APPLICATION_JSON,
		    		produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> handlePagination(@RequestBody(required = true) ManufacturersPaginationDto manufacturersPaginationDto)
    {			
    	return manufacturerControllerUtil.createPaginationData(manufacturersPaginationDto);
    }
    
    @RequestMapping(value = EDIT_URL,
					method = GET)
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
			
			return new ModelAndView(ERROR_VIEW_NAME);
    	}
    	
    	return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
    
    @RequestMapping(value = SAVE_URL,
					method = POST)
    @ResponseBody
    public ModelAndView handleSaveNewManufacturer(Model model,
									   		   	  @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
									   		   	  BindingResult result)
    {
		log.info("saving manufacturer");
    	if (!result.hasErrors())
    	{    
    		try
    		{
				log.info("saving manufacturer_2");
    			Manufacturer manufacturer = manufacturerControllerUtil.saveOrEditManufacturer(command, model);
		
    			String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
				  											  		  new Object[]{manufacturer.getFriendlyName()},
				  											  		  LocaleContextHolder.getLocale());
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
}