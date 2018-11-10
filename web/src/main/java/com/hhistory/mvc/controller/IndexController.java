package com.hhistory.mvc.controller;

import com.google.common.collect.HashMultimap;
import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.dao.sql.SqlCarDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.hhistory.data.dao.inmemory.impl.InMemoryPictureDAOImpl.IN_MEMORY_PICTURE_DAO;
import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;
import static com.hhistory.mvc.controller.BaseControllerData.INDEX_URL;
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
        method = {GET, HEAD})
public class IndexController extends BaseController {

    private static final int MAX_NUMBER_PICTURES_TO_DISPLAY = 9;

    private ModelFiller carModelFiller;
    private Random      previewPictureRandomGenerator;
    private PictureDAO  pictureDAO;
    private SqlCarDAO   sqlCarDAO;

    @Inject
    public IndexController(ModelFiller carModelFiller,
                           Random previewPictureRandomGenerator,
                           @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO,
                           SqlCarDAO sqlCarDAO) {
        this.carModelFiller = carModelFiller;
        this.previewPictureRandomGenerator = previewPictureRandomGenerator;
        this.pictureDAO = pictureDAO;
        this.sqlCarDAO = sqlCarDAO;
    }

    @RequestMapping
    public ModelAndView handleDefault(Model model) {
        try {
            this.carModelFiller.fillModel(model);
            super.getManufacturerService()
                 .getInMemoryEntityFromModel(model)
                 .ifPresent(manufacturer -> model.addAttribute("carNamesToPictureIds",
                                                               this.generateRandomCarNamesToPictureIds(manufacturer)));

            return new ModelAndView(INDEX_VIEW_NAME);
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
    private Map<String, Collection<Long>> generateRandomCarNamesToPictureIds(Manufacturer manufacturer) {
        List<Long> randomPictureIds = this.pictureDAO.getAllPreviewIds(manufacturer.getId());
        HashMultimap<String, Long> carNamesToPictureIds = HashMultimap.create();
        Collections.shuffle(randomPictureIds);

        if (randomPictureIds.size() > MAX_NUMBER_PICTURES_TO_DISPLAY) {
            randomPictureIds = this.generateRandomPictureIdsList(randomPictureIds);
        }

        randomPictureIds.forEach(pictureId -> {
            Car car = sqlCarDAO.getCarByPictureId(pictureId);
            String pictureDescription = new StringBuilder().append(car.getManufacturer().toString())
                                                           .append(" ")
                                                           .append(car.getModel())
                                                           .toString();

            carNamesToPictureIds.put(pictureDescription, pictureId);
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
