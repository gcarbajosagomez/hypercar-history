package com.phistory.mvc.controller.util;

import com.phistory.mvc.controller.CarDetailsController;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
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
    private ModelFiller pictureModelFiller;

    @Inject
    public CarControllerUtil(ModelFiller carModelFiller, ModelFiller pictureModelFiller) {
        this.carModelFiller = carModelFiller;
        this.pictureModelFiller = pictureModelFiller;
    }

    /**
     * Fills the supplied {@link Model} with the necessary data to handle car list requests
     *
     * @param abstractCarListModelFiller
     * @param model
     * @param paginationDTO
     */
    public void fillCarListModel(AbstractCarListModelFiller abstractCarListModelFiller, Model model, PaginationDTO paginationDTO) {
        abstractCarListModelFiller.fillPaginatedModel(model, paginationDTO);
        this.carModelFiller.fillModel(model);
        this.pictureModelFiller.fillModel(model);
    }
}
