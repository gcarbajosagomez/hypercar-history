package com.hhistory.mvc.controller.util;

import com.hhistory.mvc.controller.CarDetailsController;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

/**
 * Set of utilities for {@link CarDetailsController}
 *
 * @author gonzalo
 */
@Component
public class CarControllerUtil {

    private ModelFiller carModelFiller;

    @Inject
    public CarControllerUtil(ModelFiller carModelFiller) {
        this.carModelFiller = carModelFiller;
    }

    /**
     * Fills the supplied {@link Model} with the necessary data to handle car list requests
     *
     * @param carListModelFiller
     * @param model
     * @param paginationDTO
     */
    public Model fillCarListModel(AbstractCarListModelFiller carListModelFiller,
                                  Model model,
                                  PaginationDTO paginationDTO) {
        model = carListModelFiller.fillPaginatedModel(model, paginationDTO);
        this.carModelFiller.fillModel(model);
        return model;
    }
}
