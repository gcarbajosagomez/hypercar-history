package com.phistory.mvc.controller.cms;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phistory.mvc.cms.command.PictureLoadCommand;
import com.tcp.data.model.Picture;

/**
 *
 * @author Gonzalo
 */
@Controller
public class CmsPictureController extends CmsBaseController
{    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping(value = CMS_CONTEXT + PICTURE_DELETE_URL + HTML_SUFFIX,
    				method = RequestMethod.POST)
    @ResponseBody
    public String handleDeletePicture(Model model, 
    						   		  HttpServletResponse response,
    						   		  HttpServletRequest request,
    						   		  @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command,
    						   		  @RequestParam(value = DELETE_PREVIEW_PICTURE, required = true) boolean deletePreviewPicture) throws JsonProcessingException
    {
    	Map<String, Object> resultMap = new HashMap<>();
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	String jsonResultString = "";
    	
        try
        {
            Picture picture = loadPicture(command);
            getPictureDao().delete(picture);
            
            String successMessage = getMessageSource().getMessage("entityDeletedSuccessfully",
					  											  new Object[]{deletePreviewPicture ? "Preview picture" : "Picture"},
					  											  Locale.getDefault());           
            
            resultMap.put("result", "success");  
            resultMap.put("message", successMessage);  
        } 
        catch (Exception e)
        {        	
            logger.error(e.getMessage(), e);
            
            resultMap.put("result", "fail");  
            resultMap.put("message", e.getStackTrace().toString());
        }      
        
        if (!deletePreviewPicture)
        {
        	resultMap.put("pictureIds", getPictureDao().getIdsByCarId(command.getCarId()));
        }
        	
        jsonResultString = objectMapper.writeValueAsString(resultMap);
        
        return jsonResultString;
    }

    @ModelAttribute(value = CMS_CONTEXT + PICTURE_LOAD_COMMAND_ACTION)
    public PictureLoadCommand createCommand(@RequestParam(value = ACTION, required = false) String action,
    										@RequestParam(value = CAR_ID, required = false) Long carId,
            								@RequestParam(value = MANUFACTURER_ID, required = false) Long manufacturerId,
            								@RequestParam(value = PICTURE_ID, required = false) Long pictureId)
    {
        PictureLoadCommand command = new PictureLoadCommand(action,
        												    pictureId,
        												    carId,
        												    manufacturerId);
        
        return command;
    }

    private Picture loadPicture(PictureLoadCommand command)
    {
    	if(command != null && command.getAction() != null)
    	{
    		switch (command.getAction())
    		{
            	case LOAD_CAR_PICTURE_ACTION:
            	{
            		if (command.getPictureId() != null) {
            			return getPictureDao().getById(command.getPictureId());
            		}

            	}
            	case LOAD_CAR_PREVIEW_ACTION:
            	{
            		if (command.getCarId() != null) {
            			return getPictureDao().getCarPreview(command.getCarId());
            		}
            	}
            	case LOAD_MANUFACTURER_LOGO_ACTION:
            	{
            		if (command.getManufacturerId() != null) {
            			return getPictureDao().getManufacturerLogo(command.getManufacturerId());
            		}
            	}
            	default:
            	{
            		if (command.getPictureId() != null) {
            			return getPictureDao().getById(command.getPictureId());
            		}
            	}
    		}
    	}
        
        return null;
    }
}
