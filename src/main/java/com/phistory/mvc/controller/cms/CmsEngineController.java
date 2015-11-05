package com.phistory.mvc.controller.cms;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.EngineForm;
import com.phistory.mvc.cms.form.creator.EngineFormCreator;
import com.tcp.data.model.engine.Engine;

/**
 *
 * @author Gonzalo
 */
@Controller
public class CmsEngineController extends CmsBaseController
{
    private static final String ENGINE_EDIT_FORM_COMMAND = "EEFC";  
    @Inject
    private EngineFormCreator engineFormCreator;

    @RequestMapping(value = CMS_CONTEXT + ENGINE_CONTENT_LIST_URL + HTML_SUFFIX,
    				method = RequestMethod.POST,
    				produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EngineForm handleListEngineById(@ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command)
    {
        return command.getEngineForm();
    }
    
    @RequestMapping(value = CMS_CONTEXT + ENGINE_EDIT_URL + HTML_SUFFIX,
            		method = RequestMethod.POST,
            		produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Engine handleSaveOrEditEngine(HttpServletRequest request,
										 Model model,
										 @RequestBody(required = true) EngineFormEditCommand command,
										 BindingResult result)
    {
    	
    	Engine engine = engineFormCreator.createEntityFromForm(command.getEngineForm());
    	
    	try
    	{    		
    		if (!result.hasErrors())
    		{ 			
    			getEngineDao().saveOrEdit(engine);
		
    			String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
    															      new Object[]{engine.getFriendlyName()},
    															      Locale.getDefault());     
    			model.addAttribute("successMessage", successMessage);
    			
    			return engine;
    		}		
    	}
    	catch (Exception ex)
    	{
    		model.addAttribute("exceptionMessage", ex.toString());
    	}

    	return engine;
    }

    @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND)
    public EngineFormEditCommand createEditFormCommand(@RequestParam(value = ENGINE_ID, required = false) Long engineId)
    {
        EngineFormEditCommand command = new EngineFormEditCommand();

        if (engineId != null)
        {
            Engine engine = getEngineDao().getById(engineId);
            EngineForm engineForm = engineFormCreator.createFormFromEntity(engine);
            command.setEngineForm(engineForm);
        }

        return command;
    }
}
