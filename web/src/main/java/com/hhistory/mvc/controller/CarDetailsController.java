package com.hhistory.mvc.controller;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.mvc.controller.util.CarInternetContentUtils;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.hhistory.data.dao.inmemory.impl.InMemoryPictureDAOImpl.IN_MEMORY_PICTURE_DAO;
import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;
import static com.hhistory.mvc.controller.BaseControllerData.CAR_URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Cars URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = CAR_URL + "/" + "{" + BaseControllerData.CAR_MODEL_NAME + ":.+}",
        method = {GET, HEAD})
public class CarDetailsController extends BaseController {

    private ModelFiller             carModelFiller;
    private CarInternetContentUtils carInternetContentUtils;
    private PictureDAO              pictureDAO;

    @Inject
    public CarDetailsController(ModelFiller carModelFiller,
                                CarInternetContentUtils carInternetContentUtils,
                                @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO) {
        this.carModelFiller = carModelFiller;
        this.carInternetContentUtils = carInternetContentUtils;
        this.pictureDAO = pictureDAO;
    }

    @RequestMapping
    public ModelAndView handleCarDetails(Model model,
                                         @ModelAttribute(CAR_QUERY_COMMAND) CarQueryCommand carQueryCommand,
                                         @CookieValue(value = UNITS_OF_MEASURE_COOKIE_NAME,
                                                 defaultValue = UNITS_OF_MEASURE_METRIC,
                                                 required = false) String unitsOfMeasure) {
        try {
            Car car = super.getInMemoryCarDAO().getByQueryCommand(carQueryCommand);

            Long carId = car.getId();
            this.carModelFiller.fillModel(model);

            model.addAttribute(CAR, car);
            model.addAttribute(PICTURE_IDS, this.pictureDAO.getIdsByCarId(carId));
            model.addAttribute(UNITS_OF_MEASURE, unitsOfMeasure);

            List<CarInternetContent> videos = super.getInMemoryCarInternetContentDAO().getVideosByCarId(carId);
            model.addAttribute(YOUTUBE_VIDEO_IDS, this.carInternetContentUtils.extractYoutubeVideoIds(videos));
            model.addAttribute(CAR_INTERNET_CONTENT_REVIEW_ARTICLES,
                               super.getInMemoryCarInternetContentDAO().getReviewArticlesByCarId(carId));

            return new ModelAndView(CAR_DETAILS);
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @ModelAttribute(CAR_QUERY_COMMAND)
    public CarQueryCommand getCarQueryCommand(@PathVariable(CAR_MODEL_NAME) String modelName,
                                              @RequestParam(name = ENGINE_ID, required = false) Long engineId) {
        try {
            modelName = URLDecoder.decode(modelName, UTF_8.name());
        } catch (UnsupportedEncodingException uee) {
            log.error("Cannot decode model name: {}",
                      modelName,
                      uee);
        }
        return CarQueryCommand.builder()
                              .modelName(modelName)
                              .engineId(engineId)
                              .build();
    }
}
