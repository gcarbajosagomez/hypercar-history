package com.phistory.mvc.springframework.view.filler;

import com.phistory.data.model.car.Car;
import com.phistory.mvc.dto.PaginationDTO;
import org.springframework.ui.Model;

import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE_DATA;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;

/**
 * Fills a Spring Framework {@link Model} with {@link Car} list-related information
 * <p>
 * Created by gonzalo on 11/9/16.
 */
public abstract class CarListModelFiller implements ModelFiller {

    /**
     * Fill the supplied {@link Model} with paginated car data
     *
     * @param model
     * @param paginationDTO
     */
    public void fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        model.addAttribute(CARS_PER_PAGE_DATA,      paginationDTO.getItemsPerPage());
        model.addAttribute(PAG_NUM_DATA,            paginationDTO.getPagNum());
        model.addAttribute("paginationFirstResult", paginationDTO.getFirstResult());
        model.addAttribute("paginationLastResult",  paginationDTO.getLastResult());
    }
}
