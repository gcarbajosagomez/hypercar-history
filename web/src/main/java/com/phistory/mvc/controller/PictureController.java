package com.phistory.mvc.controller;

import com.phistory.data.model.Picture;
import com.phistory.mvc.command.PictureLoadAction;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.controller.util.PictureControllerUtil;
import com.phistory.mvc.propertyEditor.PictureLoadActionPropertyEditor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;
import static com.phistory.mvc.controller.BaseControllerData.PICTURE_LOAD_ACTION_ACTION;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Picture URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = PICTURES_URL + "/{" + PICTURE_LOAD_ACTION_ACTION + "}",
			    method = HEAD)
public class PictureController extends BaseController
{
    private static final int MINUTES_TO_LOAD_PICTURES_AFTER = 10;

	@Inject
    private PictureControllerUtil pictureControllerUtil;
	@Inject
    private InMemoryEntityStorage inMemoryEntityStorage;
	private DateTime picturesLoadingTime = DateTime.now().withMillisOfDay(0);

	
	@RequestMapping(method = GET)
	public void handleDefault(HttpServletResponse response,
							  @ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION) PictureLoadCommand command)
	{
		try 
		{
            if (this.mustLoadPictures()) {
                this.inMemoryEntityStorage.loadPictures();
            }

			Picture picture = this.inMemoryEntityStorage.loadPicture(command);
            //the car has been just added and its pictures have not been cached yet or a picture has been added and needs to be cached
            if (picture == null) {
                this.inMemoryEntityStorage.loadPictures();
                picture = this.inMemoryEntityStorage.loadPicture(command);
            }
			this.pictureControllerUtil.printPictureToResponse(picture, response);
		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
    }

	@ModelAttribute(value = PICTURE_LOAD_COMMAND_ACTION)
	public PictureLoadCommand createCommand(@PathVariable(PICTURE_LOAD_ACTION_ACTION) PictureLoadAction 	loadAction,
											@RequestParam(value = CAR_ID, required = false) Long 			carId,
											@RequestParam(value = MANUFACTURER_ID, required = false) Long 	manufacturerId,
											@RequestParam(value = PICTURE_ID, required = false) Long 		pictureId)
	{
		PictureLoadCommand command = new PictureLoadCommand();
		
		try
		{
			command = new PictureLoadCommand(loadAction, pictureId, manufacturerId, carId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}		
		
		return command;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(PictureLoadAction.class, new PictureLoadActionPropertyEditor());
	}

	/**
	 * Calculate whether or not the {@link List} of {@link Picture}s must be loaded from the DB
	 *
	 * @return true if it must be loaded, false otherwise
	 */
	private boolean mustLoadPictures()
	{
		DateTime now = DateTime.now();
		if (this.picturesLoadingTime.plusMinutes(MINUTES_TO_LOAD_PICTURES_AFTER).isBefore(now.toInstant())) {
            this.picturesLoadingTime = now;
			return true;
		}
		return false;
	}
}
