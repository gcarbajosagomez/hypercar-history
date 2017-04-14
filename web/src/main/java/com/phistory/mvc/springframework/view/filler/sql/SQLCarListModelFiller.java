package com.phistory.mvc.springframework.view.filler.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlCarDAO;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.CarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.phistory.mvc.controller.BaseControllerData.CARS;

/**
 * SQL implementation of a {@link CarListModelFiller}
 *
 * Created by gonzalo on 11/9/16.
 */
@Component(value = "sqlCarsListModelFiller")
public class SQLCarListModelFiller extends CarListModelFiller {

    @Inject
    private SqlCarDAO carDAO;

    @Override
    public void fillModel(Model model) {}

    @Override
    public void fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        super.fillPaginatedModel(model, paginationDTO);
        model.addAttribute(CARS, this.carDAO.getByCriteria(this.createSearchCommand(paginationDTO)));
    }

    /**
     * Create a search command to search for cars
     *
     * @param paginationDTO
     * @return
     */
    private SearchCommand createSearchCommand(PaginationDTO paginationDTO)
    {
        Map<String, Boolean> orderByMap = new HashMap<>();
        orderByMap.put(Car.PRODUCTION_START_DATE_PROPERTY_NAME, Boolean.TRUE);

        int paginationFirstResult = paginationDTO.getFirstResult();

        return new SearchCommand(Car.class,
                                 null,
                                 null,
                                 orderByMap,
                                 null,
                                 paginationFirstResult,
                                 paginationDTO.getItemsPerPage());
    }
}
