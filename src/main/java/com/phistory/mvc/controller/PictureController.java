package com.phistory.mvc.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.cms.util.PictureControllerUtil;
import com.tcp.data.model.Picture;

import static com.phistory.mvc.controller.BaseControllerData.*;

/**
 * Controller to handle Picture URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = PICTURES_URL + "/{" + ACTION + "}")
public class PictureController extends BaseController
{	
	@Inject
    private PictureControllerUtil pictureControllerUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public void handleDefault(HttpServletRequest request,
							  HttpServletResponse response,
							  @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command)
	{
		try 
		{
			Picture picture = pictureControllerUtil.loadPicture(command);
			pictureControllerUtil.printPictureToResponse(picture, response);
		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	@ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION)
	public PictureLoadCommand createCommand(@PathVariable(ACTION) String action,
											@RequestParam(value = CAR_ID, required = false) Long carId,
											@RequestParam(value = MANUFACTURER_ID, required = false) Long manufacturerId,
											@RequestParam(value = PICTURE_ID, required = false) Long pictureId)
	{
		PictureLoadCommand command = new PictureLoadCommand();
		
		try
		{
			command = new PictureLoadCommand(action, pictureId, manufacturerId, carId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}		
		
		return command;
	}
}
