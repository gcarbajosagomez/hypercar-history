package com.phistory.mvc.controller;

import com.google.common.collect.HashMultimap;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.*;

import static com.phistory.mvc.controller.BaseControllerData.INDEX_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Index URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = {"/", INDEX_URL},
                method = HEAD)
public class IndexController extends BaseController {
    private static final int MAX_NUMBER_PICTURES_TO_DISPLAY = 9;

    @Inject
    private ModelFiller pictureModelFiller;
    @Inject
    private ModelFiller carModelFiller;
    @Inject
    private Random previewPictureRandomGenerator;

    @RequestMapping(method = GET)
    public ModelAndView handleDefault(Model model) {
        try {
            this.carModelFiller.fillModel(model);
            this.pictureModelFiller.fillModel(model);
            model.addAttribute("carNamesToPictureIds", this.generateRandomCarNamesToPictureIds());

            return new ModelAndView(INDEX_URL);
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    /**
     * Generates a list of random car names to picture Ids
     *
     * @return
     */
    private Map<String, Collection<Long>> generateRandomCarNamesToPictureIds() {
        List<Long> randomPictureIds = new ArrayList<>();
        HashMultimap<String, Long> carNamesToPictureIds = HashMultimap.create();

        List<Long> pictureIds = new ArrayList<>();
        pictureIds.addAll(super.getInMemoryPictureDAO().getAllIds());
        Collections.shuffle(pictureIds);

        if (pictureIds.size() > MAX_NUMBER_PICTURES_TO_DISPLAY) {
            randomPictureIds = this.generateRandomPictureIdsList(pictureIds);
        }

        randomPictureIds.forEach(pictureId ->
        {
            Car car = super.getInMemoryCarDAO().getCarByPictureId(pictureId);
            StringBuilder pictureDescription = new StringBuilder(car.getManufacturer().getFriendlyName())
                    .append(" ")
                    .append(car.getModel());

            carNamesToPictureIds.put(pictureDescription.toString(), pictureId);
        });


        return carNamesToPictureIds.asMap();
    }

    /**
     * Generates a {@link List} of random {@link Picture} Ids out of all of the {@link Picture} Ids in the database.
     *
     * @param pictureIds
     * @return A {@link List} of {@link IndexController#MAX_NUMBER_PICTURES_TO_DISPLAY} {@link Picture} Ids
     */
    private List<Long> generateRandomPictureIdsList(List<Long> pictureIds) {
        List<Long> randomPictureIds = new ArrayList<>();
        int randomNumberMaxBound = pictureIds.size();

        while (randomPictureIds.size() < MAX_NUMBER_PICTURES_TO_DISPLAY) {
            int randomPictureIndex = this.previewPictureRandomGenerator.nextInt(randomNumberMaxBound);
            randomPictureIds.add(pictureIds.get(randomPictureIndex));
        }

        return randomPictureIds;
    }
}
