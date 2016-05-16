package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;

/**
 * Controller to handle Index URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = {"/", INDEX_URL},
				method = HEAD)
public class IndexController extends BaseController implements InitializingBean
{	
	@Inject
	private ModelFiller pictureModelFiller;	
	@Inject
	private ModelFiller carModelFiller;	
	@Inject
	private Random previewPictureRandomGenerator;	
	private List<Long> picturesIds = new ArrayList<>();
	
	@RequestMapping(method = GET)
	public ModelAndView handleDefault(Model model)
	{		
		try
		{		
			carModelFiller.fillModel(model);
			pictureModelFiller.fillModel(model);
			model.addAttribute("carNamesToPictureIds", this.generateRandomCarNamesToPictureIds());	
			
			return new ModelAndView(INDEX_URL); 
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR_VIEW_NAME);
		}
	}
	
	/**
	 * Generates a list of random car names to picture Ids
	 * @return
	 */
	private Map<String, Long> generateRandomCarNamesToPictureIds()
	{	
		List<Long> randomPicIds = this.picturesIds;		
		Map<String, Long> carNamesToPictureIds = new HashMap<>();
		
		if (!this.picturesIds.isEmpty())
		{
			int upperIndexLimit = 9;
			
			if (this.picturesIds.size() > upperIndexLimit)
			{				
				randomPicIds = this.generateRadomPictureIdsList(this.picturesIds, upperIndexLimit);				
			}
		}
		
		randomPicIds.forEach(pictureId ->
		{
			Car car = getCarDao().getByPictureId(pictureId);
			StringBuilder pictureDescription = new StringBuilder(car.getManufacturer().getFriendlyName()).append(" ")
														 .append(car.getModel());
			
			carNamesToPictureIds.put(pictureDescription.toString(), pictureId);
		});
		
		return carNamesToPictureIds;
	}
	
	/**
	 * Generates a list of random picture Ids out of all of the Picture Ids in the database
	 * 
	 * @param pictureIds
	 * @param numOfIdsToSelect
	 * @return
	 */
	private List<Long> generateRadomPictureIdsList(List<Long> pictureIds, int numOfIdsToSelect)
	{
		List<Long> randomPictureIds = new ArrayList<>();
		int randomNumberMaxBound = pictureIds.size();
		
		for (int i = 0; i < numOfIdsToSelect; i++)
		{	
			int randomPictureIndex = previewPictureRandomGenerator.nextInt(randomNumberMaxBound);
			randomPictureIds.add(pictureIds.get(randomPictureIndex));
		}
		
		return randomPictureIds;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		this.picturesIds = super.getPictureDao().getIdsByCarId(null);
		Collections.shuffle(this.picturesIds);
	}
}
