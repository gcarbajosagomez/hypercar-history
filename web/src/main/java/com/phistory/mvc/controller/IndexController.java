package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.*;

import javax.inject.Inject;

import com.google.common.collect.HashMultimap;
import com.phistory.data.model.Picture;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.springframework.view.ModelFiller;
import com.phistory.data.model.car.Car;

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
public class IndexController extends BaseController
{
    private static final int HOURS_TO_LOAD_PICTURE_IDS_AFTER = 2;
    private static final int MAX_NUMBER_PICTURES_TO_DISPLAY = 9;

    @Inject
	private ModelFiller pictureModelFiller;	
	@Inject
	private ModelFiller carModelFiller;	
	@Inject
	private Random previewPictureRandomGenerator;
	@Inject
	private InMemoryEntityStorage inMemoryEntityStorage;
	private List<Long> pictureIds = new ArrayList<>();
	private DateTime pictureIdsLoadingTime = DateTime.now().withMillisOfDay(0);
	
	@RequestMapping(method = GET)
	public ModelAndView handleDefault(Model model)
	{
		try
		{
            if (this.mustLoadPictureIds()) {
                this.loadPictureIds();
            }

            this.carModelFiller.fillModel(model);
            this.pictureModelFiller.fillModel(model);
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
	private Map<String, Collection<Long>> generateRandomCarNamesToPictureIds()
	{	
		List<Long> randomPictureIds = new ArrayList<>();
        HashMultimap<String, Long> carNamesToPictureIds = HashMultimap.create();
		
		if (!this.pictureIds.isEmpty())
		{
			if (this.pictureIds.size() > MAX_NUMBER_PICTURES_TO_DISPLAY)
			{				
				randomPictureIds = this.generateRandomPictureIdsList(this.pictureIds);
			}

            randomPictureIds.forEach(pictureId ->
            {
                Car car = this.inMemoryEntityStorage.getCarByPictureId(pictureId);
                StringBuilder pictureDescription = new StringBuilder(car.getManufacturer().getFriendlyName())
                        							.append(" ")
                        							.append(car.getModel());

                carNamesToPictureIds.put(pictureDescription.toString(), pictureId);
            });
		}
		
		return carNamesToPictureIds.asMap();
	}

    /**
     * Load all the {@link Car} {@link Picture} Ids there are on the DB
     */
    private void loadPictureIds() {
        this.inMemoryEntityStorage.loadPictures();;
        this.pictureIds = this.inMemoryEntityStorage.getAllPictureIds();
        Collections.shuffle(this.pictureIds);
    }

    /**
     * Calculate whether or not the {@link List} of picture Ids must be loaded from the DB
     *
     * @return true if it must be loaded, false otherwise
     */
    private boolean mustLoadPictureIds()
    {
        DateTime now = DateTime.now();
        if (this.pictureIdsLoadingTime.plusHours(HOURS_TO_LOAD_PICTURE_IDS_AFTER).isBefore(now.toInstant())) {
			this.pictureIdsLoadingTime = now;
            return true;
        }
        return false;
    }
	
	/**
	 * Generates a {@link List} of random {@link Picture} Ids out of all of the {@link Picture} Ids in the database.
	 * 
	 * @param pictureIds
	 * @return A {@link List} of {@link IndexController#MAX_NUMBER_PICTURES_TO_DISPLAY} {@link Picture} Ids
	 */
	private List<Long> generateRandomPictureIdsList(List<Long> pictureIds)
	{
		List<Long> randomPictureIds = new ArrayList<>();
		int randomNumberMaxBound = pictureIds.size();
		
		while(randomPictureIds.size() < MAX_NUMBER_PICTURES_TO_DISPLAY)
		{	
			int randomPictureIndex = this.previewPictureRandomGenerator.nextInt(randomNumberMaxBound);
			randomPictureIds.add(pictureIds.get(randomPictureIndex));
		}
		
		return randomPictureIds;
	}
}
