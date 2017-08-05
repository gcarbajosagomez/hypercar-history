package com.hhistory.mvc.controller;

import com.hhistory.data.model.GenericEntity;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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

    private static final String CAR_LIST_PAGE_TITLE_MESSAGE_KEY = "meta.title.allModels";

    private AbstractCarListModelFiller inMemoryCarListModelFiller;

    @Inject
    public CarListController(@Named(IN_MEMORY_CAR_LIST_MODEL_FILLER) AbstractCarListModelFiller inMemoryCarListModelFiller) {
        this.inMemoryCarListModelFiller = inMemoryCarListModelFiller;
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
    public PaginationDTO handlePagination(Model model, PaginationDTO paginationDTO) {
        model = this.inMemoryCarListModelFiller.fillPaginatedModel(model, paginationDTO);

        paginationDTO.setItems((List<GenericEntity>) model.asMap().get(CARS));

        super.getManufacturerService()
             .getInMemoryEntityFromModel(model)
             .ifPresent(manufacturer -> {
                 Object[] args = new Object[] {
                         super.getInMemoryCarDAO()
                              .getAllVisibleOrderedByProductionStartDate(manufacturer).size(),
                         paginationDTO.getFirstResult() + 1,
                         paginationDTO.getLastResult()
                 };

                 String pageTitle = super.getMessageSource()
                                         .getMessage(CAR_LIST_PAGE_TITLE_MESSAGE_KEY,
                                                     args,
                                                     LocaleContextHolder.getLocale());

                 paginationDTO.setPageTitle(pageTitle);
             });

        return paginationDTO;
    }
}
