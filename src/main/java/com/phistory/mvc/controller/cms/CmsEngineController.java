package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.BaseControllerData.ENGINES_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.EngineForm;
import com.phistory.mvc.cms.form.creator.EngineFormCreator;
import com.phistory.mvc.controller.cms.util.EngineControllerUtil;
import com.tcp.data.model.engine.Engine;

/**
 *
 * @author Gonzalo
 */
@Controller
@Slf4j
@RequestMapping(value = {CMS_CONTEXT + ENGINES_URL, CMS_CONTEXT + ENGINES_URL + "/{" + ID + "}"})
public class CmsEngineController extends CmsBaseController
{
    private static final String ENGINE_EDIT_FORM_COMMAND = "EEFC";  
    @Inject
    private EngineFormCreator engineFormCreator;
    @Inject
    private EngineControllerUtil engineControllerUtil;

    @RequestMapping(method = POST,
    				produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EngineForm handleListEngineById(@ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command)
    {
        return command.getEngineForm();
    }
    
    @RequestMapping(value = {SAVE_URL, EDIT_URL},
    				method = {POST, PUT},
            		produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Model handleSaveOrEditEngine(Model model,
    									@Valid @RequestBody(required = true) EngineFormEditCommand command,
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
    			model.addAttribute(ENGINE, engine);
    		}		
    	}
    	catch (Exception e)
    	{
    		model.addAttribute("exceptionMessage", e.toString());
    	}

    	model.addAttribute(ENGINE, engine);
		
		return model;
    }
    
    @RequestMapping(value = DELETE_URL,
					method = POST)
    public void handleDeleteEngine(Model model,
								   BindingResult result,
								   @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command)
    {
    	if (!result.hasErrors())
    	{
    		try		
    		{
    			engineControllerUtil.deleteEngine(command);
    		}
    		catch (Exception e)
    		{
    			log.error(e.toString(), e);
    			model.addAttribute("exceptionMessage", e.toString());
    		}
    	}
    }

    @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND)
    public EngineFormEditCommand createEditFormCommand(@PathVariable(ID) Long engineId)
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
