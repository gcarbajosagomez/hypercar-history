package com.phistory.mvc.controller;

import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
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
import java.util.List;

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

    @RequestMapping("/" + PAGINATION_URL)
    @ResponseBody
    public PaginationDTO handlePagination(Model model, PaginationDTO paginationDTO) {
        model = this.inMemoryCarsListModelFiller.fillPaginatedModel(model, paginationDTO);

        paginationDTO.setItems((List<GenericEntity>) model.asMap().get(CARS));
        return paginationDTO;
    }
}
