package com.phistory.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;

/**
 * Controller to handle Index URLs
 * 
 * @author Gonzalo
 *
 */
@Controller
public class IndexController extends BaseController
{	
	private final static Logger logger = Logger.getLogger(IndexController.class);
	@Inject
	private ModelFiller pictureModelFiller;	
	@Inject
	private ModelFiller carModelFiller;	
	@Inject
	private Random previewPictureRandomGenerator;	
	
	@RequestMapping(value = {"/", INDEX_URL + HTML_SUFFIX},
					method = RequestMethod.GET)
	public ModelAndView handleDefault(Model model)
	{		
		try
		{		
			carModelFiller.fillModel(model);
			pictureModelFiller.fillModel(model);
			model.addAttribute("carNamesToPictureIds", generateRandomCarNamesToPictureIds());	
			
			return new ModelAndView(INDEX_URL); 
		}
		catch(Exception e)
		{
			logger.error(e.toString(), e);
			
			return new ModelAndView(ERROR);
		}
	}
	
	/**
	 * Generates a list of random car names to picture ids
	 * @return
	 */
	private Map<String, Long> generateRandomCarNamesToPictureIds()
	{		
		List<Long> picturesIds = getPictureDao().getIdsByCarId(null);	
		List<Long> randomPicIds = picturesIds;		
		Map<String, Long> carNamesToPictureIds = new HashMap<>();
		
		if (!picturesIds.isEmpty())
		{
			int upperIndexLimit = 9;
			
			if (picturesIds.size() > upperIndexLimit)
			{				
				randomPicIds = generateRadomPictureIdsList(picturesIds, upperIndexLimit);				
			}
		}
		
		for ( Long picId : randomPicIds )
		{
			Car car = getCarDao().getByPictureId(picId);
			carNamesToPictureIds.put(car.getFriendlyName(), picId);
		}
		
		return carNamesToPictureIds;
	}
	
	/**
	 * Generates a list of random picture ids out of all of the picture Ids in the database
	 * 
	 * @param pictureIds
	 * @param numOfIdsToSelect
	 * @return
	 */
	private List<Long> generateRadomPictureIdsList(List<Long> pictureIds, int numOfIdsToSelect)
	{
		List<Long> randomPictureIds = new ArrayList<>();
		
		Long lowestPictureId = pictureIds.get(0);
		Long highestPictureId = pictureIds.get(pictureIds.size() -1);			
		
		for (int i = 0; i < numOfIdsToSelect; i++)
		{			
			if (highestPictureId > 0)
			{	
				while (true)				
				{
					//the limit value is exclusive, so we can increase the number by 1
					Long randomId = new Long(previewPictureRandomGenerator.nextInt(highestPictureId.intValue() + 1));
					
					//the generator has a max value, but not a min, so we need to check first
					if (randomId >= lowestPictureId)
					{					
						if (pictureIds.contains(randomId) && !randomPictureIds.contains(randomId))					
						{
							randomPictureIds.add(Long.valueOf(randomId));
							break;
						}
						else
						{
							randomId = new Long(previewPictureRandomGenerator.nextInt(highestPictureId.intValue() + 1));
						}				
					}
					else
					{
						continue;
					}				
				}
			}
		}
		
		return randomPictureIds;
	}
}
