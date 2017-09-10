package com.hhistory.mvc.controller;

import com.hhistory.mvc.dto.CarPaginationDTO;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.service.CarPaginationService;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;

import static com.hhistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.hhistory.mvc.springframework.view.filler.inmemory.InMemoryCarListModelFiller.IN_MEMORY_CAR_LIST_MODEL_FILLER;
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

    private AbstractCarListModelFiller inMemoryCarListModelFiller;
    private CarPaginationService       carPaginationService;

    @Inject
    public CarListController(@Named(IN_MEMORY_CAR_LIST_MODEL_FILLER) AbstractCarListModelFiller inMemoryCarListModelFiller,
                             CarPaginationService carPaginationService) {
        this.inMemoryCarListModelFiller = inMemoryCarListModelFiller;
        this.carPaginationService = carPaginationService;
    }

    @RequestMapping
    public ModelAndView handleCarsList(Model model,
                                       PaginationDTO paginationDTO) {
        try {
            super.getCarControllerUtil().fillCarListModel(this.inMemoryCarListModelFiller, model, paginationDTO);
            return new ModelAndView(CARS);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @RequestMapping("/" + PAGINATION_URL)
    @ResponseBody
    public PaginationDTO handlePagination(Model model, CarPaginationDTO paginationDTO) {
        return this.carPaginationService.paginate(model, paginationDTO);
    }
}
