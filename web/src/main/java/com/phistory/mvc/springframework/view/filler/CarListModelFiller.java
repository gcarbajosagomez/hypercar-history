package com.phistory.mvc.springframework.view.filler;

import com.phistory.data.model.car.Car;
import com.phistory.mvc.model.dto.PaginationDTO;
import org.springframework.ui.Model;

import static com.phistory.mvc.controller.BaseControllerData.CARS_PER_PAGE_DATA;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE_ID;
import static com.phistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;

/**
 * Fills a Spring Framework {@link Model} with {@link Car} list-related information
 *
 * Created by gonzalo on 11/9/16.
 */
public abstract class CarListModelFiller implements ModelFiller {

    /**
     * Fill the supplied {@link Model} with paginated car data
     *
     * @param model
     * @param PaginationDTO
     */
    public void fillPaginatedModel(Model model, PaginationDTO PaginationDTO) {
        model.addAttribute(CARS_PER_PAGE_DATA, 	PaginationDTO.getItemsPerPage());
        model.addAttribute(PAG_NUM_DATA, 	    PaginationDTO.getPagNum());
    }
}
