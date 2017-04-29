package com.phistory.mvc.controller;

import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle Cars URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = CARS_URL,
        method = {GET, HEAD})
public class CarListController extends BaseController {
    private CarControllerUtil          carControllerUtil;
    private AbstractCarListModelFiller inMemoryCarsListModelFiller;

    @Inject
    public CarListController(CarControllerUtil carControllerUtil,
                             AbstractCarListModelFiller inMemoryCarsListModelFiller) {
        this.carControllerUtil = carControllerUtil;
        this.inMemoryCarsListModelFiller = inMemoryCarsListModelFiller;
    }

    @RequestMapping
    public ModelAndView handleCarsList(Model model,
                                       PaginationDTO paginationDTO) {
        try {
            this.carControllerUtil.fillCarListModel(this.inMemoryCarsListModelFiller, model, paginationDTO);
            return new ModelAndView(CARS);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @RequestMapping(value = "/" + PAGINATION_URL)
    @ResponseBody
    public Map<String, Object> handlePagination(Model model, PaginationDTO paginationDTO) {
        this.inMemoryCarsListModelFiller.fillPaginatedModel(model, paginationDTO);

        Map<String, Object> modelMap = model.asMap();
        Map<String, Object> data = new HashMap<>();
        data.put(CARS, modelMap.get(CARS));
        data.put(CARS_PER_PAGE_DATA, modelMap.get(CARS_PER_PAGE_DATA));
        data.put(PAG_NUM_DATA, modelMap.get(PAG_NUM_DATA));

        //the model cannot be returned, or Spring would try to render the cars/pagination view
        return data;
    }
}
