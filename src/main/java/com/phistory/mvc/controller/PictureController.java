package com.phistory.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phistory.mvc.command.PictureLoadCommand;
import com.tcp.data.model.Picture;

/**
 * Controller to handle Picture URLs
 * 
 * @author Gonzalo
 *
 */
@Controller
public class PictureController extends BaseController
{			
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = PICTURE_URL + HTML_SUFFIX,
					method = RequestMethod.GET)
	public void handleDefault(HttpServletRequest request,
							  HttpServletResponse response,
							  @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command)
	{
		try 
		{
			Picture picture = loadPicture(command);
			printPictureToResponse(picture, response);
		} 
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	@ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION)
	public PictureLoadCommand createCommand(@RequestParam(value = ACTION, required = false) String action,
											@RequestParam(value = CAR_ID, required = false) Long carId,
											@RequestParam(value = PICTURE_ID, required = false) Long pictureId)
	{
		PictureLoadCommand command = new PictureLoadCommand();
		
		try
		{
			command = new PictureLoadCommand(action, pictureId, carId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}		
		
		return command;
	}

	/**
	 * Load a picture depending on the action being performed
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private Picture loadPicture(PictureLoadCommand command) throws Exception
	{
		switch (command.getAction())
		{
			case LOAD_CAR_PICTURE_ACTION:
			{
				if (command.getPictureId() != null)
				{
					return getPictureDao().getById(command.getPictureId());
				}

			}
			case LOAD_CAR_PREVIEW_ACTION:
			{
				if (command.getCarId() != null)
				{
					return getPictureDao().getCarPreview(command.getCarId());
				}
			}
		}
		
		return null;
	}

	/**
	 * Print the binary information of a Picture to a HTTP response
	 * 
	 * @param picture
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private HttpServletResponse printPictureToResponse(Picture picture, HttpServletResponse response) throws Exception
	{		
		if (picture != null)
		{
			response.setContentType(IMAGE_CONTENT_TYPE);

			int imgBytesLength = (int) picture.getImage().length();
			byte[] imgBytes = picture.getImage().getBytes(1, imgBytesLength);

			response.getOutputStream().write(imgBytes);
			response.getOutputStream().flush();
		}
		
		return response;
	}
}
