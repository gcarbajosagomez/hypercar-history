package com.phistory.mvc.springframework.view.filler.sql;

import com.phistory.data.dao.sql.impl.CarDAO;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.CarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.phistory.mvc.controller.BaseControllerData.MODELS;

/**
 * SQL implementation of a {@link CarListModelFiller}
 *
 * Created by gonzalo on 11/9/16.
 */
@Component(value = "sqlCarsListModelFiller")
public class SQLCarListModelFiller extends CarListModelFiller {

    @Inject
    private CarDAO carDAO;

    @Override
    public void fillModel(Model model) {
        model.addAttribute(MODELS, this.carDAO.getAllOrderedByProductionStartDate());
    }

    @Override
    public void fillPaginatedModel(Model model, PaginationDTO PaginationDTO) {
        super.fillPaginatedModel(model, PaginationDTO);
    }
}
