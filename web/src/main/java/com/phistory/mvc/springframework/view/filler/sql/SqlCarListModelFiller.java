package com.phistory.mvc.springframework.view.filler.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlDAO;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

import static com.phistory.data.dao.sql.SqlCarDAO.SQL_CAR_DAO;
import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.springframework.view.filler.sql.SqlCarListModelFiller.SQL_CAR_LIST_MODEL_FILLER;

/**
 * SQL implementation of a {@link AbstractCarListModelFiller}
 * <p>
 * Created by gonzalo on 11/9/16.
 */
@Component(SQL_CAR_LIST_MODEL_FILLER)
public class SqlCarListModelFiller extends AbstractCarListModelFiller {

    public static final String SQL_CAR_LIST_MODEL_FILLER = "sqlCarListModelFiller";

    private SqlDAO sqlCarDAO;

    @Inject
    public SqlCarListModelFiller(@Named(SQL_CAR_DAO) SqlDAO sqlCarDAO) {
        this.sqlCarDAO = sqlCarDAO;
    }

    @Override
    public Model fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        model = super.fillPaginatedModel(model, paginationDTO);
        model.addAttribute(CARS,
                           this.sqlCarDAO.getByCriteria(this.createSearchCommand(paginationDTO)));
        return model;
    }

    /**
     * Create a search command to search for cars
     *
     * @param paginationDTO
     * @return
     */
    private SearchCommand createSearchCommand(PaginationDTO paginationDTO) {
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
