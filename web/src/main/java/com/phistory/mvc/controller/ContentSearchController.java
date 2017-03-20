package com.phistory.mvc.controller;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import com.phistory.mvc.model.dto.ContentSearchDTO;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.phistory.data.query.command.SimpleDataConditionCommand.EntityConditionType.*;
import static com.phistory.mvc.controller.BaseControllerData.MODELS_SEARCH_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle ContentSearch URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = MODELS_SEARCH_URL,
        method = HEAD)
public class ContentSearchController extends BaseController {
    private ModelFiller carModelFiller;
    private ModelFiller pictureModelFiller;

    @Inject
    public ContentSearchController(ModelFiller carModelFiller, ModelFiller pictureModelFiller) {
        this.carModelFiller = carModelFiller;
        this.pictureModelFiller = pictureModelFiller;
    }

    @RequestMapping(method = GET)
    @ResponseBody
    public ModelAndView handleModelsSearch(Model model,
                                           ContentSearchDTO contentSearchDto) {
        try {
            ContentSearchDTO clonedContentSearchDto = contentSearchDto.clone();
            //so that we can return all the searched car model names to the view, we need to retrieve all cars, and separate them into models (names) and cars
            clonedContentSearchDto.setItemsPerPage(0);
            SearchCommand searchCommand = this.createSearchCommand(clonedContentSearchDto);
            List<GenericEntity> searchResults = this.getSqlContentSearchDAO().searchContent(searchCommand);
            searchResults = this.removeNotVisibleCars(searchResults);

            model.addAttribute(CARS, this.extractModelsListFromSearchResults(searchResults, contentSearchDto));
            model.addAttribute(MODELS, searchResults);
            model.addAttribute(CARS_PER_PAGE_DATA, contentSearchDto.getItemsPerPage());
            model.addAttribute(PAG_NUM_DATA, contentSearchDto.getPagNum());
            model.addAttribute(SEARCH_TOTAL_RESULTS_DATA, searchResults.size());
            model.addAttribute(CONTENT_TO_SEARCH_DATA, contentSearchDto.getContentToSearch());
            this.carModelFiller.fillModel(model);
            this.pictureModelFiller.fillModel(model);

            return new ModelAndView(CARS);
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    /**
     * Create a search command to search for content
     *
     * @param contentSearchDto
     * @return
     */
    private SearchCommand createSearchCommand(ContentSearchDTO contentSearchDto) {
        Map<String, Boolean> orderByMap = new HashMap<>();
        orderByMap.put(Car.PRODUCTION_START_DATE_PROPERTY_NAME, Boolean.TRUE);
        //TODO
        //orderByMap.put(Car.MODEL_PROPERTY_NAME, Boolean.TRUE);

        SimpleDataConditionCommand contentToSearchCondition =
                new SimpleDataConditionCommand(LIKE,
                                               new Object[] {contentSearchDto.getContentToSearch()});

        Map<String, SimpleDataConditionCommand> dataConditionMap = new HashMap<>();
        dataConditionMap.put(Car.MODEL_PROPERTY_NAME, contentToSearchCondition);

        return new SearchCommand(Car.class,
                                 dataConditionMap,
                                 null,
                                 orderByMap,
                                 Collections.EMPTY_LIST,
                                 contentSearchDto.getFirstResult(),
                                 contentSearchDto.getItemsPerPage());
    }

    private List<GenericEntity> removeNotVisibleCars(List<GenericEntity> cars) {
        return cars.stream()
                   .filter(car -> ((Car) car).getVisible())
                   .collect(Collectors.toList());
    }

    private List<GenericEntity> extractModelsListFromSearchResults(List<GenericEntity> searchResults,
                                                                   ContentSearchDTO contentSearchDto) {
        int fromIndex = contentSearchDto.getFirstResult();
        int toIndex = fromIndex + contentSearchDto.getItemsPerPage();

        if (toIndex > searchResults.size()) {
            toIndex = searchResults.size();
        }
        return searchResults.subList(fromIndex, toIndex);
    }
}
