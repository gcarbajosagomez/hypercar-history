package com.phistory.mvc.controller.util;

import com.phistory.mvc.controller.CarController;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.CarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

/**
 * Set of utilities for {@link CarController}
 * 
 * @author gonzalo
 *
 */
@Component
public class CarControllerUtil
{
    @Inject
    private ModelFiller carModelFiller;
    @Inject
    private ModelFiller pictureModelFiller;

    /**
     * Fills the supplied {@link Model} with the necessary data to handle car list requests
     *
     * @param carListModelFiller
     * @param model
     * @param paginationDTO
     */
	public void fillCarListModel(CarListModelFiller carListModelFiller, Model model, PaginationDTO paginationDTO) {
        carListModelFiller.fillPaginatedModel(model, paginationDTO);
        this.carModelFiller.fillModel(model);
        this.pictureModelFiller.fillModel(model);
    }
}
